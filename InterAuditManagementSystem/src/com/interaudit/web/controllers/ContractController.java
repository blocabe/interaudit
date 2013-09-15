package com.interaudit.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IContractService;
import com.interaudit.service.impl.RepositoryService;
import com.interaudit.service.param.SearchContractParam;
import com.interaudit.service.view.ContractsView;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class ContractController extends MultiActionController {
	
	private static String CONTACTS_KEY = "viewcontracts";
	public static final String STARTING_LETTER = "startingLetterName";
	
	private static final String CURRENT_PAGE_KEY = "currentPage";
	private static final String TOTAL_PAGE_KEY = "totalPage";
	private static final String PAGE = "p";
	private static final int LINEPERPAGE = 50;
	
	private IContractService contractService;
  
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showContractsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	String customerName = request.getParameter("customer");
    	String startingLetterName = request.getParameter(STARTING_LETTER);	    	
		logger.info("startingLetterName : " + startingLetterName);
		if(startingLetterName != null && startingLetterName.equalsIgnoreCase("*")){
			 startingLetterName = null;
		}
    	SearchContractParam param = new SearchContractParam(customerName,startingLetterName);
    	List<String> customers = null;
    	
    	//Gestion de la pagination
    	int firstPos = 0;
		int p;
		try {
			p = Integer.parseInt(request.getParameter(PAGE));
		} catch(NumberFormatException nfe) {
			p = 1;
		}
		firstPos = ((p - 1) * LINEPERPAGE);
		long count = contractService.getTotalCount(param);
		
		int totalPage = (int)(count / LINEPERPAGE);
		if (count % LINEPERPAGE > 0) totalPage++;
    	
    	
    	ContractsView view = new  ContractsView(contractService.findContracts(param, firstPos, LINEPERPAGE),param,customers); 
    			 
    	
    	mapResults.put(CONTACTS_KEY, view);
    	mapResults.put(TOTAL_PAGE_KEY, totalPage);
		mapResults.put(CURRENT_PAGE_KEY, p);
		return new ModelAndView("list_contracts",mapResults);
    	
				
	}
    
    public ModelAndView editContractPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	Long contractId = Long.parseLong(request.getParameter("contractId"));
    	Contract contract = contractService.getOneDetached(contractId);
	    mapResults.put("contract", contract);
	    
	    /** The application context. */
		ApplicationContext context = org.springframework.web.context.support.WebApplicationContextUtils
		.getRequiredWebApplicationContext(request.getSession().getServletContext());
		
		RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
		List<Option> allContractOptions = repositoryService.getAllContractAsOptions();
		request.getSession().setAttribute( "allContractOptions",allContractOptions );
	    /*
	    List<Option> allContractOptions = (List<Option>) request.getSession().getAttribute( "allContractOptions" );
		if ( request.getSession().getAttribute( "allContractOptions" ) == null){
			allContractOptions = contractService.getAllContractAsOptions();
			request.getSession().setAttribute( "allContractOptions",allContractOptions );
		 }
		 */
		return new ModelAndView("contract_editor",mapResults);		
    }

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
    

	
}
