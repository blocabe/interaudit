package com.interaudit.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IContactService;
import com.interaudit.service.impl.RepositoryService;
import com.interaudit.service.param.SearchContactParam;
import com.interaudit.service.view.ContactsView;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class ContactController extends MultiActionController {

   // private final Log log = LogFactory.getLog(HandleBrowseDatasetPageController.class);
    
    
    private IContactService contactService;
    
    private static final String CONTACTS_KEY = "viewcontacts";
    
    public static final String NAME_LIKE = "nameLike";
	public static final String STARTING_LETTER = "startingLetterName";
	public static final String CUSTOMER_NAME = "customerName";
	
	private static final String CURRENT_PAGE_KEY = "currentPage";
	private static final String TOTAL_PAGE_KEY = "totalPage";
	private static final String PAGE = "p";
	private static final int LINEPERPAGE = 50;
    
    
    
    
    
    
    
   
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showContactsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	    	Map<String,Object> mapResults = new HashMap<String, Object>();
	    	
	    	String customerName = request.getParameter(CUSTOMER_NAME);
	    	String nameLike = request.getParameter(NAME_LIKE);
	    	String startingLetterName = request.getParameter(STARTING_LETTER);	    	
			logger.info("startingLetterName : " + startingLetterName);
			if(startingLetterName != null && startingLetterName.equalsIgnoreCase("*")){
				 startingLetterName = null;
			}
	    	
	    	SearchContactParam param = new SearchContactParam( customerName,nameLike,startingLetterName);
	    	
	    	
	    	//Gestion de la pagination
	    	int firstPos = 0;
			int p;
			try {
				p = Integer.parseInt(request.getParameter(PAGE));
			} catch(NumberFormatException nfe) {
				p = 1;
			}
			firstPos = ((p - 1) * LINEPERPAGE);
			long count = contactService.getTotalCount(param);
			
			int totalPage = (int)(count / LINEPERPAGE);
			if (count % LINEPERPAGE > 0) totalPage++;
			
	    		
	    	ContactsView view = contactService.findContacts(param,firstPos, LINEPERPAGE);
	    	mapResults.put(CONTACTS_KEY, view);
			mapResults.put(TOTAL_PAGE_KEY, totalPage);
			mapResults.put(CURRENT_PAGE_KEY, p);
			
	    	
			
		
			return new ModelAndView("list_contacts",mapResults);		
	}
    
    
   
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showCustomerPropertiesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("contacts_proprietes");		
	}
    
    
    public ModelAndView editContactPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	Long contactId = Long.parseLong(request.getParameter("contactId"));
    	Contact contact = contactService.getOneDetached(contactId);
	    mapResults.put("contact", contact);
	    
	    /** The application context. */
		ApplicationContext context = org.springframework.web.context.support.WebApplicationContextUtils
		.getRequiredWebApplicationContext(request.getSession().getServletContext());
		
		RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
		List<Option> allContactOptions  = repositoryService.getAllContactAsOptions();
		request.getSession().setAttribute( "allContactOptions",allContactOptions );
	    /*
	    List<Option> allContactOptions = (List<Option>) request.getSession().getAttribute( "allContactOptions" );
		if ( request.getSession().getAttribute( "allContactOptions" ) == null){
			allContactOptions = contactService.getAllContactAsOptions();
			request.getSession().setAttribute( "allContactOptions",allContactOptions );
		 }
		 */
		return new ModelAndView("contact_editor",mapResults);		
    }
    
    
   

	public IContactService getContactService() {
		return contactService;
	}


	public void setContactService(IContactService contactService) {
		this.contactService = contactService;
	}


	
    
    
	



	



	



	

	

	
}
