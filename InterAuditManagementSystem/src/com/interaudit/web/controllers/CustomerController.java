package com.interaudit.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IDeclarationService;
import com.interaudit.service.IOriginService;
import com.interaudit.service.impl.RepositoryService;
import com.interaudit.service.param.SearchCustomerParam;
import com.interaudit.service.view.CustomerView;
import com.interaudit.service.view.DeclarationsView;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class CustomerController extends MultiActionController {

   // private final Log log = LogFactory.getLog(HandleBrowseDatasetPageController.class);
    
    private IOriginService originService;
    private ICustomerService customerService;
    private IDeclarationService declarationService;
    
    private static final String CUSTOMERS_KEY = "viewcustomers";
    private static final String DECLARATIONS_KEY = "viewdeclarations";
    
    
   
	public static final String NAME_LIKE = "nameLike";
	public static final String STARTING_LETTER = "startingLetterName";
	public static final String MANAGER_NAME = "managerName";
	public static final String ASSOCIE_NAME = "associeName";
	
	
	private static final String CURRENT_PAGE_KEY = "currentPage";
	private static final String TOTAL_PAGE_KEY = "totalPage";
	private static final String PAGE = "p";
	private static final int LINEPERPAGE = 50;
    
    
	
	// window.location="${ctx}/registerNewOriginPage.htm?origin="+origin+"&id="+id;
	 public ModelAndView registerNewOriginPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception { 
		 
		 String newOriginName = request.getParameter("origin");
		 String id = request.getParameter("id");
		 
		 if(newOriginName != null && newOriginName.trim().length()>0){
			boolean ret = originService.createNewOrigine(newOriginName);
			if(ret){
				RepositoryService.getInstance().refresh();
			}			
		 }
		 
		 if(id != null && id.trim().length()>0){
			 return new ModelAndView("redirect:/showCustomerRegisterPage.htm?id="+id);
		 }
		 else{
			 return new ModelAndView("redirect:/showCustomerRegisterPage.htm?");
		 }
		 
		
		
		 
	 }
    
	public ModelAndView editCustomersAsAjaxStream(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	JSONObject ajaxReply = new JSONObject();
    	JSONArray eventList = null;
    	eventList =  (JSONArray)request.getSession(false).getAttribute("CustomersAsAjaxStream");
    	if(eventList == null){
    		
        	eventList = new JSONArray();
        	JSONObject ajaxObject = null;
        	
        	List<Option> allCustomerAsOptions = customerService.getAllCustomerAsOptions();
        	
    		
    		for(Option option : allCustomerAsOptions){
    			ajaxObject = new JSONObject();
        		ajaxObject.put("name", option.getName().toLowerCase());
        		ajaxObject.put("id", option.getId());
        		eventList.put(ajaxObject);
    		}
    		
    		
    		
    		request.getSession(false).setAttribute("CustomersAsAjaxStream", eventList);
    		
    	}
    	
		ajaxReply.put("events", eventList);
    	response.setContentType("text/json;charset=UTF-8");
    	ServletOutputStream out = response.getOutputStream();
		
    	String reply = ajaxReply.toString();
		out.write(reply.getBytes("UTF-8"));
		out.flush();
		out.close();
		return null;
    }
    
    
   
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showCustomersPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	    	Map<String,Object> mapResults = new HashMap<String, Object>();
	    	
	    	String managerName = request.getParameter(MANAGER_NAME);
	    	String associeName =  request.getParameter(ASSOCIE_NAME);
	    	String nameLike = request.getParameter(NAME_LIKE);
	    	String startingLetterName = request.getParameter(STARTING_LETTER);
	    	
	    	request.getSession(false).setAttribute(MANAGER_NAME,managerName);
   		 	request.getSession(false).setAttribute(ASSOCIE_NAME,associeName);
   		 	request.getSession(false).setAttribute(NAME_LIKE,nameLike);
   		 	request.getSession(false).setAttribute(STARTING_LETTER,startingLetterName);
   		 
	    	logger.info("startingLetterName : " + startingLetterName);
			if(startingLetterName != null && startingLetterName.equalsIgnoreCase("*")){
				 startingLetterName = null;
			}
	    	
	    	SearchCustomerParam param = new SearchCustomerParam( managerName,nameLike,startingLetterName,associeName);
	    	
	    	//Gestion de la pagination
	    	int firstPos = 0;
			int p;
			try {
				p = Integer.parseInt(request.getParameter(PAGE));
			} catch(NumberFormatException nfe) {
				p = 1;
			}
			firstPos = ((p - 1) * LINEPERPAGE);
			long count = customerService.getTotalCount(param);
			
			int totalPage = (int)(count / LINEPERPAGE);
			if (count % LINEPERPAGE > 0) totalPage++;
	    			 
	    	CustomerView view = customerService.searchCustomers(param,firstPos, LINEPERPAGE);
			mapResults.put(CUSTOMERS_KEY, view);
			mapResults.put(TOTAL_PAGE_KEY, totalPage);
			mapResults.put(CURRENT_PAGE_KEY, p);
		
			return new ModelAndView("list_customers",mapResults);		
	}
    
    public ModelAndView findCustomerByCodePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	String customerCode = request.getParameter("customerCode");
    	
    	SearchCustomerParam param = new SearchCustomerParam(customerCode);
    	CustomerView view = customerService.getCustomerFromCode(param);
    	
    	mapResults.put(CUSTOMERS_KEY, view);
    	
		return new ModelAndView("list_customers",mapResults);		
	}
    
    
    
	
	
	public ModelAndView showCustomerContractsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("customer_contracts");		
	}
	
	
	public ModelAndView showCustomerContactsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("customer_contacts");		
	}
	
	public ModelAndView showDeclarationsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		Map<String,Object> mapResults = new HashMap<String, Object>();
		DeclarationsView view = new  DeclarationsView(getDeclarationService().getAll());
    	
    	mapResults.put(DECLARATIONS_KEY, view);
		return new ModelAndView("list_declarations",mapResults);	
 
	}
	
	
    
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showCustomerPropertiesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("customers_proprietes");		
	}
    
    
    public ModelAndView editCustomerPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	Long customerId = Long.parseLong(request.getParameter("customerId"));
    	Customer customer = customerService.getOneDetached(customerId);
	    mapResults.put("customer", customer);
	    
	    List<Option> allCustomerOptions = (List<Option>) request.getSession().getAttribute( "allCustomerOptions" );
		if ( request.getSession().getAttribute( "allCustomerOptions" ) == null){
			allCustomerOptions = customerService.getAllCustomerAsOptions();
			request.getSession().setAttribute( "allCustomerOptions",allCustomerOptions );
		    }
		return new ModelAndView("customer_editor",mapResults);		
    }
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showCustomerDocumentsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("customer_documents");		
	}


	public ICustomerService getCustomerService() {
		return customerService;
	}


	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public IDeclarationService getDeclarationService() {
		return declarationService;
	}

	public void setDeclarationService(IDeclarationService declarationService) {
		this.declarationService = declarationService;
	}

	public IOriginService getOriginService() {
		return originService;
	}

	public void setOriginService(IOriginService originService) {
		this.originService = originService;
	}
    
    
    
	



	



	



	

	

	
}
