package com.interaudit.web.controllers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.ObjectifPerExercise;
import com.interaudit.domain.model.data.AnnualBudgetData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IBudgetService;
import com.interaudit.service.IExerciseService;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.param.SearchBudgetParam;
import com.interaudit.service.param.SearchProfitabilityPerCustomerParam;
import com.interaudit.service.view.BudgetGeneralView;
import com.interaudit.service.view.ExerciseGeneralView;
import com.interaudit.service.view.ProfitabilityPerCustomerView;
import com.interaudit.util.WebContext;
import com.interaudit.util.WebUtils;



/**
 * The Welcome action.
 * 
 */
public class BudgetController extends MultiActionController implements  ApplicationContextAware {

   // private final Log log = LogFactory.getLog(HandleBrowseDatasetPageController.class);
    
    
	private static final Logger  logger      = Logger.getLogger(BudgetController.class);
	private IBudgetService budgetService;
	private IExerciseService exerciseService;
	
	private static final String colNumero = "colNumero";
	private static final String colClient = "colClient";
	private static final String colType = "colType";
	private static final String colExercice = "colExercice";
	private static final String colOrigine = "colOrigine";
	private static final String colBudgetprevu = "colBudgetprevu";
	private static final String colBudgetreporte = "colBudgetreporte";
	private static final String colBudgettotal = "colBudgettotal";
	private static final String colBudgetnonFiable = "colBudgetnonFiable";
	private static final String colFacturation = "colFacturation";
	private static final String colAssocie = "colAssocie";
	private static final String colManager = "colManager";
	private static final String colComment = "colComment";
	private static final String colStatut = "colStatut";
	
	private static final String BUDGETS_KEY = "viewbudget";
	private static final String EXERCISES_KEY = "viewExercise";
	private static final String PROFITABILITY_KEY = "viewprofitability";
	private static final String SEARCHBUDGETPARAM_KEY = "SEARCHBUDGETPARAM_KEY";
	
	private static final String CURRENT_PAGE_KEY = "currentPage";
	private static final String TOTAL_PAGE_KEY = "totalPage";
	private static final String PAGE = "p";
	private static final int LINEPERPAGE = 25;
	private static final String TOTAL_COUNT ="totalCount";
	private static final String FIRSTPOS_KEY ="FIRSTPOS_KEY";
	
	/*
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception
			{
		try{
			DecimalFormat format = new DecimalFormat("0,00");
			 
			PropertyEditor propertyEditor =  
			    new CustomNumberEditor(Double.class, format, true); 
			binder.registerCustomEditor(Double.class, "myDoubleField", propertyEditor); 

			
		}catch(Exception e){
			//System.out.println(e.getMessage());
			throw e;
		}
		

	}
	*/
	
	
	public ModelAndView deletePendingExercise(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Exercise exercise = null;
    	JSONObject ajaxReply = new JSONObject();    	
		try{	   		 
			String idExerciseAsString = request.getParameter("id");
			Long idExercise = Long.parseLong(idExerciseAsString);
			exercise = exerciseService.getOneDetached(idExercise);
			int year = exercise.getYear();
			exerciseService.deletePendingExercise(idExercise);
			request.getSession(false).removeAttribute("budget_years");
			request.getSession(false).setAttribute("successMessage", "Exercise [ " + year + " ] has been successfully deleted ");	
			 ajaxReply.put("result", "ok");
			 response.setContentType("text/json;charset=UTF-8");
		    	ServletOutputStream out = response.getOutputStream();				
		    	String reply = ajaxReply.toString();
				out.write(reply.getBytes("UTF-8"));
				out.flush();
				out.close();
				return null;
   	 	}catch(BusinessException be){
   	 		request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
			 //return  showGeneralBudgetPage( request, response);
	   	 	 ajaxReply.put("result", "nok");
	   	 	response.setContentType("text/json;charset=UTF-8");
	    	ServletOutputStream out = response.getOutputStream();
			
	    	String reply = ajaxReply.toString();
			out.write(reply.getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
		 }
	}
	
	
	public ModelAndView updateExerciceAndBudget(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
		JSONObject ajaxReply = new JSONObject();    	
		try{
			 String idExerciseAsString = request.getParameter("id");
			 Long idExercise = Long.parseLong(idExerciseAsString);
	   		 exerciseService.updateExerciceAndBudget(idExercise);  
			 request.getSession(false).setAttribute("successMessage", "The exercise has been successfully updated");
			 //return  showGeneralBudgetPage( request, response);	
			 ajaxReply.put("result", "ok");
			 response.setContentType("text/json;charset=UTF-8");
		    	ServletOutputStream out = response.getOutputStream();				
		    	String reply = ajaxReply.toString();
				out.write(reply.getBytes("UTF-8"));
				out.flush();
				out.close();
				//return null;
				request.getSession(false).removeAttribute(BUDGETS_KEY);
		    	request.getSession(false).removeAttribute(EXERCISES_KEY);		    	
				return new ModelAndView("redirect:/showGeneralBudgetPage.htm");
   	 	}catch(Exception be){
			 request.getSession(false).setAttribute("actionErrors",be.getLocalizedMessage());
			 //return  showGeneralBudgetPage( request, response);
	   	 	 ajaxReply.put("result", "nok");
	   	 	response.setContentType("text/json;charset=UTF-8");
	    	ServletOutputStream out = response.getOutputStream();
			
	    	String reply = ajaxReply.toString();
			out.write(reply.getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
		 }
	}
	
	public ModelAndView findBudgetsForExpression(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
		JSONObject ajaxReply = new JSONObject();    	
		try{	
			
			//Recuperation des exercices si disponibles
			 List<Integer> years = new ArrayList<Integer> ();			
			 if(request.getSession(false).getAttribute("budget_years") == null){
				
				 Integer currentYear = exerciseService.getFirstOnGoingExercise();
		    	 if(currentYear == null){
		    	 	 //currentYear = exerciseService.getMaxYear();
	 				 currentYear =  exerciseService.getLastClosedExercise();
 					 if(currentYear == null){
 						Calendar c = Calendar.getInstance(); 
 						currentYear =c.get(Calendar.YEAR);
 					 } 
		    	 }
				 years.add(currentYear);		       
		         
			 }else{
				 years = (List<Integer>)request.getSession(false).getAttribute("budget_years");
			 }
			 
			 List<AnnualBudgetData> results = null;
			 
			 //Recuperation de l'expression 
			 String expression = request.getParameter("budget_expression");
				 
			if(expression != null){
				results = budgetService.findBudgetsForExpression( expression, years);
			}
			
			String code = request.getParameter("customer_code");
			if(code != null){
				results = budgetService.findBudgetsForCustomerCode( code,years);
			}
			 
			 
			 BudgetGeneralView budgets = (BudgetGeneralView) request.getSession(false).getAttribute(BUDGETS_KEY);
			 budgets.setBudgets(results);
				
			// request.getSession(false).setAttribute("successMessage", "The exercise has been successfully updated");
			 //return  showGeneralBudgetPage( request, response);	
			 ajaxReply.put("result", "ok");
			 response.setContentType("text/json;charset=UTF-8");
		    	ServletOutputStream out = response.getOutputStream();				
		    	String reply = ajaxReply.toString();
				out.write(reply.getBytes("UTF-8"));
				out.flush();
				out.close();
				return null;
   	 	}catch(Exception be){
			 request.getSession(false).setAttribute("actionErrors",be.getLocalizedMessage());
			 //return  showGeneralBudgetPage( request, response);
	   	 	 ajaxReply.put("result", "nok");
	   	 	response.setContentType("text/json;charset=UTF-8");
	    	ServletOutputStream out = response.getOutputStream();
			
	    	String reply = ajaxReply.toString();
			out.write(reply.getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
		 }
	}

	public ModelAndView refreshBudgetsPages(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
			return new ModelAndView("budget_workshop");		
	}

	
	 
	
	
	//editBudgetValidContractReferencesAsAjaxStream.htm
	public ModelAndView editBudgetValidContractReferencesAsAjaxStream(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	JSONObject ajaxReply = new JSONObject();
    	JSONArray eventList = new JSONArray();
    	JSONObject ajaxObject = null;
    	
    	BudgetGeneralView budgetDetails = (BudgetGeneralView)request.getSession(false).getAttribute(BUDGETS_KEY) ;
		if(budgetDetails == null)
		{			
			ajaxReply.put("result", "nok");
			response.setContentType("application/json;charset=UTF-8");
		}	
    	
    	
		
	
		for(String option : budgetDetails.getValidContractReferences()){
			ajaxObject = new JSONObject();
    		ajaxObject.put("name", option.toLowerCase());    	
    		eventList.put(ajaxObject);
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
	
	
	
	
	@SuppressWarnings("unchecked")
	public ModelAndView showObjectifPerManagerBartChartPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OutputStream out = response.getOutputStream();
		try {
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				
				ExerciseGeneralView exerciseGeneralView = (ExerciseGeneralView)request.getSession(false).getAttribute(EXERCISES_KEY);
				
				Map<String,ObjectifPerExercise> objectifsPerManager = exerciseGeneralView.getObjectifsPerManager();
				
				List<Option> managerOptions = exerciseGeneralView.getManagerOptions();
				
				for(Option option : managerOptions){
					dataset.addValue(objectifsPerManager.get(option.getName()).getAnnualAmount(), option.getName(), "Objectif");
				}
				
				String title= "Objectifs "+ exerciseGeneralView.getExercise()+ " par manager" ;
				
				JFreeChart chart = ChartFactory.createBarChart(
				title,
				"Category",
				"Prevision",
				dataset,
				PlotOrientation.VERTICAL,
				true, true, false
				);
				response.setContentType("image/png");
				ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
				}
		catch (Exception e) {
			logger.error(e.toString());
		}
		finally {
		out.close();
		}
		
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView showObjectifPerAssocieBartChartPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OutputStream out = response.getOutputStream();
		try {
				
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				
				ExerciseGeneralView exerciseGeneralView = (ExerciseGeneralView)request.getSession(false).getAttribute(EXERCISES_KEY);
				
				Map<String,ObjectifPerExercise> objectifsPerAssocie = exerciseGeneralView.getObjectifsPerAssocie();
				
				List<Option> associeOptions = exerciseGeneralView.getAssocieOptions();
				
				for(Option option : associeOptions){
					dataset.addValue(objectifsPerAssocie.get(option.getName()).getAnnualAmount(), option.getName(), "Objectif");
				}
				
				
				String title= "Objectifs "+ exerciseGeneralView.getExercise()+ " par associe" ;
				
				JFreeChart chart = ChartFactory.createBarChart(
				title,
				"Category",
				"Prevision",
				dataset,
				PlotOrientation.VERTICAL,
				true, true, false
				);
				response.setContentType("image/png");
				ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
				}
		catch (Exception e) {
			logger.error(e.toString());
		}
		finally {
		out.close();
		}
		
		return null;
		
	}
	
	//showDisplayColumnsPage.htm
	
	 public ModelAndView showDisplayColumnsPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		
		request.getSession(false).setAttribute(colNumero,request.getParameter("col_numero")!= null?"true":"false");
		request.getSession(false).setAttribute(colClient,request.getParameter("col_client")!= null?"true":"false");		
		request.getSession(false).setAttribute(colType,request.getParameter("col_type")!= null?"true":"false");		
		request.getSession(false).setAttribute(colExercice,request.getParameter("col_exercice")!= null?"true":"false");		
		request.getSession(false).setAttribute(colOrigine,request.getParameter("col_origine")!= null?"true":"false");		
		request.getSession(false).setAttribute(colBudgetprevu,request.getParameter("col_budgetprevu")!= null?"true":"false");
		request.getSession(false).setAttribute(colBudgetreporte,request.getParameter("col_budgetreporte")!= null?"true":"false");
		request.getSession(false).setAttribute(colBudgettotal,request.getParameter("col_budgettotal")!= null?"true":"false");
		request.getSession(false).setAttribute(colBudgetnonFiable,request.getParameter("col_budgetnonFiable")!= null?"true":"false");		
		request.getSession(false).setAttribute(colFacturation,request.getParameter("col_facturation")!= null?"true":"false");
		request.getSession(false).setAttribute(colAssocie,request.getParameter("col_associe")!= null?"true":"false");
		request.getSession(false).setAttribute(colManager,request.getParameter("col_manager")!= null?"true":"false");
		request.getSession(false).setAttribute(colComment,request.getParameter("col_comment")!= null?"true":"false");
		request.getSession(false).setAttribute(colStatut,request.getParameter("col_statut")!= null?"true":"false");
		
		
		return showGeneralBudgetPage( request, response);
		
	}
    
    
    
	 public ModelAndView showGeneralBudgetPageFromMenu(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		 //Clean the session scope first
		 WebUtils.cleanSession(request);
		 
		 request.getSession(false).removeAttribute("budget_status");
		 request.getSession(false).removeAttribute("budget_origin");
		 request.getSession(false).removeAttribute("budget_manager");
		 request.getSession(false).removeAttribute("budget_associe");
		 
		 
		if( request.getSession(false).getAttribute(colNumero) == null){
			request.getSession(false).setAttribute(colNumero,"true");
		}
			
		
		if( request.getSession(false).getAttribute(colClient) == null){
			 request.getSession(false).setAttribute(colClient,"true");	
		}
		
		 
		 if( request.getSession(false).getAttribute(colType) == null){
			 request.getSession(false).setAttribute(colType,"true");
		 }
		 
		 
		 if( request.getSession(false).getAttribute(colExercice) == null){
			 request.getSession(false).setAttribute(colExercice,"true"); 
		 }
		 		
		 
		 if( request.getSession(false).getAttribute(colOrigine) == null){
			 request.getSession(false).setAttribute(colOrigine,"true");	
		 }
		
		 
		 if( request.getSession(false).getAttribute(colBudgetprevu) == null){
			 request.getSession(false).setAttribute(colBudgetprevu,"true"); 
		 }
		 
		 
		 if( request.getSession(false).getAttribute(colBudgetreporte) == null){
			 request.getSession(false).setAttribute(colBudgetreporte,"true");
		 }
		
		 
		 if( request.getSession(false).getAttribute(colBudgettotal) == null){
			 request.getSession(false).setAttribute(colBudgettotal,"true");
		 }
		
		 
		 if( request.getSession(false).getAttribute(colBudgetnonFiable) == null){
			 request.getSession(false).setAttribute(colBudgetnonFiable,"false");
		 }
		 
		 
		 if( request.getSession(false).getAttribute(colFacturation) == null){
			 request.getSession(false).setAttribute(colFacturation,"true");
		 }
		 
		 
		 if( request.getSession(false).getAttribute(colAssocie) == null){
			 request.getSession(false).setAttribute(colAssocie,"true"); 
		 }
		
		 
		 if( request.getSession(false).getAttribute(colManager) == null){
			 request.getSession(false).setAttribute(colManager,"true");
		 }
		 
		 
		 if( request.getSession(false).getAttribute(colComment) == null){
			 request.getSession(false).setAttribute(colComment,"false");
		 }
		
		 
		 if( request.getSession(false).getAttribute(colStatut) == null){
			 request.getSession(false).setAttribute(colStatut,"true");
		 }
		 
		 return showGeneralBudgetPage( request,response);
					 
		 
	 }
    
   
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public ModelAndView showGeneralBudgetPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	logger.info("Getting into showGeneralBudgetPage");
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	//SearchBudgetParam param = new SearchBudgetParam();
    	 boolean hasRequestedPage = false;
    	String type = request.getParameter("budget_type");
		String origin = request.getParameter("budget_origin");
		String manager = request.getParameter("budget_manager");
		String associe = request.getParameter("budget_associe");
		boolean forceReload = request.getParameter("forceReload") != null;
		boolean toCloseKey = request.getParameter("TO_CLOSE") != null;
		
		
		
		
		String budget_expression = request.getParameter("budget_expression");
		String customer_code = request.getParameter("customer_code");
		
		
		
		
		
		
		hasRequestedPage = (type == null || type.equalsIgnoreCase("-1")) ||
						   (origin == null ||origin.equalsIgnoreCase("-1"))||  
				           (manager == null || manager.equalsIgnoreCase("-1"))||  
				           (associe == null || associe.equalsIgnoreCase("-1"));
		
		
		logger.info("budget_type : " + type);
		logger.info("budget_origin : " + origin);
		logger.info("budget_manager : " + manager);
		logger.info("associe : "+associe);
		
		
		//Recuperation des exercices si disponibles
		 List<Integer> years = new ArrayList<Integer> ();
		 Enumeration<String> paramNames = request.getParameterNames();
		 while(paramNames.hasMoreElements()) {
		      String paramName = (String)paramNames.nextElement();
		      if (paramName.startsWith("year_")){
		    	  Integer year = Integer.valueOf(request.getParameter(paramName));
		    	  years.add(year);      
		      }
		 }	
		
		 if(years.isEmpty()){
			 if(request.getSession(false).getAttribute("budget_years") == null){
				 
				 Integer currentYear = exerciseService.getFirstOnGoingExercise();
				 if(currentYear == null){
					//currentYear = exerciseService.getMaxYear();
	 				 currentYear =  exerciseService.getLastClosedExercise();
					 if(currentYear == null){
						Calendar c = Calendar.getInstance(); 
						currentYear =c.get(Calendar.YEAR);
					 } 
				 }
				years.add(currentYear);
		         //years.add(c.get(Calendar.YEAR));
		         request.getSession(false).setAttribute("budget_years",years);
			 }else{
				 years = (List<Integer>)request.getSession(false).getAttribute("budget_years");
			 }
			 
		 }else{
			 request.getSession(false).setAttribute("budget_years",years);
		 }
		 
		
		
		 
		//Traitement special pour les recherches à partir d'une expression	 
		 if(budget_expression != null){
					List<AnnualBudgetData> results  = budgetService.findBudgetsForExpression( budget_expression, years);
					BudgetGeneralView budgets = (BudgetGeneralView) request.getSession(false).getAttribute(BUDGETS_KEY);
					budgets.setBudgets(results);
				 
				
		    		
		    		int totalPage = (int)(results.size() / LINEPERPAGE);
		    		if (results.size() % LINEPERPAGE > 0) totalPage++;		    		
		    		logger.info("Total page found : " + totalPage);
		    		
		    		mapResults.put(TOTAL_COUNT,  results.size());
					mapResults.put(BUDGETS_KEY, budgets);
					mapResults.put(TOTAL_PAGE_KEY, totalPage);
					mapResults.put(CURRENT_PAGE_KEY, 1);
					mapResults.put("budget_expression", budget_expression);
		    		return new ModelAndView("budget_workshop",mapResults);		
		  }
			
		//Traitement special pour les recherches à partir d'un code client
			if(customer_code != null){
				List<AnnualBudgetData> results  = budgetService.findBudgetsForCustomerCode( customer_code,years);
				BudgetGeneralView budgets = (BudgetGeneralView) request.getSession(false).getAttribute(BUDGETS_KEY);
				budgets.setBudgets(results);
				
				int totalPage = (int)(results.size() / LINEPERPAGE);
	    		if (results.size() % LINEPERPAGE > 0) totalPage++;		    		
	    		logger.info("Total page found : " + totalPage);
	    		mapResults.put(TOTAL_COUNT,  results.size());
				mapResults.put(BUDGETS_KEY, budgets);
				mapResults.put(TOTAL_PAGE_KEY, totalPage);
				mapResults.put(CURRENT_PAGE_KEY, 1);
				mapResults.put("customer_code", customer_code);
	    		return new ModelAndView("budget_workshop",mapResults);		
			}
    	
		
		 String startingLetterName = request.getParameter("startingLetterName");
		 logger.info("startingLetterName : " + startingLetterName);
		 if(startingLetterName != null && startingLetterName.equalsIgnoreCase("*")){
			 startingLetterName = null;
			 hasRequestedPage = true;
		 }
		 /*else if(startingLetterName == null ){
			 startingLetterName = "A";
		 }
		 */
		
		 if(startingLetterName != null ){
			 request.getSession(false).setAttribute("startingLetterName",startingLetterName);
			 hasRequestedPage = true;
		 }
		 else{
			 request.getSession(false).removeAttribute("startingLetterName");
		 }
		 
    	//First access to the page
    	if( type == null && origin == null && manager == null && associe == null){
    		
    		 type = (String)request.getSession(false).getAttribute("budget_type");
    		 origin = (String)request.getSession(false).getAttribute("budget_origin");
    		 manager = (String)request.getSession(false).getAttribute("budget_manager");
    		 associe = (String)request.getSession(false).getAttribute("budget_associe");
    		
    	}else{
  
    		 request.getSession(false).setAttribute("budget_type",type);
    		 request.getSession(false).setAttribute("budget_origin",origin);
    		 request.getSession(false).setAttribute("budget_manager",manager);
    		 request.getSession(false).setAttribute("budget_associe",associe);
    		 request.getSession(false).setAttribute("TO_CLOSE",toCloseKey);
    		 
    		
    		 
    	}
    	int p;
    	//Gestion de la pagination
    	
		try {
			p = Integer.parseInt(request.getParameter(PAGE));
			hasRequestedPage = true;
		} catch(NumberFormatException nfe) {
			p = 1;
		}
    	
    	
    	
    	SearchBudgetParam  param = new SearchBudgetParam( type == null ||type.equalsIgnoreCase("-1")? null: type,
					origin == null ||origin.equalsIgnoreCase("-1")? null:  Long.valueOf(origin) ,  
					manager == null || manager.equalsIgnoreCase("-1")? null: Long.valueOf(manager),  
					associe == null || associe.equalsIgnoreCase("-1")? null: Long.valueOf(associe),
					years,startingLetterName,p,toCloseKey); 
    	
    	//SearchBudgetParam  previousParam = (SearchBudgetParam)request.getSession(false).getAttribute(SEARCHBUDGETPARAM_KEY);
    	boolean queryDatabase = true;
    	/*
    	if(forceReload == true || toCloseKey == true){
    		queryDatabase = true;
    	}
    	else if( (previousParam != null && previousParam.equals(param)) || hasRequestedPage == false){
    		queryDatabase = false;
    	}
    	else{
    		request.getSession(false).setAttribute(SEARCHBUDGETPARAM_KEY, param);
    	}
    	*/
    	
    	BudgetGeneralView budgets = null;
    	ExerciseGeneralView exerciseGeneralView = null;
    	int firstPos = 0;
		
		int totalPage = 0;
		long count = 0;
    	if ( queryDatabase == true){
    		
    		
    		firstPos = ((p - 1) * LINEPERPAGE);
    		count = budgetService.getTotalCount(param);
    		logger.info("Total entries found : " + count);
    		
    		totalPage = (int)(count / LINEPERPAGE);
    		if (count % LINEPERPAGE > 0) totalPage++;
    		
    		logger.info("Total page found : " + totalPage);
    		
    		
    		
    		request.getSession(false).setAttribute(TOTAL_PAGE_KEY, totalPage);
    		request.getSession(false).setAttribute(TOTAL_COUNT, count);
    		request.getSession(false).setAttribute(FIRSTPOS_KEY, firstPos);
    		request.getSession(false).setAttribute(PAGE, p);
    		
    		budgets = budgetService.searchBudgets(param,true,firstPos, LINEPERPAGE);	
        	request.getSession(false).setAttribute(BUDGETS_KEY, budgets);
        	
    		if( years != null && years.size() ==1 ){
    			Integer exercise = years.get(0);
    			 exerciseGeneralView = (ExerciseGeneralView)request.getSession(false).getAttribute(EXERCISES_KEY);
    	    		
    	        	if( exerciseGeneralView == null){
    	        		 exerciseGeneralView = exerciseService.getExerciseGeneralViewLight(exercise);
    	    			request.getSession(false).setAttribute(EXERCISES_KEY, exerciseGeneralView);
    	        	}
    	        	else{
    	        		
    	        		if( exerciseGeneralView.getExercise() != null && exerciseGeneralView.getExercise().intValue() != exercise.intValue()){
    	        			exerciseGeneralView = exerciseService.getExerciseGeneralViewLight(exercise);
        	    			request.getSession(false).setAttribute(EXERCISES_KEY, exerciseGeneralView);
    	        		}    	        		
    	        	}
    		}
    		
        	
    		
    	}
    	/*
    	else{
    		
    		//firstPos = (Integer)request.getSession(false).getAttribute(FIRSTPOS_KEY);
    		if(request.getSession(false).getAttribute(PAGE) != null){
    			p = (Integer)request.getSession(false).getAttribute(PAGE);
    		}
    		
    		firstPos = ((p - 1) * LINEPERPAGE);
    		    		
    		if(request.getSession(false).getAttribute(TOTAL_COUNT) != null){
    			count = (Long)request.getSession(false).getAttribute(TOTAL_COUNT);
    		}
    		else{
    			count = budgetService.getTotalCount(param);
    		}
    		
    		if(request.getSession(false).getAttribute(TOTAL_PAGE_KEY) != null){
    			totalPage =(Integer)request.getSession(false).getAttribute(TOTAL_PAGE_KEY);
    		}
    		else{
    			totalPage = (int)(count / LINEPERPAGE);
        		if (count % LINEPERPAGE > 0) totalPage++;
    		}
    		
    		
    		
    		
    		budgets = (BudgetGeneralView)request.getSession(false).getAttribute(BUDGETS_KEY);
        	
        	if( budgets == null){
        		budgets = budgetService.searchBudgets(param,true,firstPos, LINEPERPAGE);	
        		request.getSession(false).setAttribute(BUDGETS_KEY, budgets);
        	}
    		
        	
        	exerciseGeneralView = (ExerciseGeneralView)request.getSession(false).getAttribute(EXERCISES_KEY);
    		
        	if( exerciseGeneralView == null){
        		if( years != null && years.size() ==1 ){
        			Integer exercise = years.get(0);
        			 exerciseGeneralView = exerciseService.getExerciseGeneralViewLight(exercise);
        			request.getSession(false).setAttribute(EXERCISES_KEY, exerciseGeneralView);
        		}
        	}
    	}
    */
		mapResults.put(TOTAL_COUNT, count);
		mapResults.put(BUDGETS_KEY, budgets);
		mapResults.put(TOTAL_PAGE_KEY, totalPage);
		mapResults.put(CURRENT_PAGE_KEY, p);

		
		return new ModelAndView("budget_workshop",mapResults);		
	}
    
    
    @SuppressWarnings("unchecked")
	public ModelAndView showBudgetExcelView(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	//List<Integer> years = (List<Integer>)request.getSession(false).getAttribute("budget_years");
    	String status = (String)request.getSession(false).getAttribute("budget_status");
    	String origin = (String)request.getSession(false).getAttribute("budget_origin");
    	String manager = (String)request.getSession(false).getAttribute("budget_manager");
    	String associe = (String)request.getSession(false).getAttribute("budget_associe");
    	
    	String idExerciseAsString = request.getParameter("id");
		Long idExercise = Long.parseLong(idExerciseAsString);
		Exercise exercise = exerciseService.getOneDetached(idExercise);
		int year = exercise.getYear();
		List<Integer> years = new ArrayList<Integer>();
		years.add(year);
    	SearchBudgetParam  param = new SearchBudgetParam( status == null ||status.equalsIgnoreCase("-1")? null: status,
    						origin == null ||origin.equalsIgnoreCase("-1")? null: Long.valueOf(origin) ,  
    						manager == null || manager.equalsIgnoreCase("-1")? null: Long.valueOf(manager),  
    						associe == null || associe.equalsIgnoreCase("-1")? null: Long.valueOf(associe),
    						years,null,null,false); 

    	BudgetGeneralView budgets = budgetService.searchBudgets(param,false,1, LINEPERPAGE);
     	mapResults.put("budgetExcelView", budgets.getBudgets());
    	mapResults.put("exercisesView", budgets.getExercises());
    	//int exercise =years.get(0);
    	ExerciseGeneralView exerciseGeneralView = exerciseService.getExerciseGeneralView(year);
    	mapResults.put("objectifs", exerciseGeneralView);
    	
    
    	return new ModelAndView("budgetExcelView",mapResults);	
    }
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public ModelAndView buildNextExercisePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	Exercise exercise = null;
    	JSONObject ajaxReply = new JSONObject();    	
		try{	 
			response.setContentType("text/json;charset=UTF-8");
	    	ServletOutputStream out = response.getOutputStream();	
			exercise = exerciseService.buildExercise(out);
			
			request.getSession(false).setAttribute("successMessage", "The exercise " + exercise.getYear() + " has been successfully built");
			 ajaxReply.put("result", "OK");
			 			
		    	String reply = ajaxReply.toString();
				out.write(/*reply.getBytes("UTF-8")*/"OK".getBytes("UTF-8"));
				out.flush();
				out.close();
				return null;
   	 	}catch(BusinessException be){
   	 		request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
			 //return  showGeneralBudgetPage( request, response);
	   	 	 ajaxReply.put("result", "nok");
	   	 	response.setContentType("text/json;charset=UTF-8");
	    	ServletOutputStream out = response.getOutputStream();
			
	    	String reply = ajaxReply.toString();
			out.write(reply.getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
		 }
    }
			
    
    
    
    public ModelAndView approveExercise(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	/*
    	 try{
    		
			 return  showGeneralBudgetPage( request, response);	
    	 }catch(BusinessException be){
			 request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
			 return  showGeneralBudgetPage( request, response);
		 }
    	 */
    	 Exercise exercise = null;
     	JSONObject ajaxReply = new JSONObject();    	
 		try{	   		 
 			 String idExerciseAsString = request.getParameter("id");
    		 Long idExercise = Long.parseLong(idExerciseAsString);
    		 exercise = exerciseService.approveExercise(idExercise);
    		 getServletContext().setAttribute("currentExercise",exercise);	
    		 /*
			Integer maxYear = exerciseService.getFirstOnGoingExercise();
			 if(maxYear == null){
				//maxYear = exerciseService.getMaxYear();
				 maxYear =  exerciseService.getLastClosedExercise();
				 if(maxYear == null){
					Calendar c = Calendar.getInstance(); 
					maxYear =c.get(Calendar.YEAR);
				 } 
			 }
			if(maxYear != null){
				Exercise currentExercise =   getExerciseService().getOneFromYear(maxYear);
				getServletContext().setAttribute("currentExercise", currentExercise);	
			}	
			*/		
    		
			 request.getSession(false).setAttribute("successMessage", "The exercise  " + exercise.getYear() + " has been successfully approved");
 			 ajaxReply.put("result", "ok");
 			 response.setContentType("text/json;charset=UTF-8");
 		    	ServletOutputStream out = response.getOutputStream();				
 		    	String reply = ajaxReply.toString();
 				out.write(reply.getBytes("UTF-8"));
 				out.flush();
 				out.close();
 				return null;
    	 	}catch(BusinessException be){
    	 		request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
 			 //return  showGeneralBudgetPage( request, response);
 	   	 	 ajaxReply.put("result", "nok");
 	   	 	response.setContentType("text/json;charset=UTF-8");
 	    	ServletOutputStream out = response.getOutputStream();
 			
 	    	String reply = ajaxReply.toString();
 			out.write(reply.getBytes("UTF-8"));
 			out.flush();
 			out.close();
 			return null;
 		 }
			
	}
    
    
    public ModelAndView updateBudgetByQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
	 		try{	  
	 			BudgetGeneralView budgetview = (BudgetGeneralView) request.getSession(false).getAttribute(BUDGETS_KEY);
	 			if(budgetview != null){
	 				
	 				List<AnnualBudgetData>  budgets = budgetview.getBudgets();
	 				for(AnnualBudgetData data : budgets){
	 					
	 					if(data.getExpectedAmountEdit() != data.getExpectedAmount()){
	 						data.setExpectedAmount(data.getExpectedAmountEdit());
	 						
	 					}
	 					
	 					if(data.getReportedAmountEdit() != data.getReportedAmount()){
	 						data.setReportedAmount(data.getReportedAmountEdit());
	 						
	 					}
	 					
	 					
	 				}
	 				
	 			}
	 			return null;
				 
	    	 	}catch(BusinessException be){
	    	 		request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
	 			    return  showGeneralBudgetPage( request, response);	 			   
	 		 }
	}
    
    
    
    public ModelAndView applyInflationPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
		 //JSONObject ajaxReply = new JSONObject();    	
	 		try{	  
	 			 String idExerciseAsString = request.getParameter("exerciseId");
				 Long idExercise = Long.parseLong(idExerciseAsString);
	 			 String inflationAsString = request.getParameter("inflation");
	   		 	 float inflation = Float.parseFloat(inflationAsString);
	   		     com.interaudit.domain.model.Exercise exercise =  exerciseService.applyInflation(inflation,idExercise);
				 request.getSession(false).setAttribute("successMessage", "The exercise " + exercise.getYear() + " has been successfully updated");
				 return  showGeneralBudgetPage( request, response);
				 /*
				 ajaxReply.put("result", "ok");
	 			 response.setContentType("text/json;charset=UTF-8");
	 		    	ServletOutputStream out = response.getOutputStream();				
	 		    	String reply = ajaxReply.toString();
	 				out.write(reply.getBytes("UTF-8"));
	 				out.flush();
	 				out.close();
	 				return null;
	 				*/
	    	 	}catch(BusinessException be){
	    	 		request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
	 			    return  showGeneralBudgetPage( request, response);
	 			    /*
	 	   	 	 ajaxReply.put("result", "nok");
	 	   	 	response.setContentType("text/json;charset=UTF-8");
	 	    	ServletOutputStream out = response.getOutputStream();
	 			
	 	    	String reply = ajaxReply.toString();
	 			out.write(reply.getBytes("UTF-8"));
	 			out.flush();
	 			out.close();
	 			return null;
	 			*/
	 		 }
	}
    
    public ModelAndView closeExercise(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	/*
    	try{
   		 	 String idExerciseAsString = request.getParameter("id");
   		 	 Long idExercise = Long.parseLong(idExerciseAsString);
   		     
			 return  showGeneralBudgetPage( request, response);	
		 }catch(BusinessException be){
			 request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
			 return  showGeneralBudgetPage( request, response);
		 }
		 
		*/
	     	JSONObject ajaxReply = new JSONObject();    	
	 		try{	   		 
	 			 String idExerciseAsString = request.getParameter("id");
	    		 Long idExercise = Long.parseLong(idExerciseAsString);
	    		 com.interaudit.domain.model.Exercise exercise =  exerciseService.closeExercise(idExercise);
	    		 getServletContext().setAttribute("currentExercise",exercise);	    		
				 request.getSession(false).setAttribute("successMessage", "The exercise : " + exercise.getYear() + " has been successfully closed");
	 			 ajaxReply.put("result", "ok");
	 			 response.setContentType("text/json;charset=UTF-8");
	 		    	ServletOutputStream out = response.getOutputStream();				
	 		    	String reply = ajaxReply.toString();
	 				out.write(reply.getBytes("UTF-8"));
	 				out.flush();
	 				out.close();
	 				return null;
	    	 	}catch(BusinessException be){
	    	 		request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
	 			 //return  showGeneralBudgetPage( request, response);
	 	   	 	 ajaxReply.put("result", "nok");
	 	   	 	response.setContentType("text/json;charset=UTF-8");
	 	    	ServletOutputStream out = response.getOutputStream();
	 			
	 	    	String reply = ajaxReply.toString();
	 			out.write(reply.getBytes("UTF-8"));
	 			out.flush();
	 			out.close();
	 			return null;
	 		 }
	}
    
    
    public ModelAndView referenceBudgetPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	/*
    	try{
   		 	 
			 return  showGeneralBudgetPage( request, response);	
		 }catch(BusinessException be){
			 request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
			 return  showGeneralBudgetPage( request, response);
		 }	
		 */
		 
		 JSONObject ajaxReply = new JSONObject();    	
	 		try{	   		 
	 			String idExerciseAsString = request.getParameter("id");
	   		 	 Long idExercise = Long.parseLong(idExerciseAsString);
	   		     com.interaudit.domain.model.Exercise exercise =  exerciseService.referenceBudgetPage(idExercise);
				 request.getSession(false).setAttribute("successMessage", "The exercise : " + exercise.getYear() + " has been successfully tagged");
	 			 ajaxReply.put("result", "ok");
	 			 response.setContentType("text/json;charset=UTF-8");
	 		    	ServletOutputStream out = response.getOutputStream();				
	 		    	String reply = ajaxReply.toString();
	 				out.write(reply.getBytes("UTF-8"));
	 				out.flush();
	 				out.close();
	 				return null;
	    	 	}catch(BusinessException be){
	    	 		request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
	 			 //return  showGeneralBudgetPage( request, response);
	 	   	 	 ajaxReply.put("result", "nok");
	 	   	 	response.setContentType("text/json;charset=UTF-8");
	 	    	ServletOutputStream out = response.getOutputStream();
	 			
	 	    	String reply = ajaxReply.toString();
	 			out.write(reply.getBytes("UTF-8"));
	 			out.flush();
	 			out.close();
	 			return null;
	 		 }
	}
	
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionWorkshopPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("budget_workshop");		
	}
    
    public ModelAndView handleBudgetItem(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String budgetId = request.getParameter("budgetId");
    	String page = request.getParameter("page");
    	String startingLetterName = request.getParameter("startingLetterName");    	
		String year = request.getParameter("year");
		String prevision = request.getParameter("prevision");
		String reported = request.getParameter("reported");
		String comments = request.getParameter("comments");
		String customerCode = request.getParameter("customer");
		String contractCode = request.getParameter("contract");
		String associeId = request.getParameter("associate");
		String managerId = request.getParameter("manager");
		String status = request.getParameter("status");
		String fiable = request.getParameter("fiable");
		String mandat = request.getParameter("mandat");
		String interim = request.getParameter("interim");
		
		AnnualBudgetData annualBudgetData =  new AnnualBudgetData(
																	budgetId == null || budgetId.trim().length()==0 ? null: Long.valueOf(budgetId),
																	year == null || year.trim().length()==0? -1: Integer.parseInt(year), 
																	null, 
																	customerCode,
																	prevision == null || prevision.trim().length()==0? 0.0: Double.parseDouble(prevision), 
																	reported == null || reported.trim().length()==0? 0.0: Double.parseDouble(reported),
																	0.0,
																	status,
																	null,
																	null,
																	null,
																	null,
																	associeId == null || associeId.trim().length()==0? -1L: Long.valueOf(associeId), 
																	managerId == null || managerId.trim().length()==0? -1L: Long.valueOf(managerId),
																	contractCode,
																	comments,
																	null,
																	fiable == null || fiable.trim().length()==0? false:true,
																	0.0,
																	0.0,
																	mandat,null,false,interim == null ||interim.trim().length()==0? false:true,
																	null,
																	false
																			
																	
				) ;
		
		try{
			AnnualBudgetData budgetIdReturned = budgetService.saveOrUpdateBudget(annualBudgetData);
			int exercise = Integer.parseInt(year);
    		Exercise exerciseUpdated = exerciseService.recalculateExercise(exercise);
			request.getSession(false).setAttribute("successMessage", "Budget [ " + budgetIdReturned.getCustomerName() + " ] has been successfully managed for exercise "+ year);
		}catch(BusinessException be){
			request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
		
		}
		//return showGeneralBudgetPage( request, response);
		request.getSession(false).removeAttribute(BUDGETS_KEY);
    	request.getSession(false).removeAttribute(EXERCISES_KEY);    	
		return new ModelAndView("redirect:/showGeneralBudgetPage.htm?startingLetterName="+startingLetterName +"&p="+page);
    }
    
    public ModelAndView deleteBudgetItem(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	String budgetId = request.getParameter("budgetId");
    	String year = request.getParameter("year");
    	try{
    	if(budgetId != null && budgetId.length() > 0){
    		Long identifier = Long.parseLong(budgetId);
    		AnnualBudget budget = budgetService.getOneDetachedFromContract(identifier);
    		//String customerName = budget.getContract().getCustomer().getCompanyName();
    		//Exercise exercise = exerciseService.getOneDetached(budget.getExercise().getId());
    		int exercise = Integer.parseInt(year);
    		budgetService.deleteOne(identifier);
    		//budgetService.cancelOne(identifier);
    		Exercise exerciseUpdated = exerciseService.recalculateExercise(exercise);
    		//AnnualBudgetData budgetData  = budgetService.getOneAnnualBudgetDataFromId(budget.getId());
    		request.getSession(false).setAttribute("successMessage", "Budget has been successfully deleted from exercise " + year);
    	}
    	}catch(Exception be){
			request.getSession(false).setAttribute("actionErrors", be.getMessage());
			
		}
    	
    	//return showGeneralBudgetPage( request, response);
    	return new ModelAndView("redirect:/showGeneralBudgetPage.htm?forceReload=true");
    }
    
    public ModelAndView editBudgetItem(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String budgetId = request.getParameter("budgetId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (budgetId != null){	
			
				BudgetGeneralView budgetDetails = (BudgetGeneralView)request.getSession(false).getAttribute(BUDGETS_KEY) ;
				if(budgetDetails == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				AnnualBudgetData budgetTofind = null;
				for(AnnualBudgetData budget : budgetDetails.getBudgets()){
					if(budget.getId().toString().equalsIgnoreCase(budgetId)){
						budgetTofind = budget;
						break;
					}
				}
				
				
				if( budgetTofind != null){
					ajaxReply.put("result", "ok");
					ajaxReply.put("budgetId",budgetTofind.getId().toString());
					ajaxReply.put("year",budgetTofind.getYear());
					ajaxReply.put("currency","EUR");
					ajaxReply.put("prevision",budgetTofind.getExpectedAmount());
					ajaxReply.put("reported",budgetTofind.getReportedAmount());
					ajaxReply.put("comments",budgetTofind.getComments()== null?"":budgetTofind.getComments());
					ajaxReply.put("customer",budgetTofind.getCustomerCode());
					ajaxReply.put("customerName",budgetTofind.getCustomerName());
					ajaxReply.put("contract",budgetTofind.getContractCode());
					ajaxReply.put("status",budgetTofind.getStatus());
					ajaxReply.put("associate",budgetTofind.getAssociateId());
					ajaxReply.put("manager",budgetTofind.getManagerId());
					ajaxReply.put("fiable",budgetTofind.isFiable());
					ajaxReply.put("mandat",budgetTofind.getMandat());
					ajaxReply.put("interim",budgetTofind.getInterim());
					//no password as it is only sent from client to server
					
					response.setContentType("text/json;charset=UTF-8");
			}else{			
				ajaxReply.put("result", "nok");
			    response.setContentType("application/json;charset=UTF-8");
			}				
			ServletOutputStream out = response.getOutputStream();
			String reply = ajaxReply.toString();	
			out.write(reply.getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
		}
		else{
			logger.error("budgetId null");
			return null;
		}
	}
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public ModelAndView showProfitabilityPerCustomerPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	//Clean the session scope first
		// WebUtils.cleanSession(request);
    	
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	SearchProfitabilityPerCustomerParam param = null;
    	
    	WebContext context = (WebContext)request.getSession(true).getAttribute("context");
    	
		 Long manager = (request.getParameter("profitability_manager") == null /*||request.getParameter("profitability_manager").equalsIgnoreCase("-1")*/)? null: Long.parseLong(request.getParameter("profitability_manager"));
		
		 Long associe = (request.getParameter("profitability_associe") == null /*||request.getParameter("profitability_associe").equalsIgnoreCase("-1")*/)? null: Long.parseLong(request.getParameter("profitability_associe"));
		 Long customer  = (request.getParameter("profitability_customer") == null /*||request.getParameter("profitability_customer").equalsIgnoreCase("-1")*/)? null: Long.parseLong(request.getParameter("profitability_customer"));
	    	/*
		 if(request.getSession(false).getAttribute("profitability_customer") == null){	
			  
			 request.getSession(false).setAttribute("profitability_customer",customer);
		 }
		 else{
			
			if(customer == null ){
				 customer =  (Long)request.getSession(false).getAttribute("profitability_customer");
			}
		 }
		*/
		if(customer == null ){
			 if(request.getSession(false).getAttribute("profitability_customer") != null){
				 customer =  (Long)request.getSession(false).getAttribute("profitability_customer");
			 }			
		}else{
			request.getSession(false).setAttribute("profitability_customer",customer);
		}
		
		if(manager == null ){
			 if(request.getSession(false).getAttribute("profitability_manager") != null){
				 manager =  (Long)request.getSession(false).getAttribute("profitability_manager");
			 }else{
				 Employee currentUser = context.getCurrentUser();
				 if(currentUser.getPosition().isManagerMission()){
					 manager =  currentUser.getId();
				 }
			 }
		}else{
			request.getSession(false).setAttribute("profitability_manager",manager);
		}
		
		if(associe == null ){
			 if(request.getSession(false).getAttribute("profitability_associe") != null){
				 associe =  (Long)request.getSession(false).getAttribute("profitability_associe");
			 }else{
				 Employee currentUser = context.getCurrentUser();
				 if(currentUser.getPosition().isPartner()){
					 associe =  currentUser.getId();
				 }
			 }			
		}else{
			request.getSession(false).setAttribute("profitability_associe",associe);
		}
		
		
		
		
		List<Integer> years = new ArrayList<Integer> ();
		 Enumeration<String> paramNames = request.getParameterNames();
		 while(paramNames.hasMoreElements()) {
		      String paramName = (String)paramNames.nextElement();
		      if (paramName.startsWith("year_")){
		    	  Integer year = Integer.valueOf(request.getParameter(paramName));
		    	  years.add(year);      
		      }
		 }	
		
		 
		 
		 if(years.isEmpty()){
			 if(request.getSession(false).getAttribute("profitability_years") == null){				
				 Integer currentYear = exerciseService.getFirstOnGoingExercise();
				 if(currentYear == null){
									//currentYear = exerciseService.getMaxYear();
					 				 currentYear =  exerciseService.getLastClosedExercise();
				 					 if(currentYear == null){
				 						Calendar c = Calendar.getInstance(); 
				 						currentYear =c.get(Calendar.YEAR);
				 					 } 
				 				 }
				 years.add(currentYear);		         
		         request.getSession(false).setAttribute("profitability_years",years);
			 }else{
				 years = (List<Integer>)request.getSession(false).getAttribute("profitability_years");
			 }
			 
		 }else{
			 request.getSession(false).setAttribute("profitability_years",years);
		 }
		
		 param = new SearchProfitabilityPerCustomerParam(customer, manager, associe,years); 
		 int firstPos = 0;
		 
		// SearchBudgetParam  param1 = new SearchBudgetParam(  null,null , null,  null,years,null,null,false); 
		 
		//Gestion de la pagination
		 
			int p;
			try {
				p = Integer.parseInt(request.getParameter(PAGE));
			} catch(NumberFormatException nfe) {
				p = 1;
			}
			firstPos = ((p - 1) * LINEPERPAGE);
			long count = budgetService.getTotalCountProfitabilityPerCustomer(param);
			
			int totalPage = (int)(count / LINEPERPAGE);
			if (count % LINEPERPAGE > 0) totalPage++;
			
		
			
	    	// param = new SearchProfitabilityPerCustomerParam(manager, associe);
	    	ProfitabilityPerCustomerView data =  budgetService.calculateProfitabilityPerCustomer(param,true,firstPos, LINEPERPAGE);
	    	mapResults.put(PROFITABILITY_KEY, data);
	    	mapResults.put(TOTAL_COUNT, count);		
	    	mapResults.put(TOTAL_PAGE_KEY, totalPage);
	    	mapResults.put(CURRENT_PAGE_KEY, p);
		
		
		return new ModelAndView("profitabily_customer",mapResults);		
	}
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showBudgetResultsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	//Clean the session scope first
		WebUtils.cleanSession(request);
		Map<String,Object> mapResults = new HashMap<String, Object>();
			ExerciseGeneralView exerciseGeneralView = null;
	    	Integer exercise = null;
	    	String year = request.getParameter("year");
	    	 
	    	if(year != null)
	    	 exercise = Integer.parseInt(year);
	    	else{
	    		
	    		//exerciseGeneralView = (ExerciseGeneralView) request.getSession(false).getAttribute(EXERCISES_KEY);
	        	if(exerciseGeneralView != null){    		
	        		return new ModelAndView("resultat_workshop",mapResults);
	        	}else{
	        		Integer currentYear = exerciseService.getFirstOnGoingExercise();
		    		if(currentYear == null){
						    			//currentYear = exerciseService.getMaxYear();
										 currentYear =  exerciseService.getLastClosedExercise();
		    							 if(currentYear == null){
		    								Calendar c = Calendar.getInstance(); 
		    								currentYear =c.get(Calendar.YEAR);
		    							 } 
		    						 }
		    		exercise =currentYear;
		    		
	        		
	        	}
	    		
	    	}
	    	exerciseGeneralView = exerciseService.getExerciseGeneralView(exercise);
    		request.getSession(false).setAttribute(EXERCISES_KEY, exerciseGeneralView);
    		mapResults.put(EXERCISES_KEY, exerciseGeneralView);
    		return new ModelAndView("resultat_workshop",mapResults);
    	
    		
    		
    	
				
	}
    
    
   
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    

    
    
    @SuppressWarnings("unchecked")
	public ModelAndView showBudgetResultsMensuelsLineChartPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	OutputStream out = response.getOutputStream();
    	XYDataset dataset = createDataset();
    		
    	JFreeChart chart = createChart(dataset) ;
    	
    	response.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(out, chart, 900, 500);
		
		return null;
		
	}
    
    
private XYDataset createDataset() {
	
	List<Exercise> exercises  = exerciseService.getAll();    
    final XYSeriesCollection dataset = new XYSeriesCollection();
    for(Exercise exercise : exercises){
    	final XYSeries series = new XYSeries(Integer.toString(exercise.getYear()));
    	ExerciseGeneralView exerciseGeneralView = exerciseService.getExerciseGeneralView(exercise.getYear());
    	series.add( 1.0d,(double)exerciseGeneralView.getTotalForManagersForMonth(0)); //Janvier
    	series.add(  2.0d,(double)exerciseGeneralView.getTotalForManagersForMonth(1));//"Fevrier"
    	series.add( 3.0,(double)exerciseGeneralView.getTotalForManagersForMonth(2));//"Mars"
    	series.add( 4.0,(double)exerciseGeneralView.getTotalForManagersForMonth(3));//"Avril"
    	series.add(  5.0,(double)exerciseGeneralView.getTotalForManagersForMonth(4));//"Mai"
    	series.add( 6.0,(double)exerciseGeneralView.getTotalForManagersForMonth(5));//"Juin"
    	series.add(7.0,(double)exerciseGeneralView.getTotalForManagersForMonth(6));//"Juillet"
    	series.add( 8.0,(double)exerciseGeneralView.getTotalForManagersForMonth(7));//"Aout"
    	series.add( 9.0,(double)exerciseGeneralView.getTotalForManagersForMonth(8));//"Septembre"
    	series.add(10.0,(double)exerciseGeneralView.getTotalForManagersForMonth(9));//"Octobre"
    	series.add( 11.0,(double)exerciseGeneralView.getTotalForManagersForMonth(10));//"Novembre"
    	series.add( 12.0,(double)exerciseGeneralView.getTotalForManagersForMonth(11) );//"Decembre"
		
    	dataset.addSeries(series);
    }
        /*
        final XYSeries series1 = new XYSeries("First");
        series1.add(1.0, 1.0);
        series1.add(2.0, 4.0);
        series1.add(3.0, 3.0);
        series1.add(4.0, 5.0);
        series1.add(5.0, 5.0);
        series1.add(6.0, 7.0);
        series1.add(7.0, 7.0);
        series1.add(8.0, 8.0);

        final XYSeries series2 = new XYSeries("Second");
        series2.add(1.0, 5.0);
        series2.add(2.0, 7.0);
        series2.add(3.0, 6.0);
        series2.add(4.0, 8.0);
        series2.add(5.0, 4.0);
        series2.add(6.0, 4.0);
        series2.add(7.0, 2.0);
        series2.add(8.0, 1.0);

        final XYSeries series3 = new XYSeries("Third");
        series3.add(3.0, 4.0);
        series3.add(4.0, 3.0);
        series3.add(5.0, 2.0);
        series3.add(6.0, 3.0);
        series3.add(7.0, 6.0);
        series3.add(8.0, 3.0);
        series3.add(9.0, 4.0);
        series3.add(10.0, 3.0);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
                */
        return dataset;
        
    }


private JFreeChart createChart(final XYDataset dataset) {
    
    // create the chart...
    final JFreeChart chart = ChartFactory.createXYLineChart(
        "Total facture par mois",      // chart title
        "Mois", // domain axis label
		"Factures", // range axis label
        dataset,                  // data
        PlotOrientation.VERTICAL,
        true,                     // include legend
        true,                     // tooltips
        false                     // urls
    );

    // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
    chart.setBackgroundPaint(Color.white);
/*
    final StandardLegend legend = (StandardLegend) chart.getLegend();
    legend.setDisplaySeriesShapes(true);
    */
    
    // get a reference to the plot for further customisation...
    final XYPlot plot = chart.getXYPlot();
    plot.setBackgroundPaint(Color.lightGray);
    //plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
    plot.setDomainGridlinePaint(Color.white);
    plot.setRangeGridlinePaint(Color.white);
    
    final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
   // renderer.setSeriesLinesVisible(0, false);
   // renderer.setSeriesShapesVisible(1, false);
    plot.setRenderer(renderer);

    // change the auto tick unit selection to integer units only...
   // final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    
    SymbolAxis sa = new SymbolAxis("Mois",
    	    new String[]{"","Janv","Fevr","Mars","Avril","Mai","Juin","Juillet","Aout","Sept","Oct","Nov","Dec"});
    plot.setDomainAxis(sa);
    // OPTIONAL CUSTOMISATION COMPLETED.
            
    return chart;
    
}
    
    
    private void buildChart(HttpServletResponse response) throws Exception{

    	 OutputStream out = response.getOutputStream();
		try {
			   
				Color colors[]={Color.RED,Color.BLUE};
			    List<Exercise> exercises  = exerciseService.getAll();
			    List<DefaultCategoryDataset> datasets = new ArrayList<DefaultCategoryDataset>();
			    
			    for(Exercise exercise : exercises){
			    	final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			    	ExerciseGeneralView exerciseGeneralView = exerciseService.getExerciseGeneralView(exercise.getYear());
			    	dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(0), Integer.toString(exercise.getYear()), "Janvier");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(1), Integer.toString(exercise.getYear()), "Fevrier");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(2), Integer.toString(exercise.getYear()), "Mars");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(3), Integer.toString(exercise.getYear()), "Avril");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(4), Integer.toString(exercise.getYear()), "Mai");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(5), Integer.toString(exercise.getYear()), "Juin");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(6), Integer.toString(exercise.getYear()), "Juillet");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(7), Integer.toString(exercise.getYear()), "Aout");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(8), Integer.toString(exercise.getYear()), "Septembre");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(9), Integer.toString(exercise.getYear()), "Octobre");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(10), Integer.toString(exercise.getYear()), "Novembre");
					dataset.addValue(exerciseGeneralView.getTotalForManagersForMonth(11), Integer.toString(exercise.getYear()), "Decembre");
					datasets.add(dataset);
			    	
			    }
				
				String title= "Total facture par mois";
				
				// create the chart...
				JFreeChart chart = ChartFactory.createLineChart(
				title, // chart title
				"Mois", // domain axis label
				"Factures", // range axis label
				datasets.get(0), // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);
				
		        chart.setBackgroundPaint(Color.white);

		        CategoryPlot plot = chart.getCategoryPlot();
		        plot.setBackgroundPaint(Color.lightGray);
		        plot.setRangeGridlinePaint(Color.white);

				//CategoryPlot plot2 = (CategoryPlot) chart.getPlot();
				plot.setDomainGridlinesVisible(true);
				plot.setRangeGridlinesVisible(true);
				
				 final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
				
			     //renderer.setDrawShapes(true);
				 renderer.setSeriesPaint(0, Color.RED);
				 
				 renderer.setDrawOutlines(true);
				
				 //renderer.setDrawLines(true)

			        renderer.setSeriesStroke(
			            0, new BasicStroke(
			                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
			                1.0f, new float[] {10.0f, 6.0f}, 0.0f
			            )
			        );
			        renderer.setSeriesStroke(
			            1, new BasicStroke(
			                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
			                1.0f, new float[] {6.0f, 6.0f}, 0.0f
			            )
			        );
			        renderer.setSeriesStroke(
			            2, new BasicStroke(
			                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
			                1.0f, new float[] {2.0f, 6.0f}, 0.0f
			            )
			        );
			        
			        plot.setDataset(1, datasets.get(1));
					//renderer.setPaint(Color.BLUE);
					renderer.setSeriesPaint(1, Color.BLUE);
					plot.setRenderer(1, renderer);
					
			       
				       
				response.setContentType("image/png");
				ChartUtilities.writeChartAsPNG(out, chart, 900, 500);
				}
		catch (Exception e) {
			logger.error(e.toString());
		}
		finally {
		out.close();
		}
		
    }
    
    
    public ModelAndView showBudgetResultsMensuelsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    		
    	return new ModelAndView("resultat_workshop_mensuel");		
	}
    
    public ModelAndView showBudgetResultsTrimestrielsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    		
    	return new ModelAndView("resultat_workshop_trimestriel");		
	}
    
    
    
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   
    
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showDocumentsManagementPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("list_documents");		
	}

	public IBudgetService getBudgetService() {
		return budgetService;
	}

	public void setBudgetService(IBudgetService budgetService) {
		this.budgetService = budgetService;
	}



	public IExerciseService getExerciseService() {
		return exerciseService;
	}



	public void setExerciseService(IExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	
    


	

	



	



	



	

	

	
}
