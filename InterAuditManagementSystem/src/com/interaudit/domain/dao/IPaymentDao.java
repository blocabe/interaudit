package com.interaudit.domain.dao;

import java.util.Date;
import java.util.List;

import com.interaudit.domain.model.Payment;
import com.interaudit.service.param.SearchPaymentParam;

public interface IPaymentDao {
	
	/**
     * 
     * @return Payment objects list representing all Payment known by IAMS
     *         application.
     */
	public  List<Payment> getAll();

    /**
     * Returns a Payment object identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	public Payment getOne(Long id);
	
	
	
	/**
     * Returns a Payment object detached identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	public Payment getOneDetached(Long id);
    
    
 
    /**
     * Delete a Task object identified by given id.
     * 
     * @param id
     */
	public void deleteOne(Long id); 
    
 
    /**
     * Returns a Payment object identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	public Payment saveOne(Payment payment); 

    /**
     * Returns a Payment object identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	public Payment updateOne(Payment payment);
    
    /**
     * Returns a  collection of Payment object paid at a given date
     * 
     * @param paymentDate
     * @return Returns a collection of Payment.
     */
	public  List<Payment> getPaymentByPaidDate(Date paymentDate);
	
	public int getNextPaymentSequence(Long idFacture);
	
	public List<Payment> getPaymentFromIdentifier(String identifier);
	
	public List<Payment> getPaymentFromInvoiceIdentifier(String identifier);
	
    public List<Payment>  searchPayments(  SearchPaymentParam param);

}
