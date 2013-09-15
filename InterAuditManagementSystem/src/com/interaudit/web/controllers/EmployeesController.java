package com.interaudit.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IUserService;
import com.interaudit.service.param.SearchEmployeeParam;
import com.interaudit.service.view.EmployeeView;
import com.interaudit.util.WebUtils;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class EmployeesController extends MultiActionController {

   // private final Log log = LogFactory.getLog(HandleBrowseDatasetPageController.class);
	public static final String NAME_LIKE = "nameLike";
	public static final String STARTING_LETTER = "startingLetterName";
	public static final String ROLE = "role";
	
	private static final String CURRENT_PAGE_KEY = "currentPage";
	private static final String TOTAL_PAGE_KEY = "totalPage";
	private static final String PAGE = "p";
	private static final int LINEPERPAGE = 50;

	private IUserService userService;
	
	private static final String USERS_KEY = "viewusers";
	private static final String FINANCIAL_DATA_USERS_KEY = "viewFinancialDataUsers";
     
    /**
     * @param request
     * @param response
     * @return the list of all employees
     * @throws Exception
     */
    public ModelAndView showEmployeesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    	Map<String,Object> mapResults = new HashMap<String, Object>();
	    	
	    	String roleName = request.getParameter(ROLE);
	    	String nameLike = request.getParameter(NAME_LIKE);
	    	String startingLetterName = request.getParameter(STARTING_LETTER);
	    	
	    	SearchEmployeeParam param = new SearchEmployeeParam( roleName,  
	    														 nameLike,
	    			 											 startingLetterName);
	    	
	    	//Gestion de la pagination
	    	int firstPos = 0;
			int p;
			try {
				p = Integer.parseInt(request.getParameter(PAGE));
			} catch(NumberFormatException nfe) {
				p = 1;
			}
			firstPos = ((p - 1) * LINEPERPAGE);
			long count = userService.getTotalCount(param);
			
			int totalPage = (int)(count / LINEPERPAGE);
			if (count % LINEPERPAGE > 0) totalPage++;
			
	    	EmployeeView view = userService.getMatchingEmployees(param,firstPos, LINEPERPAGE);	
			mapResults.put(USERS_KEY, view);
			mapResults.put(TOTAL_PAGE_KEY, totalPage);
			mapResults.put(CURRENT_PAGE_KEY, p);
			return new ModelAndView("list_employees",mapResults);		
	}
    
    
    
    public ModelAndView findEmployeesByCodePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	String employeeCode = request.getParameter("employeeCode");
    	
    	SearchEmployeeParam param = new SearchEmployeeParam(employeeCode);
    	EmployeeView view = userService.getEmployeeFromCode(param);
    	
    	mapResults.put(USERS_KEY, view);
    	
		return new ModelAndView("list_employees",mapResults);		
	}
    
    public ModelAndView showEmployeesFinancialDataPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	//Clean the session scope first
		WebUtils.cleanSession(request);
		
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	mapResults.put(FINANCIAL_DATA_USERS_KEY, userService.getAll());
    	
		return new ModelAndView("employees_financial_data_workshop",mapResults);		
	}
    
    
  
    
    /*
    public ModelAndView createEmployeesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
   
    	String lastname = request.getParameter("lastname");
    	String firstname = request.getParameter("firstname");
    	String email = request.getParameter("email");
    	String country = request.getParameter("country");
    	
    	String role = request.getParameter("role");
    	String isactiveStr = request.getParameter("isactive");
    	String login = request.getParameter("login");
    	String psswd = request.getParameter("psswd");
    	
    	boolean isactive = (isactiveStr != null && isactiveStr.length() > 0);
    	
    	
    	
		
    	Employee employee = null;
    		userService.createEmployee(  lastname,
    													 firstname,
    													 email,
    													 country,
    													 role,
    													 isactive,
    													 login,
    													 psswd
    													);
    	
    	if(employee !=null){
    		SearchEmployeeParam param = new SearchEmployeeParam(employee.getCode());
        	EmployeeView view = userService.getEmployeeFromCode(param);
        	
        	mapResults.put(USERS_KEY, view);
        	
    		return new ModelAndView("list_employees",mapResults);
    	}else{
    		return showEmployeesPage( request,response);
    	}
		
    	
    			
	}
    */
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showEmployeePropertiesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("employee_proprietes");		
	}
    
    
    public ModelAndView editEmployeePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	Long employeeId = Long.parseLong(request.getParameter("employeeId"));
    	Employee employee = userService.getOneDetached(employeeId);
	    mapResults.put("employee", employee);
	    
	    List<Option> allEmployeeOptions = (List<Option>) request.getSession().getAttribute( "allEmployeeOptions" );
		if ( request.getSession().getAttribute( "allEmployeeOptions" ) == null){
			allEmployeeOptions = userService.getAllEmployeeAsOptions();
			request.getSession().setAttribute( "allEmployeeOptions",allEmployeeOptions );
		    }
		
		return new ModelAndView("employee_editor",mapResults);		
    }
    
    
    public ModelAndView showEmployeeFormationsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("employee_formations");		
	}
    
    
    

    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showEmployeeDocumentsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("employee_documents");		
	}


	public IUserService getUserService() {
		return userService;
	}


	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
}
