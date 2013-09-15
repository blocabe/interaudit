package com.interaudit.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IPaymentDao;
import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Payment;
import com.interaudit.service.IFactureService;
import com.interaudit.service.IPaymentService;

@Transactional
public class PaymentService implements IPaymentService {
	//private Log log = LogFactory.getLog(TaskService.class);
    private IPaymentDao paymentDao;
    private IFactureService factureService;
    
    /**
     * 
     * @return Payment objects list representing all Payment known by IAMS
     *         application.
     */
	public  List<Payment> getAll(){
		return paymentDao.getAll();
	}

    /**
     * Returns a Payment object identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	public Payment getOne(Long id){
		return paymentDao.getOne(id);
	}
	
	protected String getNextPaymentReference(Long idFacture, String factureReference){
		int nextId = paymentDao.getNextPaymentSequence(idFacture);
		
		return "P_"+ factureReference.toUpperCase() + "_" +nextId;
	}
	
	public Payment createPayment( Payment payment){
		
		Invoice facture = factureService.getOne(payment.getFacture().getId());
		
		if(facture != null){
		
			Mission project = facture.getProject();
			
			AnnualBudget annualBudget = project.getAnnualBudget();
			
			Contract contract = annualBudget.getContract();
			
			Customer customer = contract.getCustomer();
			
			payment.setCustomerName(customer.getCompanyName());
			
			payment.setReference(getNextPaymentReference(facture.getId(), facture.getReference()));
		
			payment.setFacture(facture);
			
			
			//Calendar c = Calendar.getInstance();
			payment.setYear(facture.getExercise());
			
			return this.saveOne(payment);
			
		}else{
			return null;
		}
		
	}
	
	public Payment updatePayment( Payment payment){
		 Invoice facture = factureService.getOne(payment.getFacture().getId());
		 if(facture != null){
			 
			 	Mission project = facture.getProject();
				
				AnnualBudget annualBudget = project.getAnnualBudget();
				
				Contract contract = annualBudget.getContract();
				
				Customer customer = contract.getCustomer();
				
				payment.setCustomerName(customer.getCompanyName());
				
				//payment.setReference(getNextPaymentReference(facture.getId(), facture.getReference()));
				
				payment.setFacture(facture);
				
				//return payment;
				
				return this.updateOne(payment);
			}else{
				return null;
			}
		 
		 
	}
	
	
	
	/**
     * Returns a Payment object detached identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	public Payment getOneDetached(Long id){
		Payment payment = paymentDao.getOneDetached(id);
		//Invoice facture = factureService.getOneDetached(payment.getFactureId());
		//payment.setFacture(facture);
		return payment;
	}
	
    
    
 
    /**
     * Delete a Task object identified by given id.
     * 
     * @param id
     */
	public void deleteOne(Long id){
		 paymentDao.deleteOne(id);
	}
    
 
    /**
     * Returns a Payment object identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	public Payment saveOne(Payment payment){
		return paymentDao.saveOne(payment);
	}

    /**
     * Returns a Payment object identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	public Payment updateOne(Payment payment){
		return paymentDao.updateOne(payment);
	}
    
    /**
     * Returns a  collection of Payment object paid at a given date
     * 
     * @param paymentDate
     * @return Returns a collection of Payment.
     */
	public  List<Payment> getPaymentByPaidDate(Date paymentDate){
		return paymentDao.getPaymentByPaidDate(paymentDate);
	}

	public IPaymentDao getPaymentDao() {
		return paymentDao;
	}

	public void setPaymentDao(IPaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	public IFactureService getFactureService() {
		return factureService;
	}

	public void setFactureService(IFactureService factureService) {
		this.factureService = factureService;
	}
    
    
	
}
