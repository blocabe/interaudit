package com.interaudit.web.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.domain.model.AddtionalFeeInvoice;
import com.interaudit.domain.model.DeductionFeeInvoice;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Payment;
import com.interaudit.domain.model.RemindInvoice;
import com.interaudit.domain.model.UserAction;
import com.interaudit.domain.model.data.InvoiceData;
import com.interaudit.domain.model.data.MissionBudgetData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.TimesheetData;
import com.interaudit.service.IBankService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IExerciseService;
import com.interaudit.service.IFactureService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.IPaymentService;
import com.interaudit.service.ITimesheetService;
import com.interaudit.service.IUserActionService;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExcessiveAmountException;
import com.interaudit.service.impl.RepositoryService;
import com.interaudit.service.param.SearchBalanceAgeeParam;
import com.interaudit.service.param.SearchInvoiceParam;
import com.interaudit.service.param.SearchInvoiceReminderParam;
import com.interaudit.service.param.SearchPaymentParam;
import com.interaudit.service.view.BalanceAgeeView;
import com.interaudit.service.view.InvoiceReminderView;
import com.interaudit.service.view.InvoiceView;
import com.interaudit.service.view.PaymentsView;
import com.interaudit.util.Constants;
import com.interaudit.util.WebContext;



/**
 * The Welcome action.
 * 
 */
public class InvoiceController extends MultiActionController {

   // private final Log log = LogFactory.getLog(HandleBrowseDatasetPageController.class);
	private static final String INVOICES_KEY = "viewinvoices";
	private static final String PAYMENTS_KEY = "viewpayments";
	private static final String INVOICE_KEY = "viewInvoiceDetails";
	private static final String INVOICE_CUSTOMER_NAME_KEY = "viewCustomerName";
	private static final String INVOICES_REMINDER_KEY ="viewinvoicereminders";
	private static final String BALANCEAGEE_KEY="viewbalanceagee";
	
	
	private static final String INVOICE_PENDING_STATUS = "invoice_pending_status";
	private static final String INVOICE_APPROVED_STATUS = "invoice_approved_status";
	private static final String INVOICE_ONGOING_STATUS = "invoice_ongoing_status";
	private static final String INVOICE_PAID_STATUS = "invoice_paid_status";
	
	private static final String INVOICE_YEAR_KEY = "invoice_year_key";
	private static final String INVOICE_CUSTOMER_KEY = "invoice_customer_key";
	private static final String INVOICE_TYPE_KEY = "invoice_type_key";
	private static final String INVOICE_MOIS_KEY = "invoice_mois_key";
	private static final String PAGE = "p";
	private static final int LINEPERPAGE = 30;
	
	private static final String CURRENT_PAGE_KEY = "currentPage";
	private static final String TOTAL_PAGE_KEY = "totalPage";	
	private static final String TOTAL_COUNT ="totalCount";
	
	
	
	
	private IPaymentService paymentService;
	private IFactureService service;
	private IMissionService missionService;
	private ITimesheetService timesheetService;
	private IUserActionService userActionService;
	private ICustomerService customerService;
	private IBankService bankService;
	private IExerciseService exerciseService;
	
	
   
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showInvoicesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	context.setCurrentInvoice(null);    	   	 
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	String pending = "";
    	String approved= "";
    	String ongoing = "";
    	String paid = "";
    	String cancelled = "";
    	String year =null;
    	String  customer;
    	String  type;
    	int firstPos = 0;		
		int totalPage = 0;
		long count = 0;
		int p;
    	//Gestion de la pagination
    	
		try {
			p = Integer.parseInt(request.getParameter(PAGE));			
		} catch(NumberFormatException nfe) {
			p = 1;
		}
    	String mois = request.getParameter("mois");
    	customer = request.getParameter("customer");
    	type = request.getParameter("type");
    	
    	pending = request.getParameter("pending");
    	approved = request.getParameter("approved");
    	ongoing = request.getParameter("ongoing");
    	paid = request.getParameter("paid");
    	cancelled = request.getParameter("cancelled");
    	year = request.getParameter("year");
    
    	
    	
    	//Gestion de la variable année
    	if(year == null ){    		
    		if(request.getSession(false).getAttribute(INVOICE_YEAR_KEY) == null){    			
    			Integer currentYear = exerciseService.getFirstOnGoingExercise();
    			if(currentYear == null){
				    				//currentYear = exerciseService.getMaxYear();
					 				 currentYear =  exerciseService.getLastClosedExercise();
    								 if(currentYear == null){
    									Calendar c = Calendar.getInstance(); 
    									currentYear =c.get(Calendar.YEAR);
    								 } 
    							 }
        		year = Integer.toString(currentYear) ;        		
        		request.getSession(false).setAttribute(INVOICE_YEAR_KEY,year);    			
    		}
    		else{
    			year = (String)request.getSession(false).getAttribute(INVOICE_YEAR_KEY);
    			/*
    			if(year.equalsIgnoreCase("") ){
    	    		Integer currentYear = exerciseService.getFirstOnGoingExercise();
    				if(currentYear == null){
    				    				//currentYear = exerciseService.getMaxYear();
    					 				 currentYear =  exerciseService.getLastClosedExercise();
    									 if(currentYear == null){
    										Calendar c = Calendar.getInstance(); 
    										currentYear =c.get(Calendar.YEAR);
    									 } 
    								 }
    	    		year = Integer.toString(currentYear) ;        		
    	    		request.getSession(false).setAttribute(INVOICE_YEAR_KEY,year);    	
    			}
    			*/
    		}    	    		
    	}
    	else{   
    		if(!year.equalsIgnoreCase("-1") ){
    			request.getSession(false).setAttribute(INVOICE_YEAR_KEY,year);
    		}
    		
    	}
    	
    	
    	
    	
    	
    	//Gestion de la variable mois
    	if(mois == null ){
    		if(request.getSession(false).getAttribute(INVOICE_MOIS_KEY) == null){
    			Calendar c = Calendar.getInstance(); 
				mois =Integer.toString(c.get(Calendar.MONTH));
				request.getSession(false).setAttribute(INVOICE_MOIS_KEY,mois);   
    		}
    		else{
    			mois = (String)request.getSession(false).getAttribute(INVOICE_MOIS_KEY);
    		}    	
    	}
    	else{
    		if(!mois.equalsIgnoreCase("-1") ){
    			request.getSession(false).setAttribute(INVOICE_MOIS_KEY,mois); 	
    		}
    		
    	}
    	
    	//Gestion de la variable customer
    	if(customer == null ){
    		if(request.getSession(false).getAttribute(INVOICE_CUSTOMER_KEY) == null){    			
				request.getSession(false).setAttribute(INVOICE_CUSTOMER_KEY,null);   
    		}
    		else{
    			customer = (String)request.getSession(false).getAttribute(INVOICE_CUSTOMER_KEY);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_CUSTOMER_KEY,customer); 
    	}
    	
    	
    	//Gestion de la variable type
    	if(type == null ){
    		if(request.getSession(false).getAttribute(INVOICE_TYPE_KEY) == null){    			
				request.getSession(false).setAttribute(INVOICE_TYPE_KEY,null);   
    		}
    		else{
    			type = (String)request.getSession(false).getAttribute(INVOICE_TYPE_KEY);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_TYPE_KEY,type); 
    	}
    	
    	
    	
    	//Gestion de la variable pending
    	if(pending == null ){
    		if(request.getSession(false).getAttribute(INVOICE_PENDING_STATUS) == null){    			
				request.getSession(false).setAttribute(INVOICE_PENDING_STATUS,null);   
    		}
    		else{
    			request.getSession(false).setAttribute(INVOICE_PENDING_STATUS,null);
    			//pending = (String)request.getSession(false).getAttribute(INVOICE_PENDING_STATUS);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_PENDING_STATUS,pending); 
    	}
    	
    	//Gestion de la variable approved
    	if(approved == null ){
    		if(request.getSession(false).getAttribute(INVOICE_APPROVED_STATUS) == null){    			
				request.getSession(false).setAttribute(INVOICE_APPROVED_STATUS,null);   
    		}
    		else{
    			request.getSession(false).setAttribute(INVOICE_APPROVED_STATUS,null);
    			//approved = (String)request.getSession(false).getAttribute(INVOICE_APPROVED_STATUS);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_APPROVED_STATUS,approved); 
    	}
    	
    	//Gestion de la variable ongoing
    	if(ongoing == null ){
    		if(request.getSession(false).getAttribute(INVOICE_ONGOING_STATUS) == null){    			
				request.getSession(false).setAttribute(INVOICE_ONGOING_STATUS,null);   
    		}
    		else{
    			request.getSession(false).setAttribute(INVOICE_ONGOING_STATUS,null);   
    			//ongoing = (String)request.getSession(false).getAttribute(INVOICE_ONGOING_STATUS);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_ONGOING_STATUS,ongoing); 
    	}
    	
    	//Gestion de la variable paid
    	if(paid == null ){
    		if(request.getSession(false).getAttribute(INVOICE_PAID_STATUS) == null){    			
				request.getSession(false).setAttribute(INVOICE_PAID_STATUS,null);   
    		}
    		else{
    			request.getSession(false).setAttribute(INVOICE_PAID_STATUS,null);   
    			//paid = (String)request.getSession(false).getAttribute(INVOICE_PAID_STATUS);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_PAID_STATUS,paid); 
    	}
    	
    	//Statuts par défauklt à afficher
    	
    	if(/*pending == null &&*/ approved == null && ongoing == null && paid == null && cancelled == null){
    		 //pending = "1";
        	approved= "1";
        	ongoing ="1"; 
        	paid ="1";
    	}    	
    
    
    	
    	
    	SearchInvoiceParam param = new SearchInvoiceParam( year, mois,
    			 pending,
    			 approved,
    			 ongoing,
    			 paid,
    			 cancelled,
    			 customer, 
    			 type);
    	
    	firstPos = ((p - 1) * LINEPERPAGE);
		count = service.getTotalCount(param);
		logger.info("Total entries found : " + count);
		
		totalPage = (int)(count / LINEPERPAGE);
		if (count % LINEPERPAGE > 0) totalPage++;
		
		logger.info("Total page found : " + totalPage);
		
    	InvoiceView view =  service.searchInvoices(param,true,firstPos, LINEPERPAGE);
    	mapResults.put(TOTAL_COUNT, count);		
		mapResults.put(TOTAL_PAGE_KEY, totalPage);
		mapResults.put(CURRENT_PAGE_KEY, p);
    	mapResults.put(INVOICES_KEY, view);
		return new ModelAndView("list_invoices",mapResults);		
	}
    
    
    public ModelAndView showBalanceAgeePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	context.setCurrentInvoice(null);    	   	 
    	Map<String,Object> mapResults = new HashMap<String, Object>();    	
   	
    	String customerCode = request.getParameter("customerCode");
    	SearchBalanceAgeeParam param = new SearchBalanceAgeeParam( customerCode);
    	
    	BalanceAgeeView  view =  service.buildBalanceAgeeView(param);
    	mapResults.put(BALANCEAGEE_KEY, view);
		return new ModelAndView("list_balanceagee",mapResults);		
	}
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showDraftInvoicesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	context.setCurrentInvoice(null);    	   	 
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	String pending = "1";
    	String approved= null;
    	String ongoing = null;
    	String paid = null;
    	String cancelled = null;
    	String year =null;
    	String  customer;
    	String  type;
    	String mois = request.getParameter("mois");
    	customer = request.getParameter("customer");
    	type = request.getParameter("type");
    	/*
    	pending = request.getParameter("pending");
    	approved = request.getParameter("approved");
    	ongoing = request.getParameter("ongoing");
    	paid = request.getParameter("paid");
    	cancelled = request.getParameter("cancelled");
    	*/
    	year = request.getParameter("year");
    	
    	//Gestion de la variable année
    	if(year == null ){    		
    		if(request.getSession(false).getAttribute(INVOICE_YEAR_KEY) == null){    			
    			Integer currentYear = exerciseService.getFirstOnGoingExercise();
    	    	 if(currentYear == null){
    	    	 					 //currentYear = exerciseService.getMaxYear();
    	    		 				 currentYear =  exerciseService.getLastClosedExercise();
    	    	 					 if(currentYear == null){
    	    	 						Calendar c = Calendar.getInstance(); 
    	    	 						currentYear =c.get(Calendar.YEAR);
    	    	 					 } 
    	    	 				 }
        		year = Integer.toString(currentYear) ;        		
        		request.getSession(false).setAttribute(INVOICE_YEAR_KEY,year);    			
    		}
    		else{
    			year = (String)request.getSession(false).getAttribute(INVOICE_YEAR_KEY);
    		}    	    		
    	}
    	else{    		
    		request.getSession(false).setAttribute(INVOICE_YEAR_KEY,year);
    	}
    	
    	//Any par défaut
    	if(mois == null ){
    		mois="-1";
    	}
    	
    	//Gestion de la variable mois
    	if(mois == null ){
    		if(request.getSession(false).getAttribute(INVOICE_MOIS_KEY) == null){
    			Calendar c = Calendar.getInstance(); 
				mois =Integer.toString(c.get(Calendar.MONTH));
				request.getSession(false).setAttribute(INVOICE_MOIS_KEY,mois);   
    		}
    		else{
    			mois = (String)request.getSession(false).getAttribute(INVOICE_MOIS_KEY);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_MOIS_KEY,mois); 
    	}
    	
    	//Gestion de la variable customer
    	if(customer == null ){
    		if(request.getSession(false).getAttribute(INVOICE_CUSTOMER_KEY) == null){    			
				request.getSession(false).setAttribute(INVOICE_CUSTOMER_KEY,null);   
    		}
    		else{
    			customer = (String)request.getSession(false).getAttribute(INVOICE_CUSTOMER_KEY);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_CUSTOMER_KEY,customer); 
    	}
    	
    	
    	//Gestion de la variable type
    	if(type == null ){
    		if(request.getSession(false).getAttribute(INVOICE_TYPE_KEY) == null){    			
				request.getSession(false).setAttribute(INVOICE_TYPE_KEY,null);   
    		}
    		else{
    			type = (String)request.getSession(false).getAttribute(INVOICE_TYPE_KEY);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_TYPE_KEY,type); 
    	}
    	
    	
    	
    	//Gestion de la variable pending
    	/*
    	if(pending == null ){
    		if(request.getSession(false).getAttribute(INVOICE_PENDING_STATUS) == null){    			
				request.getSession(false).setAttribute(INVOICE_PENDING_STATUS,null);   
    		}
    		else{
    			request.getSession(false).setAttribute(INVOICE_PENDING_STATUS,null);
    			//pending = (String)request.getSession(false).getAttribute(INVOICE_PENDING_STATUS);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_PENDING_STATUS,pending); 
    	}
    	
    	//Gestion de la variable approved
    	if(approved == null ){
    		if(request.getSession(false).getAttribute(INVOICE_APPROVED_STATUS) == null){    			
				request.getSession(false).setAttribute(INVOICE_APPROVED_STATUS,null);   
    		}
    		else{
    			request.getSession(false).setAttribute(INVOICE_APPROVED_STATUS,null);
    			//approved = (String)request.getSession(false).getAttribute(INVOICE_APPROVED_STATUS);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_APPROVED_STATUS,approved); 
    	}
    	
    	//Gestion de la variable ongoing
    	if(ongoing == null ){
    		if(request.getSession(false).getAttribute(INVOICE_ONGOING_STATUS) == null){    			
				request.getSession(false).setAttribute(INVOICE_ONGOING_STATUS,null);   
    		}
    		else{
    			request.getSession(false).setAttribute(INVOICE_ONGOING_STATUS,null);   
    			//ongoing = (String)request.getSession(false).getAttribute(INVOICE_ONGOING_STATUS);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_ONGOING_STATUS,ongoing); 
    	}
    	
    	//Gestion de la variable paid
    	if(paid == null ){
    		if(request.getSession(false).getAttribute(INVOICE_PAID_STATUS) == null){    			
				request.getSession(false).setAttribute(INVOICE_PAID_STATUS,null);   
    		}
    		else{
    			request.getSession(false).setAttribute(INVOICE_PAID_STATUS,null);   
    			//paid = (String)request.getSession(false).getAttribute(INVOICE_PAID_STATUS);
    		}    	
    	}
    	else{
    		request.getSession(false).setAttribute(INVOICE_PAID_STATUS,paid); 
    	}
    	*/
    	  	
    	int firstPos = 0;		
		int totalPage = 0;
		long count = 0;
		int p;
    	//Gestion de la pagination
    	
		try {
			p = Integer.parseInt(request.getParameter(PAGE));			
		} catch(NumberFormatException nfe) {
			p = 1;
		}
		
    	
    	
    	
    	SearchInvoiceParam param = new SearchInvoiceParam( year, mois,
    			 pending,
    			 approved,
    			 ongoing,
    			 paid,
    			 cancelled,
    			 customer, 
    			 type);
    	
    	firstPos = ((p - 1) * LINEPERPAGE);
		count = service.getTotalCount(param);
		logger.info("Total entries found : " + count);
		
		totalPage = (int)(count / LINEPERPAGE);
		if (count % LINEPERPAGE > 0) totalPage++;
		
		logger.info("Total page found : " + totalPage);
    	InvoiceView view =  service.searchInvoices(param,true,firstPos, LINEPERPAGE);
    	mapResults.put(TOTAL_COUNT, count);		
		mapResults.put(TOTAL_PAGE_KEY, totalPage);
		mapResults.put(CURRENT_PAGE_KEY, p);
    	mapResults.put(INVOICES_KEY, view);
		return new ModelAndView("list_draft-invoices",mapResults);		
	}
    
    
    
    
    
    public ModelAndView findInvoiceByIdPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	context.setCurrentInvoice(null);
    	
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	String invoiceId = request.getParameter("invoiceId");
    	if(invoiceId != null){
    		invoiceId = invoiceId.trim();
    	}
    	
    	SearchInvoiceParam param = new SearchInvoiceParam(invoiceId);
    	
    	int firstPos = 0;		
		int totalPage = 0;
		long count = 0;
		int p;
    	//Gestion de la pagination
    	
		try {
			p = Integer.parseInt(request.getParameter(PAGE));			
		} catch(NumberFormatException nfe) {
			p = 1;
		}
		
		firstPos = ((p - 1) * LINEPERPAGE);
		count = service.getTotalCount(param);
		logger.info("Total entries found : " + count);
		
		totalPage = (int)(count / LINEPERPAGE);
		if (count % LINEPERPAGE > 0) totalPage++;
		
    	InvoiceView view =  service.getInvoiceFromIdentifier(param,true,firstPos, LINEPERPAGE);
    	
    	if(view.getInvoices() != null && view.getInvoices().size() == 1){
    		InvoiceData invoiceData = view.getInvoices().get(0);
    		 return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+invoiceData.getId());
    	}
    	else{
    		mapResults.put(TOTAL_COUNT, count);		
    		mapResults.put(TOTAL_PAGE_KEY, totalPage);
    		mapResults.put(CURRENT_PAGE_KEY, p);
    		mapResults.put(INVOICES_KEY, view);
    		return new ModelAndView("list_invoices",mapResults);	
    	}
    		
	}
    
    
   
	
	 /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showAdvancePropertyPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	String invoiceIdAstring = request.getParameter("id");
    	String customerNameIdAstring = request.getParameter("name");
    	Long invoiceIdentifier = Long.parseLong(invoiceIdAstring);
    	
    	Invoice facture = service.getOneDetached(invoiceIdentifier);
    	
    	mapResults.put(INVOICE_KEY, facture);
    	mapResults.put(INVOICE_CUSTOMER_NAME_KEY, customerNameIdAstring);
		request.getSession(false).setAttribute(INVOICE_KEY, facture);
		request.getSession(false).setAttribute(INVOICE_CUSTOMER_NAME_KEY, customerNameIdAstring);
 
		return new ModelAndView("invoice_advance_properties",mapResults);		
	}
    
    public ModelAndView showPaymentsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	String invoiceReference = request.getParameter("invoiceReference");
    	String bankCode = request.getParameter("bank");
		String customerNameLike = request.getParameter("customerNameLike");
		String fromDateAsString = request.getParameter("fromDate");
		String toDateAsString = request.getParameter("toDate");
		String year = request.getParameter("year");
		
		
		//Gestion de la variable année
    	if(year == null ){    		
    		if(request.getSession(false).getAttribute(INVOICE_YEAR_KEY) == null){    			
    			Integer currentYear = exerciseService.getFirstOnGoingExercise();
    			if(currentYear == null){
				    				//currentYear = exerciseService.getMaxYear();
					 				 currentYear =  exerciseService.getLastClosedExercise();
    								 if(currentYear == null){
    									Calendar c = Calendar.getInstance(); 
    									currentYear =c.get(Calendar.YEAR);
    								 } 
    							 }
        		year = Integer.toString(currentYear) ;        		
        		request.getSession(false).setAttribute(INVOICE_YEAR_KEY,year);    			
    		}
    		else{
    			year = (String)request.getSession(false).getAttribute(INVOICE_YEAR_KEY);
    		}    	    		
    	}
    	else{    		
    		request.getSession(false).setAttribute(INVOICE_YEAR_KEY,year);
    	}
    	
    	
    	
		SearchPaymentParam param = null;
		PaymentsView view = null;
		if(invoiceReference == null){
			Date fromDate = null;
			Date toDate = null;
			
			if( fromDateAsString != null){
				try{
					fromDate =  DateFormat.getInstance().parse(fromDateAsString);
				}
				catch(ParseException pex) {
					fromDate = null;
				}
			}
			if( toDateAsString != null){
				
				try{
					toDate =  DateFormat.getInstance().parse(toDateAsString);
				}
				catch(ParseException pex) {
					toDate = null;
				}
				
			}
			param =  new SearchPaymentParam( null,  bankCode,
	    			 customerNameLike,  fromDate,  toDate,year);
			view =  service.searchPayments(param);
		}else{
			param =  new SearchPaymentParam(invoiceReference.trim(),  null,
	    			 null,  null,  null,null);
			view =  service.getPaymentFromInvoiceIdentifier(param);
			
		}
    	
    	mapResults.put(PAYMENTS_KEY, view);
		return new ModelAndView("list_payments",mapResults);
		
	}
    
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showFinalBillPropertyPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("invoice_final_bill_properties");		
	}
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showInvoiceDocumentsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("invoice_documents");		
	}
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showInvoicePaymentPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("invoice_payments");		
	}
    
 
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showInvoiceReminderPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	 String year;	
    	 String active = "";
    	 String notActive = "";
    	 String sent= "";	
    	 String notSent= "";	
    	 String  customer;	
    	 String invoiceReminderId = "";
    	
    	
    	
    	 active = request.getParameter("active");
    	 notActive = request.getParameter("notActive");
    	 sent = request.getParameter("sent");
    	 notSent = request.getParameter("notSent");    	
    	 year = request.getParameter("year");
    	 if(year == null ){
    		 /*
    		Calendar c = Calendar.getInstance();
    		year = Integer.toString(c.get(Calendar.YEAR)) ;
    		*/
    		
    		 Integer currentYear = exerciseService.getFirstOnGoingExercise();
    		 if(currentYear == null){
				    			 //currentYear = exerciseService.getMaxYear();
				 				 currentYear =  exerciseService.getLastClosedExercise();
    		 					 if(currentYear == null){
    		 						Calendar c = Calendar.getInstance(); 
    		 						currentYear =c.get(Calendar.YEAR);
    		 					 } 
    		 				 }
    		year = Integer.toString(currentYear) ;
    		
    	 }
    	 customer = request.getParameter("customer");
    	 invoiceReminderId = request.getParameter("invoiceReminderId");
    	
    	 SearchInvoiceReminderParam param = null;
    	
    	 
    	 
    	 InvoiceReminderView view =  null;
    	 if(invoiceReminderId != null){   
    		 param = new  SearchInvoiceReminderParam( invoiceReminderId);
    		 view = service.getsearchInvoiceRemindersFromIdentifier(param);
    	 }
    	 else{
    		 param = new SearchInvoiceReminderParam( year,  active, notActive,  sent, notSent, customer) ;
    		 view = service.searchInvoiceReminders(param);
    	 }
    	 
    	mapResults.put(INVOICES_REMINDER_KEY, view);
    	
		return new ModelAndView("list_invoices-reminder",mapResults);
	}

    public ModelAndView changeFieldInvoiceStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	String status = request.getParameter("value");
		String idAsString = request.getParameter("id");
		
		Long id = Long.parseLong(idAsString);
		Invoice invoice = getService().getOneDetached(id);
		
		
    	if( invoice != null){
    		invoice.setStatus(status);
    		if("sent".equalsIgnoreCase(status)){
    			invoice.setSentDate( new java.sql.Timestamp(System.currentTimeMillis()));
    		}
    		getService().updateOne(invoice);
    		getService().updateExerciceAndBudget(invoice);
    	}
				
    	response.getWriter().append(status).flush();
    	
    	return null;
    	
    }
    
  
    
    
    public ModelAndView addDeductionToInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {    	
    	Long invoiceId = Long.parseLong(request.getParameter("invoiceId"));
    	String codeFrais = request.getParameter("frais");
    	String lang = request.getParameter("lang");
    	Long idFacture = service.addDeductionToInvoice(invoiceId,codeFrais,lang); 
    	service.updateInvoiceAmountTotal(idFacture);
	    return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+idFacture);
		
    }
    
    public ModelAndView removeDeductionFromInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {    	
    	Long deductionId = Long.parseLong(request.getParameter("deductionId"));
    	Long invoiceId = Long.parseLong(request.getParameter("invoiceId"));
    	service.removeDeductionFromInvoice(deductionId); 
    	service.updateInvoiceAmountTotal(invoiceId);
	    return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+invoiceId);
		
    }
    
    
    
    
  //updateFraisLibelle.htm
    public ModelAndView updateDeductionLibelle(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

    	try{
    		
    		String libelle = request.getParameter("translation");
            final String deductionId = request.getParameter("deductionId");
           // String invoiceId = request.getParameter("invoiceId");

            //Update the service layer
            DeductionFeeInvoice fee = service.updateDeductionLibelle(Long.parseLong(deductionId),libelle);
            
            response.setContentType("text/json");        
            response.getWriter().write(fee.getJustificationHtml());

            logger.info("Returning null");
            return null;
	    }
		catch(Exception e){
			throw e;
		}
        
    }
    
    
  //updateInvoiceFraisValue
    public ModelAndView updateInvoiceDeductionValue(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

    	try{
    		String value = request.getParameter("translation");
            final String deductionId = request.getParameter("deductionId");
            String invoiceId = request.getParameter("invoiceId");
            
            double valueNumber = Double.parseDouble(value);
            if(valueNumber < 0.0d){
            	throw new NumberFormatException();
            }

            DeductionFeeInvoice fee = service.updateInvoiceDeductionValue(Long.parseLong(deductionId),valueNumber);
            
            //Update the amounts
	        Invoice facture = service.updateInvoiceAmountTotal(Long.parseLong(invoiceId) );
            
            response.setContentType("text/json");        
            response.getWriter().write(Double.toString(fee.getValue()));

            logger.info("Returning null");
            return null;
	    }
		catch(Exception e){
			throw e;
		}
        
    }
    
    public ModelAndView updateInvoiceDateFacturation(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

    	try{
    		String value = request.getParameter("translation");            
            String invoiceId = request.getParameter("invoiceId");            
            Date dateFacturation = service.updateInvoiceDateFacturation(Long.parseLong(invoiceId),value);  
            if(dateFacturation == null)throw new Exception();
            response.setContentType("text/json");        
            response.getWriter().write(dateFacturation.toString());            
            return null;
	    }
		catch(Exception e){
			throw e;
		}
        
    }
    
    
    
    
    
    public ModelAndView addFraisToInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {    	
    	Long invoiceId = Long.parseLong(request.getParameter("invoiceId"));
    	String codeFrais = request.getParameter("frais");
    	String lang = request.getParameter("lang");
    	Long idFacture = service.addFraisToInvoice(invoiceId,codeFrais,lang); 
    	service.updateInvoiceAmountTotal(idFacture);
	    return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+idFacture);
		
    }
    
    public ModelAndView removeFraisFromInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {    	
    	Long fraisId = Long.parseLong(request.getParameter("fraisId"));
    	Long invoiceId = Long.parseLong(request.getParameter("invoiceId"));
    	service.removeFraisFromInvoice(fraisId); 
    	service.updateInvoiceAmountTotal(invoiceId);
	    return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+invoiceId);
		
    }
    
    
    public ModelAndView deleteInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {    
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Long invoiceId = Long.parseLong(request.getParameter("invoiceId"));
    	service.deleteOne(invoiceId);   
    	ArrayList<String> messages = new ArrayList<String>();
    	messages.add("The invoice has been successfully deleted...");
    	request.getSession(false).setAttribute("successMessage", messages);
    	return closeInvoicePage( request,response);
    			 
	   // return new ModelAndView("redirect:/showInvoicesPage.htm");
		
    }
    
    public ModelAndView deletePayment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {    
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Long paymentId = Long.parseLong(request.getParameter("paymentId"));
    	
    	//Search for the payment reference
    	Payment payment = paymentService.getOneDetached(paymentId);
    	String paymentReference = payment.getFacture().getReference().trim();
        Invoice invoice = service.getOneDetachedFromReference(paymentReference);
    	
        //Delete the payment
        service.deletePayment(paymentId);   
    	
    	//Updating the invoice status
		Invoice invoice2 = service.getOne(invoice.getId());
		service.updateInvoiceStatusafterPayment(invoice2);
		
		//Build the message to be displayed to the end user
    	ArrayList<String> messages = new ArrayList<String>();
    	messages.add("The payment has been successfully deleted...");
    	request.getSession(false).setAttribute("successMessage", messages);
    	
    	//Register user action
		userActionService.saveOne(new UserAction(context.getCurrentUser(), "SUPPRESION PAYMENT " + paymentReference, invoice2.getClass().getName(),
				invoice.getId(), Calendar.getInstance().getTime()) );
    	return showPaymentsPage( request,response);
    			 
	   // return new ModelAndView("redirect:/showInvoicesPage.htm");
		
    }
    
    
    
   
    
    public ModelAndView closeInvoicePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {    
    	String pending = request.getSession(false).getAttribute(INVOICE_PENDING_STATUS) == null?null:"1";
    	String approved = request.getSession(false).getAttribute(INVOICE_APPROVED_STATUS)==null?null:"1";
    	String ongoing = request.getSession(false).getAttribute(INVOICE_ONGOING_STATUS)==null?null:"1";
    	String paid = request.getSession(false).getAttribute(INVOICE_PAID_STATUS)==null?null:"1";
    	Long invoiceId = Long.parseLong(request.getParameter("invoiceId"));
        Invoice invoice = service.getOneDetached(invoiceId);
        
        
    	
    	StringBuffer requestParameters = null;
    	
    	if(pending != null){
    		if(requestParameters == null) requestParameters = new StringBuffer();
    			requestParameters.append("?pending=").append(pending);
    	}
    	
    	if(approved != null){
    		if(requestParameters == null) {
    			requestParameters = new StringBuffer();
    			requestParameters.append("?approved=").append(approved);
    		}
    		else{
    			requestParameters.append("&approved=").append(approved);
    		}    		
    	}
    	
    	if(ongoing != null){
    		if(requestParameters == null) {
    			requestParameters = new StringBuffer();
    			requestParameters.append("?ongoing=").append(ongoing);
    		}
    		else{
    			requestParameters.append("&ongoing=").append(ongoing);
    		}  
    		
    	}
    	
    	if(paid != null){
    		if(requestParameters == null) {
    			requestParameters = new StringBuffer();
    			requestParameters.append("?paid=").append(paid);
    		}
    		else{
    			requestParameters.append("&paid=").append(paid);
    		}      		
    	}
    	
	    //return new ModelAndView("redirect:/showInvoicesPage.htm?pending=" + pending + "&approved="+approved + "&ongoing=" +ongoing + "&paid=" +paid);
    	if(requestParameters == null){
    		if( invoice !=null && invoice.getStatus().equalsIgnoreCase(Invoice.FACTURE_STATUS_CODE_PENDING)){    			
    			return new ModelAndView("redirect:/showDraftInvoicesPage.htm");
    		}
    		else{
    			return new ModelAndView("redirect:/showInvoicesPage.htm");
    		}
    		
    	}else{
    		
    		if(invoice !=null && invoice.getStatus().equalsIgnoreCase(Invoice.FACTURE_STATUS_CODE_PENDING)){
    			return new ModelAndView("redirect:/showDraftInvoicesPage.htm" + requestParameters.toString());
    		}
    		else{
    			return new ModelAndView("redirect:/showInvoicesPage.htm" + requestParameters.toString());
    		}
    		
    	}
    	
		
    }
    
    
	public ModelAndView approveInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
		Long invoiceId = null;
		try{
			WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	    	invoiceId = Long.parseLong(request.getParameter("invoiceId"));
	    	Invoice invoice = service.approveInvoice(invoiceId,context.getCurrentUser());     	
	    	request.getSession(false).setAttribute("successMessage", "Invoice [ " + invoice.getReference() + " ] is approved");
			   
		}catch(BusinessException be){
			request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
		}
    	return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+invoiceId);
		
    }
	
	public ModelAndView markAsSentInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long invoiceId = null;
		try{
			WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	    	invoiceId = Long.parseLong(request.getParameter("invoiceId"));
	    	Invoice invoice = service.markAsSentInvoice(invoiceId,context.getCurrentUser()); 
	    	request.getSession(false).setAttribute("successMessage", "Invoice [ " + invoice.getReference() + " ] is marked as sent");
		   
		}catch(BusinessException be){
			request.getSession(false).setAttribute("actionErrors", be.getExcpetionMessages().get(0).getMessage());
		}
		return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+invoiceId);
    }
	
	public ModelAndView markAsSentReminderInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {  
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Long reminderId = Long.parseLong(request.getParameter("reminderId"));
    	RemindInvoice remindInvoice =  service.markAsSentReminderInvoice(reminderId,context.getCurrentUser());     	
	    return new ModelAndView("redirect:/editRemindInvoicePage.htm?reminderId="+reminderId);
		
    }
	
    
   
	
    
    public ModelAndView editInvoicePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	Long invoiceId = Long.parseLong(request.getParameter("invoiceId"));
    	Invoice facture = service.getOneDetached(invoiceId);
	    mapResults.put("invoice", facture);
		return new ModelAndView("invoice_editor",mapResults);
		//return new ModelAndView("redirect:/showInvoiceRegisterPage.htm?customerid="+customerid+"&type="+type+"&tva="+tvaAstring+"&honoraires="+honorairesAstring);
    }
    
    public ModelAndView editRemindInvoicePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	Long reminderId = Long.parseLong(request.getParameter("reminderId"));
    	RemindInvoice reminder = service.getOneRemindInvoiceDetached(reminderId);
	    mapResults.put("reminder", reminder);
		return new ModelAndView("reminder_editor",mapResults);
		//return new ModelAndView("redirect:/showInvoiceRegisterPage.htm?customerid="+customerid+"&type="+type+"&tva="+tvaAstring+"&honoraires="+honorairesAstring);
    }
    
    
    
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView selectCustomerForInvoicePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	 Map<String,Object> mapResults = new HashMap<String, Object>();
    	 WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	 
    	 //Initialize the current invoice to null
    	 context.setCurrentInvoice(null);
    	 
		 //Defaults to current year 
    	 /*
    	 Integer value = getExerciseService().getMaxYear();
    	 if(value == null){
				Calendar c = Calendar.getInstance(); 
				value =c.get(Calendar.YEAR);
			 }
		*/
    	 
    	 Integer currentYear = exerciseService.getFirstOnGoingExercise();
    	 if(currentYear == null){
    	 					 //currentYear = exerciseService.getMaxYear();
    		 				 currentYear =  exerciseService.getLastClosedExercise();
    	 					 if(currentYear == null){
    	 						Calendar c = Calendar.getInstance(); 
    	 						currentYear =c.get(Calendar.YEAR);
    	 					 } 
    	 				 }
    	 /*
		 Calendar c = Calendar.getInstance();
         Integer value = c.get(Calendar.YEAR);
         */
         String year = currentYear.toString();
         
         
         
         
        List<Option> banksOptions = bankService.getBankAsOptions();
        mapResults.put("banksOptions",banksOptions);			
    	//List<Option> customerOptions = missionService.getAllMissionForYearAsOptions(context.getCurrentUser(), year);
        List<Option> customerOptions = missionService.getAllOpenMissionForYearAsOptions(context.getCurrentUser(), year);
        
    	mapResults.put("customerForInvoiceOptions", customerOptions);
		return new ModelAndView("select_mission_invoice",mapResults);		
	}
    
    public ModelAndView selectCustomerForInvoiceReminderPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	 Map<String,Object> mapResults = new HashMap<String, Object>();
    	 WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	 
    	 /** The application context. */
 		ApplicationContext applicationContext = org.springframework.web.context.support.WebApplicationContextUtils
 		.getRequiredWebApplicationContext(request.getSession().getServletContext());
 		
 		RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
    	
    	 //Initialize the current invoice to null
    	 context.setCurrentInvoice(null);
    	 
		 //Defaults to current year 	
    	 /*
		 Calendar c = Calendar.getInstance();
         Integer value = c.get(Calendar.YEAR);
         */
    	 Integer currentYear = exerciseService.getFirstOnGoingExercise();
    	 if(currentYear == null){
    	 					 //currentYear = exerciseService.getMaxYear();
    		 				 currentYear =  exerciseService.getLastClosedExercise();
    	 					 if(currentYear == null){
    	 						Calendar c = Calendar.getInstance(); 
    	 						currentYear =c.get(Calendar.YEAR);
    	 					 } 
    	 				 }
         String year = currentYear.toString();      
         List<Option> customerOptions = repositoryService.getCustomerOptions();//this.customerService.getAllCustomerAsOptions();
    		
    	mapResults.put("customerForInvoiceOptions", customerOptions);
		return new ModelAndView("select_mission_invoice_reminder",mapResults);		
	}
    
    
    
    
    
   
    public ModelAndView selectCustomerForNoteCreditPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	return new ModelAndView("select_mission_notecredit");
    	
    }
    
   // ${ctx}/checkInvoiceReminderForMissionPage.htm?customerid="+customerid;" 
   public ModelAndView checkInvoiceReminderForMissionPage(HttpServletRequest request,
    			HttpServletResponse response) throws Exception {
	   ArrayList<String> messages = new ArrayList<String>();
	   String customerAsString = request.getParameter("customerid"); 
	   String lang = request.getParameter("lang"); 
	   WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		if(lang==null || lang.equalsIgnoreCase("-1")){
			messages.add("Please select a valid language... ");	
			request.getSession(false).setAttribute("invoiceErrorMessage", messages);
			return new ModelAndView("redirect:/selectCustomerForInvoiceReminderPage.htm?customerid="+customerAsString);
		}
	   
	   RemindInvoice rappel =  service.createFactureReminder(context.getCurrentUser(),Long.parseLong(customerAsString),lang);				
	   if(rappel == null){
			messages.add("No reminder could be created for thisn invoice...");
			request.getSession(false).setAttribute("invoiceErrorMessage", messages);
			return new ModelAndView("redirect:/selectCustomerForInvoiceReminderPage.htm?customerid="+customerAsString);
		}else{
			
			return new ModelAndView("redirect:/editRemindInvoicePage.htm?reminderId="+rappel.getId());				
		}
    	
    }
    
   
    public ModelAndView checkInvoiceForMissionPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	 
    	
    	//facture="+facture+"&type=CN"+"&tva="+tva+"&honoraires="+honoraires;
    	ArrayList<String> messages = new ArrayList<String>();
    	String customerAsString = request.getParameter("customerid"); 
		String type = request.getParameter("type"); 
		String tvaAstring = request.getParameter("tva"); 
		String honorairesAstring = request.getParameter("honoraires");
		String forcecreationAsString = request.getParameter("forcecreation");
		String referenceFacture =  request.getParameter("facture");
		String interneAsString = request.getParameter("interne");
		
		 Integer currentYear = exerciseService.getFirstOnGoingExercise();
		 if(currentYear == null){
			request.getSession(false).setAttribute("invoiceErrorMessage", "No ongoing exercice found... ");									
			return new ModelAndView("redirect:/selectCustomerForInvoicePage.htm");  
		 }
        String year = currentYear.toString();
		
		int delai = 0;
		
		String lang = request.getParameter("lang"); 
		String delaiAstring = request.getParameter("delai"); 
		String bank = request.getParameter("bank");
		
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		
		float tva = 0.0f;
		double honoraires = 0.0d;
		boolean forcecreation = false;
		boolean interne =false;
	
		//Converts the forceCreation parameter
		forcecreation = Boolean.parseBoolean(forcecreationAsString);
		interne = (interneAsString != null) && Boolean.parseBoolean(interneAsString);
		Mission mission = null;
		boolean isClosed = false;
		boolean hasFinalBill = false;
		Long bankId = null;
		Long customerid = null;
		//Make this checks only for advances and final bill
		if(!type.equalsIgnoreCase(Invoice.TYPE_CREDITNOTE)){
			
			
			bankId = Long.parseLong(bank);  
			if(customerAsString != null && !customerAsString.isEmpty()){
				customerid = Long.parseLong(customerAsString);
			}
			else{
				messages.add("Please enter a valid customer... ");	
			}
			
			//converts the tva amount
			try{
				tva= Float.parseFloat(tvaAstring);
				if(tva <0.0d) throw new NumberFormatException();
			}catch(NumberFormatException nfe){				
				messages.add("Please enter a valid tva amount... ");				
			}
			
			//converts the honoraires amount
			if(!honorairesAstring.equalsIgnoreCase("auto")){
				try{			
					honoraires= Double.parseDouble(honorairesAstring);				
					if(honoraires <0.0d) throw new NumberFormatException();					
				}catch(NumberFormatException nfe){
					messages.add("Please enter a valid honoraires amount... ");					
				}	
			}
			
			try{
				delai = Integer.parseInt(delaiAstring);
			}
			catch(NumberFormatException nfe){
				delai = 0;
			}
			
			
			
			if(lang.equalsIgnoreCase("-1")){
				messages.add("Please select a valid language... ");				
			}
		
			//1.Check if the bank is not null
			if(bank.equalsIgnoreCase("-1")){
				messages.add("Please select a valid bank... ");				
			}
			
			
			if(messages != null && !messages.isEmpty()){
				request.getSession(false).setAttribute("invoiceErrorMessage", messages);									
				return new ModelAndView("redirect:/selectCustomerForInvoicePage.htm?customerid="+customerid+"&type="+type+"&tva="+tvaAstring+"&honoraires="+honorairesAstring+"&bank"+bank+"&lang="+lang+"&delai="+delai+"&forcecreation="+forcecreationAsString);
			}
			
			mission = missionService.getOneDetached(Long.valueOf(customerid));
			
			//1.Check for the fial bill first
			hasFinalBill = service.findFinalBillForMission(mission.getId());
			
			//2.Check if the mission is closed
			isClosed = mission.getStatus().equalsIgnoreCase(Mission.STATUS_CLOSED) || mission.getStatus().equalsIgnoreCase(Mission.STATUS_STOPPED);		
			if((isClosed == true) && (!type.equalsIgnoreCase(Invoice.TYPE_FINALBILL))){
				messages.add("As the mission is closed no advance can be invoiced for it... ");				
			}

			
		}
		
		if(type.equalsIgnoreCase(Invoice.TYPE_SUPPLEMENT)){
			
			Invoice facture = service.createFactureForMission(customerid,context.getCurrentUser(),type, tva, honoraires,delai, lang,bankId,year);				
			return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+facture.getId());
			
		}else if(type.equalsIgnoreCase(Invoice.TYPE_ADVANCE)){//3.Check if the total advance amount exceeds 90% of the budget
			
			double budget = mission.getAnnualBudget().getExpectedAmount() + mission.getAnnualBudget().getReportedAmount();
			double totalInvoiced = service.getTotalInvoicedForMission(mission.getId());
			budget = budget * 0.9;
			if((forcecreation == false) && ( (totalInvoiced + honoraires) >= budget)){
				messages.add("The total advances already requested exceeds 90% of the budget");
				request.getSession(false).setAttribute("invoiceErrorMessage", messages);
				return new ModelAndView("redirect:/selectCustomerForInvoicePage.htm?customerid="+customerid+"&type="+type+"&tva="+tvaAstring+"&honoraires="+honorairesAstring+"&bank="+bank+"&lang="+lang+"&delai="+delai+"&forcecreation="+forcecreationAsString);
			}else{
				Invoice facture = service.createFactureForMission(customerid,context.getCurrentUser(),type, tva, honoraires,delai, lang,bankId,year);				
				return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+facture.getId());				
			}
			
		}		
		else if(type.equalsIgnoreCase(Invoice.TYPE_FINALBILL)){
			messages.clear();
			//Tester que la mission est close
			if(isClosed == false){
				messages.add("The mission must be closed in order to proceed further");
			}	
			
			long  countHeurePrestees = missionService.countMissionBudget(mission.getId());
			if( countHeurePrestees == 0 ){				
			    messages.add("No chargeable hours registered for this mission...Please register the worked hours in order to proceed further...");				
			}
			
			boolean hasError = false;
			
			int countTsToValidate = timesheetService.getCountTimesheetToValidateForProject(mission.getId());
			if(countTsToValidate > 0){
				List<TimesheetData> timesheets = timesheetService.getAllTimesheetToValidateForProject(mission.getId());
				messages.add("Please validate all the reports activity listed below in order to proceed further...");
				request.getSession(false).setAttribute("listTimesheetsToValidate", timesheets);						
			}
				
			/*
			List<TimesheetData> timesheets = timesheetService.getAllTimesheetToValidateForProject(mission.getId());
			if( timesheets != null){			
				
				for(TimesheetData timesheetData : timesheets){
					if(!timesheetData.getStatus().equalsIgnoreCase(Constants.TIMESHEET_STATUS_STRING_VALIDATED)){
						messages.add("Please validate all the reports activity listed below in order to proceed further...");
						request.getSession(false).setAttribute("listTimesheetsToValidate", timesheets);						
						break;
					}
				}
			}
			*/
			
			
			if(!messages.isEmpty()){
				request.getSession(false).setAttribute("invoiceErrorMessage", messages);					
				hasError = true;
				request.getSession(false).setAttribute("hasError", hasError);
			}
			
			if( (hasError == true) && (forcecreation == false)){								
				return new ModelAndView("redirect:/selectCustomerForInvoicePage.htm?customerid="+customerid+"&type="+type+"&tva="+tvaAstring+"&honoraires="+honorairesAstring+"&bank="+bank+"&lang="+lang+"&delai="+delai+"&forcecreation="+forcecreationAsString);
			}
			else{
				
				int countAdvancesNotSent = service.findAdvancesNotSentForMission(mission.getId());
				if(countAdvancesNotSent > 0){
					messages.add("Please send all advances in order to proceed further...");
				}
				
				if(hasFinalBill==true){						
					messages.add("The mission has already a final bill registered");	
				}
				
				if(hasFinalBill==true || countAdvancesNotSent > 0){																
					request.getSession(false).setAttribute("invoiceErrorMessage", messages);
					hasError = true;
					request.getSession(false).setAttribute("hasError", hasError);										
					return new ModelAndView("redirect:/selectCustomerForInvoicePage.htm?customerid="+customerid+"&type="+type+"&tva="+tvaAstring+"&honoraires="+honorairesAstring+"&bank="+bank+"&lang="+lang+"&delai="+delai+"&forcecreation="+forcecreationAsString);
				}
				else{
					Invoice facture = service.createFactureForMission(customerid,context.getCurrentUser(),type, tva, honoraires,delai, lang,bankId,year);
					return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+facture.getId());					
				}
				
			}

			
		}else{ //Traitement des Note de credit			
			//Try to find the target invoice from the reference passed
			Invoice facture = service.getOneDetachedFromReference(referenceFacture.trim());
			if(facture == null){
				messages.add("Please enter a valid reference for invoice... ");
			}
			
			if((facture!=null) && facture.getStatus().equalsIgnoreCase(Invoice.FACTURE_STATUS_CODE_PAID)){
				messages.add("You are not allowed to create a Note de Credit for an already paid invoice... ");
			}
			
			//converts the tva amount
			try{
				tva= Float.parseFloat(tvaAstring);
				if(tva <0.0d) throw new NumberFormatException();
			}catch(NumberFormatException nfe){				
				messages.add("Please enter a valid tva amount... ");				
			}
			
			//converts the honoraires amount			
			try{			
				honoraires= Double.parseDouble(honorairesAstring);				
				if(honoraires <0.0d) throw new NumberFormatException();				
			}catch(NumberFormatException nfe){
				messages.add("Please enter a valid honoraires amount... ");				
			}
			
			if(messages != null && !messages.isEmpty()){
				request.getSession(false).setAttribute("invoiceErrorMessage", messages);					
				return new ModelAndView("redirect:/selectCustomerForNoteCreditPage.htm");
			}
			
			
			try{
				facture = service.createNoteDeCreditForMission(facture,context.getCurrentUser(),type, tva, honoraires,interne,forcecreation);
			}catch(ExcessiveAmountException nfe){				
				messages.add("The amount entered cannot exceed the total of total amount of the parent invoice... ");				
			}
			
			if(messages != null && !messages.isEmpty()){
				request.getSession(false).setAttribute("invoiceErrorMessage", messages);					
				return new ModelAndView("redirect:/selectCustomerForNoteCreditPage.htm");
			}
			
			return new ModelAndView("redirect:/editInvoicePage.htm?invoiceId="+facture.getId());
			
		}
 }
    
    
    
    
    
    

    public ModelAndView showInvoiceInfos(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	//Country reference data
		List<Option> countryOptions= new ArrayList<Option>();
		countryOptions.add(new Option("LU","LUXEMBOURG"));
		countryOptions.add(new Option("DK","GERMANY"));
		countryOptions.add(new Option("FR","FRANCE"));
		countryOptions.add(new Option("BE","BELGIQUE"));
		
		String invoiceid = request.getParameter("id");
		String missionid = request.getParameter("customerid"); 
		
		Mission mission = null;
		if(invoiceid == null || invoiceid.trim().length() == 0){
			mission = missionService.getOneDetached(Long.parseLong(missionid) );
		}
		else{
			Invoice facture = service.getOneDetached(Long.valueOf(invoiceid) );
			mission = missionService.getOneDetached( facture.getProject().getId());
		}
		
		
		//List<MissionHeurePresteeData> heurePrestees = missionService.calculateMissionHeuresPrestees( mission );
	    List<MissionBudgetData> budgets = missionService.calculateMissionBudget(mission);
	    
	    double budget = mission.getAnnualBudget().getExpectedAmount() + mission.getAnnualBudget().getReportedAmount();
		
	    mapResults.put("budgetOPtion", budget);
	    mapResults.put("countryOptions", countryOptions);
	    mapResults.put("invoices", mission.getFactures());
	    mapResults.put("costs", mission.getExtraCosts());
	    mapResults.put("countryOptions", countryOptions);
	    //mapResults.put("heurePrestees", heurePrestees);
	    mapResults.put("budgets", budgets);
 
		return new ModelAndView("invoice_register_info",mapResults);		
	}
    
    
    
    public ModelAndView editInvoiceHistoryItemPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	JSONObject ajaxReply = new JSONObject();
    	JSONArray historyList = new JSONArray();
    	JSONObject ajaxObject = null;

    	String invoiceId = request.getParameter("invoiceId");
    	String invoiceRef  = request.getParameter("invoiceRef");
    	List<UserAction> actions = userActionService.getAll("com.interaudit.domain.model.Invoice", Long.parseLong(invoiceId));
    	for(UserAction userAction : actions){
    		ajaxObject = new JSONObject();
    		ajaxObject.put("date", userAction.getTime().toString());
    		ajaxObject.put("action", userAction.getAction());
    		ajaxObject.put("user", userAction.getEmployee().getFullName());
    		
    		historyList.put(ajaxObject);
    	}
    	
    	
    	Invoice invoice = service.getOne(Long.parseLong(invoiceId));
    	Set<Payment> payments = invoice.getPayments();
    	for(Payment payment: payments){
    		actions = userActionService.getAll("com.interaudit.domain.model.Payment", payment.getId());
        	for(UserAction userAction : actions){
        		ajaxObject = new JSONObject();
        		ajaxObject.put("date", userAction.getTime().toString());
        		ajaxObject.put("action", userAction.getAction());
        		ajaxObject.put("user", userAction.getEmployee().getFullName());
        		
        		historyList.put(ajaxObject);
        	}
    		
    	}
    	
    	
    	ajaxReply.put("invoiceId", invoiceRef);
    	ajaxReply.put("history", historyList);
    	
    	response.setContentType("text/json;charset=UTF-8");
    	ServletOutputStream out = response.getOutputStream();
		//String reply = historyList.toString();	
    	String reply = ajaxReply.toString();
		out.write(reply.getBytes("UTF-8"));
		out.flush();
		out.close();
		return null;
    	
    }
    
    public ModelAndView editPaymentHistoryItemPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	JSONObject ajaxReply = new JSONObject();
    	JSONArray historyList = new JSONArray();
    	JSONObject ajaxObject = null;
    	
    	String paymentId = request.getParameter("paymentId");
    	String invoiceRef  = request.getParameter("invoiceRef");
    	List<UserAction> actions = userActionService.getAll("com.interaudit.domain.model.Payment", Long.parseLong(paymentId));
    	for(UserAction userAction : actions){
    		ajaxObject = new JSONObject();
    		ajaxObject.put("date", userAction.getTime().toString());
    		ajaxObject.put("action", userAction.getAction());
    		ajaxObject.put("user", userAction.getEmployee().getFullName());
    		
    		historyList.put(ajaxObject);
    	}
    	
    	ajaxReply.put("invoiceId", invoiceRef);
    	ajaxReply.put("history", historyList);
    	
    	response.setContentType("text/json;charset=UTF-8");
    	ServletOutputStream out = response.getOutputStream();
		//String reply = historyList.toString();	
    	String reply = ajaxReply.toString();
		out.write(reply.getBytes("UTF-8"));
		out.flush();
		out.close();
		return null;
    	
    }
    
    
    //updateFraisLibelle.htm
    public ModelAndView updateFraisLibelle(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

    	try{
    		
    		String libelle = request.getParameter("translation");
            final String fraisId = request.getParameter("fraisId");
           // String invoiceId = request.getParameter("invoiceId");

            //Update the service layer
            AddtionalFeeInvoice fee = service.updateFraisLibelle(Long.parseLong(fraisId),libelle);
            
            response.setContentType("text/json");        
            response.getWriter().write(fee.getJustificationHtml());

            logger.info("Returning null");
            return null;
	    }
		catch(Exception e){
			throw e;
		}
        
    }
    
  //updateInvoiceLibDelai.htm
    public ModelAndView updateInvoiceLibDelai(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

    	try{
    		String libelle = request.getParameter("translation");
    	       // final String fraisId = request.getParameter("fraisId");
    	        String invoiceId = request.getParameter("invoiceId");
    	        
    	        //Update the service layer
    	        Invoice facture = service.updateInvoiceLibDelai(Long.parseLong(invoiceId),libelle);

    	        response.setContentType("text/json");        
    	        response.getWriter().write(facture.getLibDelaiHtml());

    	        logger.info("Returning null");
    	        return null;
	    }
		catch(Exception e){
			throw e;
		}
        
    }
    
  //updateInvoiceLibHonoraires.htm
    public ModelAndView updateInvoiceLibHonoraires(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	try{
    		String libelle = request.getParameter("translation");
    	       // final String fraisId = request.getParameter("fraisId");
    	        String invoiceId = request.getParameter("invoiceId");
    	        
    	        //Update the service layer
    	        Invoice facture = service.updateInvoiceLibHonoraires(Long.parseLong(invoiceId),libelle);
    	        
    	        //Update the amounts
    	        facture = service.updateInvoiceAmountTotal(facture.getId() );

    	        response.setContentType("text/json");        
    	        response.getWriter().write(facture.getLibHonorairesHtml());

    	        logger.info("Returning null");
    	        return null;
	    }
		catch(Exception e){
			throw e;
		}
        
    } 
    
    
    //updateInvoiceAdresse.htm
    public ModelAndView updateInvoiceAdresse(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	try{
    		String addresse = request.getParameter("translation");
            //final String fraisId = request.getParameter("fraisId");
            String invoiceId = request.getParameter("invoiceId");
            
            Invoice facture = service.updateInvoiceAdresse(Long.parseLong(invoiceId),addresse);

            response.setContentType("text/json");        
            response.getWriter().write(facture.getBillingAddressHtml());

            logger.info("Returning null");
            return null;
	    }
		catch(Exception e){
			throw e;
		}
        
    } 
    
    
   
    
    
    //updateInvoiceFraisValue
    public ModelAndView updateInvoiceFraisValue(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

    	try{
    		String value = request.getParameter("translation");
            final String fraisId = request.getParameter("fraisId");
            String invoiceId = request.getParameter("invoiceId");
            
            double valueNumber = Double.parseDouble(value);
            if(valueNumber < 0.0d){
            	throw new NumberFormatException();
            }

            AddtionalFeeInvoice fee = service.updateInvoiceFraisValue(Long.parseLong(fraisId),valueNumber);
            
            //Update the amounts
	        Invoice facture = service.updateInvoiceAmountTotal(Long.parseLong(invoiceId) );
            
            response.setContentType("text/json");        
            response.getWriter().write(Double.toString(fee.getValue()));

            logger.info("Returning null");
            return null;
	    }
		catch(Exception e){
			throw e;
		}
        
    }
    
  //updateInvoiceTvaZeroLibCodeValue
    public ModelAndView updateInvoiceTvaZeroLibCodeValue(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

    	try{
    		Map<String,Object> mapResults = new HashMap<String, Object>();
            final String tvaZeroLibCode = request.getParameter("code");
            String invoiceId = request.getParameter("invoiceId");           
            //Update the invoice
	        Invoice facture = service.updateInvoiceCodeTvaZero(Long.parseLong(invoiceId),  tvaZeroLibCode);
            mapResults.put("invoice", facture);
			return new ModelAndView("invoice_editor",mapResults);
	    }
		catch(Exception e){
			throw e;
		}
        
    }
    
    
    ///changeExcludedFromBalanceAgeeStatus.htm
    public ModelAndView changeExcludedFromBalanceAgeeStatus(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	try{
    		Map<String,Object> mapResults = new HashMap<String, Object>();            
            final String invoiceId = request.getParameter("invoiceId");           
            //Update the invoice
	        Invoice facture = service.changeExcludedFromBalanceAgeeStatus(Long.parseLong(invoiceId));
            mapResults.put("invoice", facture);
			return new ModelAndView("invoice_editor",mapResults);
	    }
		catch(Exception e){
			throw e;
		}
        
    }
    
    
  //updateInvoiceHonoraires
    public ModelAndView updateInvoiceHonoraires(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	try{
    		String value = request.getParameter("translation");
            //final String fraisId = request.getParameter("fraisId");
            String invoiceId = request.getParameter("invoiceId");

            double valueNumber = Double.parseDouble(value);
            if(valueNumber < 0.0d){
            	throw new NumberFormatException();
            }
            
            Invoice facture = service.updateInvoiceHonoraires(Long.parseLong(invoiceId),valueNumber);
            
          //Update the amounts
	        facture = service.updateInvoiceAmountTotal(Long.parseLong(invoiceId) );
            
            
            response.setContentType("text/json");        
            response.getWriter().write(Double.toString(facture.getHonoraires()));

            logger.info("Returning null");
            return null;
	    }
		catch(Exception e){
			throw e;
		}
        
    }
    
  //updateInvoiceTva
    public ModelAndView updateInvoiceTva(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	
        String value = request.getParameter("translation");
       // final String fraisId = request.getParameter("fraisId");
        String invoiceId = request.getParameter("invoiceId");

        double valueNumber = Double.parseDouble(value);
        if(valueNumber < 0.0d){
        	throw new NumberFormatException();
        }
        
        Invoice facture = service.updateInvoiceTva(Long.parseLong(invoiceId),valueNumber);
        
      //Update the amounts
         facture = service.updateInvoiceAmountTotal(Long.parseLong(invoiceId) );
        
        
        response.setContentType("text/json");        
        response.getWriter().write(Double.toString(facture.getTva()));

        logger.info("Returning null");
        return null;
    }
    
    
  //updateInvoiceLang
    public ModelAndView updateInvoiceLang(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	
    	try{
    		    String lang = request.getParameter("value");
    	         // final String fraisId = request.getParameter("fraisId");
    	        String invoiceId = request.getParameter("invoiceId");

    	        Invoice facture = service.updateInvoiceLang(Long.parseLong(invoiceId),lang);
    	        
    	        response.setContentType("text/json");        
    	        response.getWriter().write(facture.getLanguage());

    	        logger.info("Returning null");
    	        return null;
    	}
    	catch(Exception e){
    		throw e;
    	}

        
    }
    
    
    
    public ModelAndView sendInvoicesReminderEmails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	 final ExecutorService  pool = Executors.newFixedThreadPool(1);
    	 pool.execute(new  HandlerReminder());    	
    	 return showInvoicesPage( request,response);
    }
    
    class HandlerReminder implements Runnable
    {

    	HandlerReminder()
      { 
      }
      
      public void run()
      {    	 
    	  timesheetService.sendInvoicesReminderEmails();
      }
    }
    
   
    
  //updateInvoiceLang
    public ModelAndView updateInvoiceCustomer(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	
    	 final ExecutorService  pool = Executors.newFixedThreadPool(1);
    	
    	try{
    		   // String lang = request.getParameter("value");
    	         // final String fraisId = request.getParameter("fraisId");
    	        String invoiceId = request.getParameter("invoiceId");
    	        String customerId = request.getParameter("value");

    	        //Update the invoice
    	        Invoice facture = service.updateInvoiceCustomer(Long.parseLong(invoiceId),Long.parseLong(customerId));
    	        
    	        //Update the budget
    	        //exerciseService.updateExerciceAndBudget(); 
    	        pool.execute(new  Handler());
    	        
    	        response.setContentType("text/json");        
    	        response.getWriter().write(facture.getCustomerName());
    	       

    	        logger.info("Returning from updateInvoiceCustomer");
    	        return null;
    	}
    	catch(Exception e){
    		throw e;
    	}

        
    }
    
    
    class Handler implements Runnable
    {
   
      
      Handler()
      { 
       
      }
      
      public void run()
      {
    	  Integer year = exerciseService.getFirstOnGoingExercise();
    	  Exercise exercise = exerciseService.getOneFromYear(year);
      	  exerciseService.updateExerciceAndBudget(exercise.getId()); 
      }
    }

    
  //updateInvoiceLang
    public ModelAndView loadCustomersForInvoice(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	
    	try{
    		    //JSONArray eventList = new JSONArray();
    			StringBuffer buffer = new StringBuffer("[");
    		//['key_1', 'value one'], ['key_2', 'value two'], ['key_3', 'value three']] 

    		    WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	        String invoiceId = request.getParameter("invoiceId");

    	        Invoice facture = service.getOneDetached(Long.parseLong(invoiceId));
    	        
    	        //Take the year of the invoice 
    	    	String year = facture.getExercise();
    	       
    	    	//List<Option> customerOptions = missionService.getAllOpenMissionForYearAsOptions(context.getCurrentUser(), year);
    	    	List<Option> customerOptions = missionService.getAllOpenMissionsWithoutFinalBillForYearAsOptions( year);
    	    	int size = customerOptions.size();
    	    	int index=0;
    	    	for(Option option : customerOptions){
    	    		
    	    		buffer.append("[\"" +option.getId()+ "\",\"" + option.getName().toLowerCase()+"\"]" );
    	    		
    	    		if(index < size){
    	    			buffer.append("," );
    	    		}
    	    		index++;
    	    		/*
    	    		//JSONObject ajaxObject = new JSONObject();
    	    		ajaxObject.put("key", );
    	    		
	        		ajaxObject.put("key", option.getId());
	        		ajaxObject.put("value", option.getName().toLowerCase());
	        		*/
	        		//eventList.put(ajaxObject);
	    		}
    	        
    	    	buffer.append("]" );
    	    	
    	        response.setContentType("text/json");    
    	        ServletOutputStream out = response.getOutputStream();
    			
    	    	String reply = buffer.toString();
    			out.write(reply.getBytes("UTF-8"));
    			out.flush();
    			out.close();
    	        //response.getWriter().write(eventList.toString());

    	        logger.info("Returning from loadCustomersForInvoice");
    	        return null;
    	}
    	catch(Exception e){
    		throw e;
    	}

        
    }


	public IFactureService getService() {
		return service;
	}




	public void setService(IFactureService service) {
		this.service = service;
	}



	public IMissionService getMissionService() {
		return missionService;
	}



	public void setMissionService(IMissionService missionService) {
		this.missionService = missionService;
	}






	public ITimesheetService getTimesheetService() {
		return timesheetService;
	}






	public void setTimesheetService(ITimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}






	public IUserActionService getUserActionService() {
		return userActionService;
	}






	public void setUserActionService(IUserActionService userActionService) {
		this.userActionService = userActionService;
	}






	public ICustomerService getCustomerService() {
		return customerService;
	}






	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}






	public IBankService getBankService() {
		return bankService;
	}






	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}






	






	public IExerciseService getExerciseService() {
		return exerciseService;
	}






	public void setExerciseService(IExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}


	public IPaymentService getPaymentService() {
		return paymentService;
	}


	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}



	



	







	



	



	

	

	
}
