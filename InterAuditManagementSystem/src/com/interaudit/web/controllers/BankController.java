package com.interaudit.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.service.IBankService;
import com.interaudit.service.view.BanksView;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class BankController extends MultiActionController {
  
	private IBankService bankService;
	private static String BANKS_KEY ="viewbanks";
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showBanksPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	BanksView view = new  BanksView(bankService.getAll());
    	
    	mapResults.put(BANKS_KEY, view);
		return new ModelAndView("list_banks",mapResults);	
			
	}
	public IBankService getBankService() {
		return bankService;
	}
	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}
    

	
}
