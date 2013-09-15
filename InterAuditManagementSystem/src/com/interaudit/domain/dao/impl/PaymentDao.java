package com.interaudit.domain.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IPaymentDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Payment;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchPaymentParam;


/**
 * @author bernard
 *
 */
public class PaymentDao implements IPaymentDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(PaymentDao.class);
	
	
	
	
    public void deleteOne(Long id) {
			
		try{
			
			Payment payment =em.find(Payment.class, id);
	        if (payment == null) {
	            throw new DaoException(2);
	        }
	        em.remove(payment);  
	      
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  deleteOne..."));
	        
		}finally{
			em.close();
		}
		
		      
    }

	@SuppressWarnings("unchecked")
	
	/**
     * 
     * @return Payment objects list representing all Payment known by IAMS
     *         application.
     */
	public  List<Payment> getAll() {
		try{
			return em.createQuery("select distinct p from Payment p order by p.paymentDate asc").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  getAll..."));

		}finally{
			em.close();
		}
		
        
    }

    
    
    /**
     * Returns a Payment object identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	
	public Payment getOne(Long id){
		try{
			return em.find(Payment.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  getOne..."));

		}finally{
			em.close();
		}
		
        
    }

	
	/**
     * Returns a Payment object detached identified by given id.
     * 
     * @param id
     * @return Returns a Payment object identified by given id.
     */
	@SuppressWarnings("unchecked")
	
	public Payment getOneDetached(Long id){
		try{
			//String queryString = "select new com.interaudit.domain.model.Payment(p.id, p.bankCode,p.reference,p.year,p.customerName,p.totalDu,p.totalNoteCredit,p.totalPaid,p.totalRemaining,p.amount,p.reimbourse,p.currencyCode,p.paymentDate,i.reference,i.id) from Payment p join p.facture i where p.id = :id";
			String queryString = "select p from Payment p  where p.id = :id";
			List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
            if (resultList.size() > 0) {
                return (Payment) resultList.get(0);
            }
            return null;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  getOneDetached..."));

		}finally{
			em.close();
		}
		
		
        	
        	
	}
	
	
	public int getNextPaymentSequence(Long idFacture) {
		
		try{
			
			Number result = (Number) em
            .createQuery(
                    "select count(*) from Payment p where p.facture.id = :idFacture")
            .setParameter("idFacture", idFacture).getSingleResult();
		    if ( result == null) {
		        return 1;
		    } else {
		        return result.intValue() + 1;
		    }

		}
		catch(javax.persistence.NoResultException e){	
			return 1;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  getNextPaymentSequence..."));

		}finally{
			em.close();
		}
		
        
    }
    
    

    
    public Payment saveOne(Payment payment) {    	
		try{			
			em.persist(payment);	        
	        return payment;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  saveOne..."));
	        
		}finally{
			em.close();
		}
		
    	
        
    }

    
    public Payment updateOne(Payment payment) {    	    	
		try{		
			Payment ret =  em.merge(payment);	       
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  updateOne..."));
		}finally{
			em.close();
		}        
    }
    
    
	
    @SuppressWarnings("unchecked")
	
	public  List<Payment> getPaymentByPaidDate(Date paymentDate){
		
    	try{
    		return em
            .createQuery("SELECT distinct new com.interaudit.domain.model.Payment(p.id, p.bankCode,p.reference,p.year,p.customerName,p.totalDu,p.totalNoteCredit,p.totalPaid,p.totalRemaining,p.amount,p.reimbourse,p.currencyCode,p.paymentDate,i.reference,i.id,p.escompte) FROM Payment p join p.facture i  WHERE p.paymentDate = (:paymentDate)").setParameter("paymentDate", paymentDate).getResultList();
             
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  getPaymentByPaidDate..."));

		}finally{
			em.close();
		}
		
		
		
	}
    
    
    
 // Get a customer by its code
    @SuppressWarnings("unchecked")
	
    public List<Payment> getPaymentFromIdentifier(String identifier){
    	
    	try{
    		String queryString = "select new com.interaudit.domain.model.Payment(p.id, p.bankCode,p.reference,p.year,p.customerName,p.totalDu,p.totalNoteCredit,p.totalPaid,p.totalRemaining,p.amount,p.reimbourse,p.currencyCode,p.paymentDate,i.reference,i.id,p.escompte) from Payment p join p.facture i where upper(p.reference) like upper(:identifier)  ";
        	
        	List<Payment> resultList = em.createQuery(queryString)
            .setParameter("identifier",
            		"%" + identifier +  "%" ).getResultList();
            
            return resultList;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  getPaymentFromIdentifier..."));

		}finally{
			em.close();
		}
		
    	
    }
    
  @SuppressWarnings("unchecked")
	
    public List<Payment> getPaymentFromInvoiceIdentifier(String identifier){
    	
    	try{
    		String queryString = "select new com.interaudit.domain.model.Payment(p.id, p.bankCode,p.reference,p.year,p.customerName,p.totalDu,p.totalNoteCredit,p.totalPaid,p.totalRemaining,p.amount,p.reimbourse,p.currencyCode,p.paymentDate,i.reference,i.id,p.escompte) from Payment p  join p.facture i where upper(i.reference) like upper(:identifier)  ORDER BY p.paymentDate asc  ";
        	
        	List<Payment> resultList = em.createQuery(queryString)
            .setParameter("identifier",
            		"%" + identifier +  "%" ).getResultList();
            
            return resultList;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  getPaymentFromInvoiceIdentifier..."));

		}finally{
			em.close();
		}
		
    	
    }
	
	
	
	@SuppressWarnings("unchecked")
	
	public List<Payment>  searchPayments(  SearchPaymentParam param){
		
		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
					 	
	        StringBuilder hql = new StringBuilder("select new com.interaudit.domain.model.Payment(p.id, p.bankCode,p.reference,p.year,p.customerName,p.totalDu,p.totalNoteCredit,p.totalPaid,p.totalRemaining,p.amount,p.reimbourse,p.currencyCode,p.paymentDate,i.reference,i.id,p.escompte) from Payment p join p.facture i ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher le bankCode
	        if (param.getBankCode()!= null && param.getBankCode().length() > 0) {
	            parameters.put("bankCode", param.getBankCode());
	            whereClause.append("( upper(p.bankCode) = upper(:bankCode))");	           
	        }
	        
	        
	        //Rechercher les années
	        if (param.getYear() != null && param.getYear().length() > 0) {
	        	if (whereClause.length() > 0) {
		                whereClause.append(" AND ");
		        }
	            parameters.put("year", param.getYear());
	            whereClause.append("( upper(p.year) = upper(:year))");
	        }
	        
	       
	        
	      //Rechercher le customerNameLike
	        if (param.getCustomerNameLike() != null && param.getCustomerNameLike().length() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            /*
	            parameters.put("customerName","%" + param.getCustomerNameLike() + "%" );
	            whereClause.append("upper(p.customerName) like upper(:customerName)");
	            */
	            parameters.put("customerName", Long.parseLong(param.getCustomerNameLike())  );
	            //whereClause.append("p.facture.project.annualBudget.contract.customer.id = :customerName)");
	            whereClause.append("i.project.annualBudget.contract.customer.id = :customerName)");
	            
	        }
	        
	      //Rechercher le manager
	        if ( (param.getFromDate() != null )&& (param.getToDate() != null)) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("fromDate", param.getFromDate());
	            parameters.put("toDate", param.getToDate());
	            whereClause.append("p.paymentDate BETWEEN :fromDate AND :toDate");
	        }
	        
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	        hql.append(" ORDER BY p.paymentDate asc ");
	        
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	    
	        return q.getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in PaymentDao :  searchPayments..."));

		}finally{
			em.close();
		}
		
		
	}

    
}
