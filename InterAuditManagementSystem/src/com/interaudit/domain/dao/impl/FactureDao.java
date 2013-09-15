package com.interaudit.domain.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IFactureDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.AddtionalFeeInvoice;
import com.interaudit.domain.model.DeductionFeeInvoice;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Payment;
import com.interaudit.domain.model.RemindInvoice;
import com.interaudit.domain.model.data.BalanceAgeeData;
import com.interaudit.domain.model.data.InvoiceData;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchBalanceAgeeParam;
import com.interaudit.service.param.SearchInvoiceParam;
import com.interaudit.service.param.SearchInvoiceReminderParam;
import com.interaudit.service.view.BalanceAgeeView;
import com.interaudit.util.DateUtils;

public class FactureDao implements IFactureDao {
	
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(FactureDao.class);
	
	
	 public Map<String, String>  loadInvoiceTranslationsForLanguage(String language){
		  try{
	    		String queryString = "select KEY, VALUE FROM interaudit.INVOICE_TRANSLATIONS WHERE LANG = :lang";
	    		@SuppressWarnings("unchecked")
				List<Object[]> resultList = (List<Object[]>)em.createNativeQuery(queryString).setParameter("lang",language.toLowerCase()).getResultList();
		            if (resultList.size() > 0) {
		            	Map<String, String> cachedProperties = new HashMap<String, String>();
		            	for(int i = 0;i<resultList.size();i++){
							Object[] resultat = resultList.get(i);
							String key =  (String)resultat[0] ;
							String value =  (String)resultat[1] ;
							cachedProperties.put(key, value);
						}		            	
		            	return cachedProperties;
		            }
		            else{
		            	return null;
		            }
	        	}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  loadInvoiceTranslationsForLanguage..."));
			}
			finally{
	    		em.close();
	    	}
	 }
	 
	 public Map<String, String>  loadInvoiceTranslationsForLanguageForTVAZeroPourcent(String language){
		  try{
	    		String queryString = "select KEY, VALUE FROM interaudit.INVOICE_TRANSLATIONS WHERE LANG = :lang and key in ('lib.fa.tva0.opt0','lib.fa.tva0.opt1','lib.fa.tva0.opt2')";
	    		@SuppressWarnings("unchecked")
				List<Object[]> resultList = (List<Object[]>)em.createNativeQuery(queryString).setParameter("lang",language.toLowerCase()).getResultList();
		            if (resultList.size() > 0) {
		            	Map<String, String> cachedProperties = new HashMap<String, String>();
		            	for(int i = 0;i<resultList.size();i++){
							Object[] resultat = resultList.get(i);
							String key =  (String)resultat[0] ;
							String value =  (String)resultat[1] ;
							cachedProperties.put(key, value);
						}		            	
		            	return cachedProperties;
		            }
		            else{
		            	return null;
		            }
	        	}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  loadInvoiceTranslationsForLanguage..."));
			}
			finally{
	    		em.close();
	    	}
	 }
	

	public void deleteOne(Long id) {
		
		try{			
			Invoice facture = (Invoice)em.find(Invoice.class, id);
			if (facture == null) {
				throw new DaoException(2);
			}
			em.remove(facture);	     
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  deleteOne...")); 
		}finally{
			em.close();
		}

	}
	
	public void deleteOneFee(Long id) {
		
		try{			
			AddtionalFeeInvoice fee = em.find(AddtionalFeeInvoice.class, id);
			if (fee == null) {
				throw new DaoException(2);
			}
			em.remove(fee);	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  deleteOneFee..."));         
		}finally{
			em.close();
		}

	}
	
	@SuppressWarnings("unchecked")

	public List<Invoice> getAll() {
		try{
			return em			
			.createQuery("select f from Invoice f order by f.dateFacturation")
			.getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getAll...")); 
		}finally{
			em.close();
		}
		
	}
	
	@SuppressWarnings("unchecked")

	public Invoice getOneDetachedFromReference(String reference){
		try{	        
			String queryString = "select distinct(c) from Invoice c  join c.project.annualBudget.contract.customer cust left join fetch c.payments  left join fetch c.documents left join fetch c.frais where upper(c.reference) = upper(:reference)";
	            List resultList = em.createQuery(queryString).setParameter("reference",reference).getResultList();
	            if (resultList.size() > 0) {
	                return (Invoice) resultList.get(0);
	            }
	            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getOneDetachedFromReference...")); 
    		}finally{
        		em.close();
        	}
	}
	
	@SuppressWarnings("unchecked")
	public Invoice getOneDetached(Long id){
		try{        	
			String queryString = "select distinct(c) from Invoice c join c.project.annualBudget.contract.customer cust left join fetch c.payments left join fetch c.documents left join fetch c.frais  where c.id = :id";
            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
            if (resultList.size() > 0) {
            	Invoice facture = (Invoice) resultList.get(0);
            	Map<String, String> cachedProperties =  loadInvoiceTranslationsForLanguageForTVAZeroPourcent(facture.getLanguage());
            	facture.setLibTvaZeroOpt0(cachedProperties.get("lib.fa.tva0.opt0"));
        		facture.setLibTvaZeroOpt1(cachedProperties.get("lib.fa.tva0.opt1"));
        		facture.setLibTvaZeroOpt2(cachedProperties.get("lib.fa.tva0.opt2"));
                return facture;
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getOneDetached..."));
    		}finally{
        		em.close();
        	}
	}
	
	 @SuppressWarnings("unchecked")
	public RemindInvoice getOneRemindInvoiceDetached(Long id){
		 try{			
	        	String queryString = "select distinct(r) from RemindInvoice r left join fetch r.invoices where r.id = :id";
	            List<RemindInvoice> resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
	            if (resultList.size() > 0) {
	                return (RemindInvoice) resultList.get(0);
	            }
	            return null;
	        	}catch(Exception e){
	    			logger.error(e.getMessage());
	    			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getOneRemindInvoiceDetached..."));
	    		}finally{
	        		em.close();
	        	} 
	 }
	
	@SuppressWarnings("unchecked")

	public List<Invoice> getAllLibelleLike(String model) {		
		try{
			return em
			.createQuery("select f from Invoice f where f.libelle like :model")
			.setParameter("model", model)
			.getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getAllLibelleLike..."));

		}finally{
			em.close();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Invoice> getAllFactureByCustomerId(Long customerId){		
		try{
			
			return em
			.createQuery("select f from Invoice f where f.customer.id = :customerId")
			.setParameter("customerId", customerId)
			.getResultList(); 

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getAllFactureByCustomerId..."));

		}finally{
			em.close();
		}
		
		 
	 }
	
	@SuppressWarnings("unchecked")
	public List<Invoice> getAllUnpaidInvoices(){
		
try{			
			return em
			.createQuery("select f from Invoice f where f.status <> :unpaidStatus")
			.setParameter("unpaidStatus", "paid")
			.getResultList(); 

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getAllFactureByCustomerId..."));

		}finally{
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Invoice> getAllUnpaidAndSentInvoices(){
		
		try{			
			return em
			.createQuery("select f from Invoice f where f.status = :status")
			.setParameter("status", "sent")
			.getResultList();    
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getAllUnpaidAndSentInvoices..."));

		}finally{
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Invoice> getAllUnpaidAndSentInvoicesForCustomer(Long customerId){
		
		try{			
			return em
			.createQuery("select f from Invoice f where f.status = :status and f.project.annualBudget.contract.customer.id = :customerId")
			.setParameter("status", "sent").setParameter("customerId", customerId)
			.getResultList();    
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getAllUnpaidAndSentInvoicesForCustomer..."));

		}finally{
			em.close();
		}
	}
	
	public Invoice saveInvoiceReminderRelation(Long invoiceId,Long reminderId){
		Invoice invoice = em.find(Invoice.class, invoiceId);
		RemindInvoice reminder = em.find(RemindInvoice.class, reminderId);
		reminder.addInvoice(invoice);
	    return invoice;

	}
	
	
	public void deleteOneDeduction(Long id){		
		try{			
			DeductionFeeInvoice fee = em.find(DeductionFeeInvoice.class, id);
			if (fee == null) {
				throw new DaoException(2);
			}
			em.remove(fee);	    
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  deleteOneDeduction..."));       
		}finally{
			em.close();
		}

	}
	
    public DeductionFeeInvoice getOneDeduction(Long id){
    	try{
			return (DeductionFeeInvoice)em.find(DeductionFeeInvoice.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getOneDeduction..."));

		}finally{
			em.close();
		}	
    }
    
    public DeductionFeeInvoice updateOneDeduction(DeductionFeeInvoice fee){
    	try{			
    		DeductionFeeInvoice ret = em.merge(fee);
			return ret;
		}
		catch(Exception e){		
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  updateOneDeduction..."));
		}finally{
			em.close();
		}	
    }
	
	
	// get a fee by its id
    public AddtionalFeeInvoice getOneFee(Long id){
    	try{
			return (AddtionalFeeInvoice)em.find(AddtionalFeeInvoice.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getOneFee..."));

		}finally{
			em.close();
		}	
    }
    
    public AddtionalFeeInvoice updateOneFee(AddtionalFeeInvoice fee){
    	try{			
    		AddtionalFeeInvoice ret = em.merge(fee);
			return ret;
		}
		catch(Exception e){	
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  updateOneFee..."));
		}finally{
			em.close();
		}	
    }
	

	public Invoice getOne(Long id) {
		
		try{
			return (Invoice)em.find(Invoice.class, id);
		}
		catch(Exception e){		
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getOne..."));
		}finally{
			em.close();
		}	
	}

	
	public Invoice saveOne(Invoice facture) {		
		try{			
			em.persist(facture);	    
	        return facture;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  saveOne..."));
	        
		}finally{
			em.close();
		}

	}


	public Invoice updateOne(Invoice facture) {
	
		try{		
			Invoice ret = em.merge(facture);	     
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  updateOne..."));    
		}finally{
			em.close();
		}

		
	}
	
	public RemindInvoice updateOneReminder(RemindInvoice reminder){		
		try{			
			RemindInvoice ret = em.merge(reminder);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  updateOneReminder..."));            
		}finally{
			em.close();
		}
	}
	
	
	public RemindInvoice saveOneReminder(RemindInvoice reminder){
		try{		
			em.persist(reminder);	       
	        return reminder;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  updateOneReminder...")); 	        
		}finally{
			em.close();
		}
	}
	
	// Get a customer by its code
    @SuppressWarnings("unchecked")
 
    public List<InvoiceData> getInvoiceFromIdentifier(String identifier){
    	
    	try{
    		String queryString = "select new com.interaudit.domain.model.data.InvoiceData(f.id,f.type,f.dateFacturation,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro, f.reference, c.companyName,c.associeSignataire.code, f.statusString ,f.parentReference,f.dateFacturation,f.customerCode) from Invoice f join f.project m join f.project.annualBudget.contract.customer c where upper(f.reference) like upper(:identifier)  ";
        	
        	List<InvoiceData> resultList = em.createQuery(queryString)
            .setParameter("identifier",
            		"%" + identifier +  "%" ).getResultList();
            
            return resultList;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  updateOneReminder..."));
		}finally{
			em.close();
		}
		
    	
    }
    
    public void loadListBalanceAgeeData(int minDays, int maxDays, String custCode, Map<String,BalanceAgeeData> mapData){
		  try{
			  List<Object[]> resultList = null;
			    String queryWithMinMaxIntervals = "select min(dateFacturation),max(dateFacturation), avg ( DATE_PART('days', NOW() - dateFacturation) ) as days, count(custcode ) ,sum(amount) as amount ,custname, custcode, sum((select  sum(amount) from interaudit.payments where fk_facture = invoices.id )) as payments from  interaudit.invoices invoices where ( DATE_PART('days', NOW() - dateFacturation) > :minDays ) and ( DATE_PART('days', NOW() - dateFacturation) < :maxDays ) and status in ('sent')  group by custname , custcode  order by custname";
			    
			    String queryWithMinMaxIntervalsAndCustcode ="select min(dateFacturation),max(dateFacturation), avg ( DATE_PART('days', NOW() - dateFacturation) ) as days, count(custcode ) ,sum(amount) as amount ,custname, custcode, sum((select  sum(amount) from interaudit.payments where fk_facture = invoices.id )) as payments from  interaudit.invoices invoices where ( DATE_PART('days', NOW() - dateFacturation) > :minDays ) and ( DATE_PART('days', NOW() - dateFacturation) < :maxDays ) and status in ('sent') and custcode = :custCode  group by custname , custcode  order by custname";
	    		
			    String queryWithMaxIntervalsOnly = "select min(dateFacturation),max(dateFacturation), avg ( DATE_PART('days', NOW() - dateFacturation) ) as days, count(custcode ) ,sum(amount) as amount ,custname, custcode, sum((select  sum(amount) from interaudit.payments where fk_facture = invoices.id )) as payments from  interaudit.invoices invoices where  ( DATE_PART('days', NOW() - dateFacturation) > :maxDays ) and status in ('sent')  group by custname , custcode  order by custname";
			    
			    String queryWithMaxIntervalsAndCustcode ="select min(dateFacturation),max(dateFacturation), avg ( DATE_PART('days', NOW() - dateFacturation) ) as days, count(custcode ) ,sum(amount) as amount ,custname, custcode, sum((select  sum(amount) from interaudit.payments where fk_facture = invoices.id )) as payments from  interaudit.invoices invoices where  ( DATE_PART('days', NOW() - dateFacturation) > :maxDays ) and status in ('sent') and custcode = :custCode  group by custname , custcode  order by custname";
	    		
	    		if (minDays !=-1 && custCode != null && custCode.length()>0){
	    			resultList = (List<Object[]>)em.createNativeQuery(queryWithMinMaxIntervalsAndCustcode).setParameter("minDays",minDays).setParameter("maxDays",maxDays).setParameter("custCode",custCode).getResultList();
	    		}
	    		else if (minDays !=-1 && custCode == null ){
	    			resultList = (List<Object[]>)em.createNativeQuery(queryWithMinMaxIntervals).setParameter("minDays",minDays).setParameter("maxDays",maxDays).getResultList();
	    		}
	    		else if (minDays ==-1 && custCode != null && custCode.length()>0){
	    			resultList = (List<Object[]>)em.createNativeQuery(queryWithMaxIntervalsAndCustcode).setParameter("maxDays",maxDays).setParameter("custCode",custCode).getResultList();
	    		}
	    		else if (minDays ==-1 && custCode == null ){
	    			resultList = (List<Object[]>)em.createNativeQuery(queryWithMaxIntervalsOnly).setParameter("maxDays",maxDays).getResultList();
	    		}
	    			
	            if (resultList.size() > 0) {
	            	
	            	for(int i = 0;i<resultList.size();i++){
						Object[] resultat = resultList.get(i);
						
						
						String customerName =  (String)resultat[5] ;
						String customerCode =  (String)resultat[6] ;
						double amount =  resultat[4]==null?0.0d:(Double)resultat[4] ;
						double amountPaid =  resultat[7]==null?0.0d:(Double)resultat[7] ;						
						Double averageNdDays=  resultat[2]==null?0.0f:(Double)resultat[2] ;
						
						BalanceAgeeData balanceAgeeData = mapData.get(customerCode);
						if(balanceAgeeData == null){
							balanceAgeeData = new BalanceAgeeData( customerName,  customerCode,amountPaid);
							balanceAgeeData.updateAmountArray(averageNdDays.intValue(), (amount-amountPaid));
							mapData.put(customerCode, balanceAgeeData);
									 
						}
						else{
							balanceAgeeData.updateAmountArray(averageNdDays.intValue(), (amount-amountPaid));
							balanceAgeeData.setAmountPaid(balanceAgeeData.getAmountPaid()+amountPaid);
						}
						
						
					}		            	
	            	
	            }
	            
	        	}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  loadInvoiceTranslationsForLanguage..."));
			}
			finally{
	    		em.close();
	    	}
	 }
    
    
   
    
    public BalanceAgeeView buildBalanceAgeeView(SearchBalanceAgeeParam param){
    	try{
    		
    		Map<String,BalanceAgeeData> mapData = new HashMap<String,BalanceAgeeData >();
    		     		 
    		SearchInvoiceParam paramSearch = new  SearchInvoiceParam(null,null,null,null,"1",null,null,null, null);
    		 
    		//Do not use pagination to build the balance agée
    	    List<InvoiceData>  invoices =  searchInvoices( paramSearch,false ,-1,-1);
    	   
    	    for(InvoiceData invoiceData : invoices){
    		   if (invoiceData.isExcludedFromBalanceAgee() == false){    			 
	    	    	String customerName =  invoiceData.getCustomerName() ;
					String customerCode =  invoiceData.getCustomerCode() ;
					double amount =  invoiceData.getAmountEuroTva() ;
					double amountPaid =  invoiceData.getAmountPaidEuro() ;	
					Date facturation = invoiceData.getDateFacturation();
					int averageNdDays = DateUtils.getNbDays(facturation);
					/*
					 * Prise en compte du total de note crédit enregistré pour la facture
					 * Doit être ajouter au total déja payé pour la facture
					 */
					double totalNoteDeCredit = calculateTotalNoteDeCreditForInvoice(invoiceData.getReference());
					amountPaid += totalNoteDeCredit;
					
					BalanceAgeeData balanceAgeeData = mapData.get(customerCode);
					if(balanceAgeeData == null){
						balanceAgeeData = new BalanceAgeeData( customerName,  customerCode,amountPaid);
						balanceAgeeData.updateAmountArray(averageNdDays, (amount-amountPaid));
						mapData.put(customerCode, balanceAgeeData);								
					}
					else{
						balanceAgeeData.updateAmountArray(averageNdDays, (amount-amountPaid));
						balanceAgeeData.setAmountPaid(balanceAgeeData.getAmountPaid()+amountPaid);
					}
    		   }
    	    }

    	   
    		List<BalanceAgeeData> items = new ArrayList(mapData.values());
    		
    		return new  BalanceAgeeView( items, param);
    				
    		
    	}
    	catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  buildBalanceAgeeView..."));

		}finally{
			em.close();
		}
    }
    
    
    /**
	 * @return a list of mission with the mandat that need to be invoice as the employers have started to charge on them alraedy
	 */
	public Long countRemindersForInvoice(Long invoiceId){
		try{
			
			
			
			//Building the queryString
			StringBuffer searchQuery = new StringBuffer("select count(*) from interaudit.INVOICE_REMINDS_RELATION where invoiceid = " + invoiceId);	
			
			Query q = em.createNativeQuery(searchQuery.toString());
			@SuppressWarnings("unchecked")
			java.math.BigInteger result = (java.math.BigInteger) q.getSingleResult();
			
			if(result == null)return 0L;
			 return result.longValue();
			}catch(DaoException daoe){	
			  logger.error(daoe.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("FactureDao : Failed in countRemindersForInvoice...") ) ;		 
		  }
			catch(BusinessException e){
		  		throw e;
		  	}catch(Exception e){	 
			  logger.error(e.getMessage());
		 		 throw new BusinessException(new ExceptionMessage("FactureDao : Failed in countRemindersForInvoice...") ) ;		 
		  }
		  finally{
				em.close();
		  }  
	}
	
	public long getTotalCount(SearchInvoiceParam param){
		try{

			Map<String, Object> parameters = new HashMap<String, Object>();						
			//String groupByExpression="group by f.id,f.type,f.dateFacturation,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro,  f.reference,c.companyName,c.associeSignataire.code,f.status,f.amountEuro,f.language,f.refAssSec,f.parentReference,f.dateFacturation,f.customerCode";	        
			
			//StringBuilder hql = new StringBuilder("select count(*) from Invoice f  left join f.payments p join f.project m join f.project.annualBudget.contract.customer c   ");
			
			StringBuilder hql = new StringBuilder("select count(*) from Invoice f join f.project m join f.project.annualBudget.contract.customer c   ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	        if (param.getYear()!= null && param.getYear().length() > 0) {
	            parameters.put("year", param.getYear());
	            whereClause.append("( upper(f.exercise) = upper(:year))");
	        }
	        
	        if (param.getMois()!= null && param.getYear().length() > 0 && !param.getMois().equalsIgnoreCase("-1")) {
	        	if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("mois", param.getMois());
	            whereClause.append("( upper(f.mois) = upper(:mois))");
	        }
	        
	        //Rechercher le status
	        if (param.getListOfStatus() != null && param.getListOfStatus().isEmpty()== false) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("status", param.getListOfStatus());
	            whereClause.append("( f.status in (:status) )");
	        }
	        
	      //Rechercher le customer
	        if (param.getCustomer() != null && param.getCustomer().length() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerName","%" + param.getCustomer() + "%" );
	            whereClause.append("upper(c.companyName) like upper(:customerName)");
	        }
	        
	      //Rechercher le manager
	        if (param.getType() != null && param.getType().length() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("type", "%" + param.getType() +"%");
	            whereClause.append("upper(f.type) like upper(:type)");
	        }
	        
	      //Rechercher la reference
	        if (param.getInvoiceId() != null && param.getInvoiceId().length() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("reference", "%" + param.getInvoiceId() +"%");
	            whereClause.append("upper(f.reference) like upper(:reference)");
	        }
	        
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	       //hql.append(groupByExpression);	
	        
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	        Number result = (Number) q.getSingleResult();
	        
			 if ( result == null) {
			     return 0;
			 } else {
			     return result.longValue();
			 }
			

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getTotalCount()..."));

		}finally{
			em.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<InvoiceData>  searchInvoices(  SearchInvoiceParam param,boolean usePagination ,int firstPos,int LINEPERPAGE){
		
		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			
					
		 	//String groupByExpression="group by f.id,f.type,f.dateFacture,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro,  f.reference,c.companyName,c.associeSignataire.code,f.status,f.amountEuro,f.language,f.refAssSec,f.parentReference";
			//String groupByExpression="group by f.id,f.type,f.dateFacturation,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro,  f.reference,c.companyName,c.associeSignataire.code,f.status,f.amountEuro,f.language,f.refAssSec,f.parentReference,f.dateFacturation,f.customerCode";
	        //StringBuilder hql = new StringBuilder("select new com.interaudit.domain.model.data.InvoiceData(f.id,f.type,f.dateFacture,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountEuro, f.reference, c.companyName,c.associeSignataire.code, f.status,  p==null?0.0f:sum(p.amount)) from Invoice f left join f.payments p join f.project m join f.project.annualBudget.contract.customer c   ");
			//StringBuilder hql = new StringBuilder("select f.id,f.type,f.dateFacture,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro, f.reference, c.companyName,c.associeSignataire.code, f.status, sum(p.amount),f.amountEuro,f.language, count(r) from Invoice f left join f.rappels r left join f.payments p join f.project m join f.project.annualBudget.contract.customer c   ");
		 	//StringBuilder hql = new StringBuilder("select f.id,f.type,f.dateFacture,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro, f.reference, c.companyName,c.associeSignataire.code, f.status, sum(p.amount),f.amountEuro,f.language,f.refAssSec,f.parentReference from Invoice f  left join f.payments p join f.project m join f.project.annualBudget.contract.customer c   ");
			
			//StringBuilder hql = new StringBuilder("select f.id,f.type,f.dateFacturation,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro, f.reference, c.companyName,c.associeSignataire.code, f.status, sum(p.amount),f.amountEuro,f.language,f.refAssSec,f.parentReference,f.dateFacturation,f.customerCode from Invoice f  left join f.payments p join f.project m join f.project.annualBudget.contract.customer c   ");
			
			//StringBuilder hql = new StringBuilder("select f.id,f.type,f.dateFacturation,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro, f.reference, c.companyName,c.associeSignataire.code, f.status,f.amountEuro,f.language,f.refAssSec,f.parentReference,f.dateFacturation,f.customerCode from Invoice f  left join f.payments p join f.project m join f.project.annualBudget.contract.customer c   ");
			
			StringBuilder hql = new StringBuilder("select f.id,f.type,f.dateFacturation,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro, f.reference, c.companyName,c.associeSignataire.code, f.status, f.amountEuro,f.language,f.refAssSec,f.parentReference,f.dateFacturation,f.customerCode,f.excludedFromBalanceAgee from Invoice f join f.project m join f.project.annualBudget.contract.customer c   ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	        if (param.getYear()!= null && param.getYear().length() > 0) {
	            parameters.put("year", param.getYear());
	            whereClause.append("( upper(f.exercise) = upper(:year))");
	        }
	        
	        if (param.getMois()!= null && param.getYear().length() > 0 && !param.getMois().equalsIgnoreCase("-1")) {
	        	if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("mois", param.getMois());
	            whereClause.append("( upper(f.mois) = upper(:mois))");
	        }
	        
	        //Rechercher le status
	        if (param.getListOfStatus() != null && param.getListOfStatus().isEmpty()== false) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("status", param.getListOfStatus());
	            whereClause.append("( f.status in (:status) )");
	        }
	        
	      //Rechercher le customer
	        if (param.getCustomer() != null && param.getCustomer().length() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerName","%" + param.getCustomer() + "%" );
	            whereClause.append("upper(c.companyName) like upper(:customerName)");
	        }
	        
	      //Rechercher le manager
	        if (param.getType() != null && param.getType().length() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("type", "%" + param.getType() +"%");
	            whereClause.append("upper(f.type) like upper(:type)");
	        }
	        
	      //Rechercher la reference
	        if (param.getInvoiceId() != null && param.getInvoiceId().length() > 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("reference", "%" + param.getInvoiceId() +"%");
	            whereClause.append("upper(f.reference) like upper(:reference)");
	        }
	        
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	       // hql.append(groupByExpression);
	       // hql.append(" ORDER BY c.companyName asc ");
	       // hql.append(" ORDER BY f.dateFacture asc ");
	        hql.append(" ORDER BY f.dateFacturation asc ");
	        
	        
	        
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	    
	        //Gestion de la pagination
	        if(usePagination == true){
	        	q.setFirstResult(firstPos);
		        q.setMaxResults(LINEPERPAGE);
	        }
	       
	        List<Object[]> resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {
				
				List<com.interaudit.domain.model.data.InvoiceData> results = new ArrayList<com.interaudit.domain.model.data.InvoiceData>();
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					
					Long id = ((Long)resultat[0]).longValue(); 
					String type  =  (String)resultat[1]; 
					Date dateFacture =  resultat[2] == null?null:(Date)resultat[2]; 
					Date sentDate =  resultat[3] == null?null:(Date)resultat[3]; 
					Date dateOfPayment  =  resultat[4] == null?null:(Date)resultat[4]; 
					String exercise  =  (String)resultat[5]; 
					String libelle =  (String)resultat[6]; 
					Double amountEuro =  resultat[7] == null?0.0d:(Double)resultat[7]; 
					String reference  =  (String)resultat[8]; 
					String customerName  =  (String)resultat[9]; 
					String associeCode  =  (String)resultat[10];  
					String status   =  (String)resultat[11];  
					//Double amountPaidEuro=   calculateTotalPaidForInvoice(reference);//resultat[12] == null?0.0d:(Double)resultat[12]; 
					Double amountEuroTva=  resultat[12] == null?0.0d:(Double)resultat[12]; 
					String language =  (String)resultat[13]; 
					Long countRappels = 0L; 
					
					//Building the queryString
					StringBuffer searchQuery = new StringBuffer("select count(*) from interaudit.INVOICE_REMINDS_RELATION where invoiceid = " + id);						
					Query q1 = em.createNativeQuery(searchQuery.toString());
					java.math.BigInteger result1 = (java.math.BigInteger) q1.getSingleResult();					
					if(result1 == null){
						countRappels = 0L;
					}
					else{
						countRappels = result1.longValue();
					}
					
					  
					 
					String parentReference = (String)resultat[15];
					Date dateFacturation =  resultat[16] == null?null:(Date)resultat[16];
					String customerCode  =  (String)resultat[17];
					
					boolean excludedFromBalanceAgee =  resultat[18] == null?false:(Boolean)resultat[18];
					
					Double amountPaidEuro= calculateTotalPaidForInvoice(reference);
					double totalNoteDeCredit = 0.0d;//this.calculateTotalNoteDeCreditForInvoice(reference.trim());
					
					Number result = (Number) em
				       .createQuery(
				               "select sum(f.amountEuro) from Invoice f where f.type='CN' and f.parentReference = :parentReference")
				       .setParameter("parentReference", reference).getSingleResult();
					if ( result == null) {
						totalNoteDeCredit = 0.0d;
					} else {
						totalNoteDeCredit = result.doubleValue();
					}
					

					results.add(new InvoiceData( id,  type,  dateFacture,  sentDate,
							 dateOfPayment,  exercise,  libelle,
							 amountEuro,  reference,  customerName,
							 associeCode,  status, amountPaidEuro,amountEuroTva,language,countRappels,parentReference,totalNoteDeCredit,dateFacturation,customerCode,excludedFromBalanceAgee) );
																	
				}
					
				return	results;
				
            }else{
            	return null;
            }

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  searchInvoices..."));

		}finally{
			em.close();
		}
		
		
	}
	
	 public List<RemindInvoice>  searchInvoiceReminders(SearchInvoiceReminderParam param){
		 
		
		 try{
				
				Map<String, Object> parameters = new HashMap<String, Object>();
				
						
			 	//String groupByExpression="group by f.id,f.type,f.dateFacture,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro,  f.reference,c.companyName,c.associeSignataire.code,f.status,f.amountEuro,f.language";
		        //StringBuilder hql = new StringBuilder("select new com.interaudit.domain.model.data.InvoiceData(f.id,f.type,f.dateFacture,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountEuro, f.reference, c.companyName,c.associeSignataire.code, f.status,  p==null?0.0f:sum(p.amount)) from Invoice f left join f.payments p join f.project m join f.project.annualBudget.contract.customer c   ");
				//StringBuilder hql = new StringBuilder("select f.id,f.type,f.dateFacture,f.sentDate,f.dateOfPayment,f.exercise,f.libelle,f.amountNetEuro, f.reference, c.companyName,c.associeSignataire.code, f.status, sum(p.amount),f.amountEuro,f.language, count(r) from RemindInvoice f left join f.rappels r left join f.payments p join f.project m join f.project.annualBudget.contract.customer c   ");
			 	StringBuilder hql = new StringBuilder("select r from RemindInvoice r  join r.customer c ");
		        StringBuilder whereClause = new StringBuilder("");
		        
		        
		        //Rechercher les années
		        if (param.getYear()!= null && param.getYear().length() > 0) {
		            parameters.put("year",Integer.parseInt(param.getYear()) );
		            whereClause.append(" r.exercise = :year ");
		        }
		        
		         /*
		        //Rechercher le status actif
		        if (param.getListOfActiveStatus() != null && param.getListOfActiveStatus().isEmpty()== false) {
		            if (whereClause.length() > 0) {
		                whereClause.append(" AND ");
		            }
		            parameters.put("statusActive", param.getListOfActiveStatus());
		            whereClause.append("( r.active in (:statusActive) )");
		        }
		        */
		        
		       //Rechercher le status sent		        
		        if (param.getListOfSentStatus() != null && param.getListOfSentStatus().isEmpty()== false) {
		            if (whereClause.length() > 0) {
		                whereClause.append(" AND ");
		            }
		            parameters.put("statusSent", param.getListOfSentStatus());
		            whereClause.append("( r.sent in (:statusSent) )");
		        }
			      
			        
		      //Rechercher le customer
		        if (param.getCustomer() != null && param.getCustomer().length() > 0) {
		            if (whereClause.length() > 0) {
		                whereClause.append(" AND ");
		            }
		            parameters.put("customerName","%" + param.getCustomer() + "%" );
		            whereClause.append("upper(c.companyName) like upper(:customerName)");
		        }
		        
		      
		        
		      //Rechercher la reference
		        /*
		        if (param.getInvoiceReminderId() != null && param.getInvoiceReminderId().length() > 0) {
		            if (whereClause.length() > 0) {
		                whereClause.append(" AND ");
		            }
		            parameters.put("reference", "%" + param.getInvoiceReminderId() +"%");
		            whereClause.append("upper(f.reference) like upper(:reference)");
		        }
		        */
		        
		        
		        if (whereClause.length() > 0) {
		            hql.append(" WHERE ").append(whereClause);
		        }
		       // hql.append(groupByExpression);
		       hql.append(" ORDER BY r.order, r.remindDate asc ");
		        
		        Query q = em.createQuery(hql.toString());
		        for (Map.Entry<String, Object> me : parameters.entrySet()) {
		            q.setParameter(me.getKey(), me.getValue());
		           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
		        }
		    
		        //return q.getResultList();
		       
		        List<RemindInvoice> resultList = (List<RemindInvoice>) q.getResultList();
		        return	resultList;

			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  searchInvoiceReminders..."));

			}finally{
				em.close();
			}
		 
		 
	 }
	
	
	
    public int getMaxYearSequence(String year) {
    	
    	try{    		
    		Number result = (Number) em
            .createQuery(
                    "select count(*) from Invoice f where f.exercise = :year and f.reference like 'F-%'")
            .setParameter("year", year).getSingleResult();
		    if ( result == null) {
		        return 0;
		    } else {
		        return result.intValue();
		    }

		}
    	catch(javax.persistence.NoResultException e){	
    		return 0; 
    	}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getMaxYearSequence..."));

		}finally{
			em.close();
		}
		
        
    }
    
public int getMaxYearApprovedInvoiceSequence(String year) {
	
		try{    		
    		Number result = (Number) em
            .createQuery(
                   // "select count(*) from Invoice f where f.exercise = :year and  f.status = 'approved'")
            	"select count(*) from Invoice f where f.exercise = :year and  f.status <> 'pending'")
            .setParameter("year", year).getSingleResult();
		    if ( result == null) {
		        return 0;
		    } else {
		        return result.intValue();
		    }

		}
    	catch(javax.persistence.NoResultException e){	
    		return 0; 
    	}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getMaxYearApprovedInvoiceSequence..."));

		}finally{
			em.close();
		}
		
        
    }
    
    
    
    public int getMaxYearSequenceForDraft(String year) {
    	
    	try{
    		
    		Number result = (Number) em
            .createQuery(
                    "select count(*) from Invoice f where f.exercise = :year and f.reference like 'FB-%'")
            .setParameter("year", year).getSingleResult();
		    if ( result == null) {
		        return 0;
		    } else {
		        return result.intValue();
		    }

		}
    	catch(javax.persistence.NoResultException e){	
    		return 0; 
    	}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getMaxYearSequenceForDraft..."));

		}finally{
			em.close();
		}
		
        
    }
    
    
    
    public int getMaxYearSequenceForNc(String year) {
    	
    	try{
    		
    		Number result = (Number) em
            .createQuery(
                    "select count(*) from Invoice f where f.exercise = :year and f.reference like 'NC-%'")
            .setParameter("year", year).getSingleResult();
		    if ( result == null) {
		        return 0;
		    } else {
		        return result.intValue();
		    }

		}
    	catch(javax.persistence.NoResultException e){	
    		return 0; 
    	}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getMaxYearSequenceForNc..."));

		}finally{
			em.close();
		}
		
        
    }
    
    public int getMaxYearSequenceForNcDraft(String year) {
    	
    	try{    		
    		Number result = (Number) em
            .createQuery(
                    "select count(*) from Invoice f where f.exercise = :year and f.reference like 'NCB-%'")
            .setParameter("year", year).getSingleResult();
		    if ( result == null) {
		        return 0;
		    } else {
		        return result.intValue();
		    }

		}
    	catch(javax.persistence.NoResultException e){	
    		return 0; 
    	}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getMaxYearSequenceForNcDraft..."));

		}finally{
			em.close();
		}	 
    }
    
    
    
    
    
    
    
     public int getNextSequenceForReminderForCustomer(Long idCustomer) {
    	
    	try{
    		int year = Calendar.getInstance().get(Calendar.YEAR);
    		Number result = (Number) em
            .createQuery(
                    "select count(*) from RemindInvoice r where r.exercise = :year and r.customer.id = :idCustomer")
            .setParameter("year", year).setParameter("idCustomer", idCustomer).getSingleResult();
		    if ( result == null) {
		        return 0;
		    } else {
		        return result.intValue();
		    }

		}
    	catch(javax.persistence.NoResultException e){	
    		return 0;	 
    	}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getNextSequenceForReminderForCustomer..."));

		}finally{
			em.close();
		}
		
        
    }
     
     
     
     public Date getDatePaymentForInvoice(Long idFacture){
    	 try{    		
    		
    		 Query result = (Query) em.createQuery(
                     "select max(p.paymentDate) from Payment p where p.facture.id = :idFacture")
             .setParameter("idFacture", idFacture).getSingleResult();
 		    if ( result == null) {
 		        return null;
 		    } else {
 		        return (Date) result.getSingleResult();
 		    }

 		}
     	catch(javax.persistence.NoResultException e){	
     		return null; 
     	}
 		catch(Exception e){
 			logger.error(e.getMessage());
 			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getDatePaymentForInvoice..."));

 		}finally{
 			em.close();
 		}	 
     }
    
    
   public double  calculateTotalWorkedHoursForMission(Mission project){
	   /*
	   Number result = (Number) em
       .createQuery(
               "select count(*) from Invoice f where f.exercise = :year")
       .setParameter("year", year).getSingleResult();
		if ( result == null) {
		   return 0;
		} else {
		   return result.intValue();
		}
		*/
	   return 0.0;
    }
 	
 /* (non-Javadoc)
 * Total number of advances requested for the mission
 */
public double calculateTotalAdvancesForMission(Mission project){	
	try{		
		Number result = (Number) em
	       .createQuery(
	               "select sum(f.amountNetEuro) from Invoice f where f.type='AD' and f.project.id = :idProject")
	       .setParameter("idProject", project.getId()).getSingleResult();
			if ( result == null) {
			   return 0;
			} else {
			   return result.doubleValue();
			}

	}
	catch(javax.persistence.NoResultException e){	
		return 0.0;
	}
	catch(Exception e){	
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  calculateTotalAdvancesForMission..."));
	}finally{
		em.close();
	}   
 }


public int findAdvancesNotSentForMission(Long projectId){
	
	try{	
		List<String> listOfStatuses = new ArrayList<String>();
		listOfStatuses.add(Invoice.FACTURE_STATUS_CODE_PENDING);
		listOfStatuses.add(Invoice.FACTURE_STATUS_CODE_APPROVED);
		Number result = (Number) em
	       .createQuery(
	               "select count(f) from Invoice f where f.type='AD' and f.project.id = :idProject and  f.status in (:statuses) ")
	       .setParameter("idProject", projectId).setParameter("statuses", listOfStatuses).getSingleResult();
			if ( result == null) {
			   return 0;
			} else {
			   return result.intValue();
			}

	}
	catch(javax.persistence.NoResultException e){	
		return 0;
	}
	catch(Exception e){	
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  findAdvancesNotSentForMission..."));
	}finally{
		em.close();
	}   
 }

public List<Invoice> getNoteDeCreditForInvoice(String reference){
	try{	
		 Query q = em.createQuery("select f from Invoice f where f.type='CN' and f.parentReference = :parentReference").setParameter("parentReference", reference);
		 List<Invoice> resultList = (List<Invoice>) q.getResultList();
	     return	resultList;
	}
	catch(Exception e){		
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getNoteDeCreditForInvoice..."));
	}finally{
		em.close();
	}   
}


public double calculateTotalNoteDeCreditForInvoice(String reference){
	try{	
		
		Number result = (Number) em
	       .createQuery(
	               "select sum(f.amountEuro) from Invoice f where f.type='CN' and f.parentReference = :parentReference")
	       .setParameter("parentReference", reference).getSingleResult();
			if ( result == null) {
			   return 0;
			} else {
			   return result.doubleValue();
			}
			

	}
	catch(javax.persistence.NoResultException e){	
		return 0.0; 
	}
	catch(Exception e){	
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  calculateTotalNoteDeCreditForInvoice..."));
	}finally{
		em.close();
	}   
}
public double calculateTotalPaidForInvoice(String reference){
	try{	
		double resultat = 0.0;
		 Query q = em.createQuery("select p from Payment p where p.facture.reference = :reference").setParameter("reference", reference);
		 List<Payment> resultList = (List<Payment>) q.getResultList();
		 for(Payment payment : resultList){
			 
			 if(payment.isReimbourse()){
				 resultat -=  payment.getAmount();
			 }
			 else{
				 resultat +=  payment.getAmount();
			 }
			 
		 }
		 
		 return resultat;
		/*
		Number result = (Number) em
	       .createQuery(
	               "select sum(amount) from Payment p where p.facture.reference = :reference")
	       .setParameter("reference", reference).getSingleResult();
			if ( result == null) {
			   return 0;
			} else {
			   return result.doubleValue();
			}
			*/

	}
	catch(javax.persistence.NoResultException e){	
		return 0.0; 
	}
	catch(Exception e){	
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  calculateTotalPaidForInvoice..."));
	}finally{
		em.close();
	}   
}


public void getAdvancesForMission(Long idMission,List<Invoice> resultList){
	// List<Invoice> resultList = new ArrayList<Invoice>();
	try{		
		Mission mission = (Mission)em.find(Mission.class, idMission);
		if (mission == null) {
			throw new DaoException(2);
		}
		else{
			Mission missionParent = mission.getParent();
			if(missionParent != null){
				 getAdvancesForMission(missionParent.getId(),resultList);
			}
			
		}
		
		 Query q = (Query) em
	       .createQuery(
	               "select f from Invoice f where f.type='AD' and f.project.id = :idProject")
	       .setParameter("idProject", idMission);
		 @SuppressWarnings("unchecked")
		 List<Invoice> resultList2 = (List<Invoice>)q.getResultList()  ;
		 resultList.addAll(resultList2);
	   //  return	resultList;			
	}
	catch(Exception e){		
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getAdvancesForMission..."));
	}finally{
		em.close();
	}   
}

@Override
public boolean findFinalBillForMission(Long idMission){
	try{		
		 Query q = (Query) em
	       .createQuery(
	               "select count(f) from Invoice f where f.type='FB' and f.project.id = :idProject")
	       .setParameter("idProject", idMission);
		 @SuppressWarnings("unchecked")
		 Number result = (Number) q.getSingleResult() ;
		 if(result == null || result.intValue() == 0){
			 Mission mission = (Mission)em.find(Mission.class, idMission);
			 if (mission == null) {
				throw new DaoException(2);
			 }
			 Mission missionParent = mission.getParent();
			 if(missionParent != null){
				 return findFinalBillForMission(missionParent.getId());
			 }
			 else{
				 return false;
			 }
		 }
		 else{
			 return true;
		 }
				
	}
	catch(Exception e){		
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  findFinalBillForMission..."));
	}finally{
		em.close();
	}   
}


	public double getTotalInvoicedForContract(Long idContract, String year,String exerciseMandat){
		
		try{			
			
			//Calcul du total sans notes de credit 
			Number result1 = (Number) em
		       .createQuery(
		               "select sum(f.amountNetEuro) from Invoice f where f.type !='CN' and f.project.annualBudget.contract.id = :idContract and f.exercise = :year and f.exerciseMandat =:mandat")
		       .setParameter("idContract", idContract).setParameter("year", year).setParameter("mandat", exerciseMandat).getSingleResult();
			
			//Calcul du total des notes de credit
			Number result2 = (Number) em
		       .createQuery(
		               "select sum(f.amountNetEuro) from Invoice f where f.type ='CN' and f.project.annualBudget.contract.id = :idContract and f.exercise = :year and f.exerciseMandat =:mandat")
		       .setParameter("idContract", idContract).setParameter("year", year).setParameter("mandat", exerciseMandat).getSingleResult();
			
			double res1,res2;
			if ( result1 == null) {
				res1 = 0.0;
			} else {
			   res1 = result1.doubleValue();
			}
			
			if ( result2 == null) {
				res2 = 0.0;
			} else {
			   res2 = result2.doubleValue();
			}
			
			//Fiare la difference
			return res1-res2;
				

		}
		catch(javax.persistence.NoResultException e){	
			return 0.0;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getTotalInvoicedForContract..."));

		}finally{
			em.close();
		}
		
		
	}
	
	 public double getTotalInvoicedForExercise(int year){
		 try{
				Number result1 = (Number) em
			       .createQuery(
			               "select sum(f.amountNetEuro) from Invoice f where f.type !='CN' and f.exercise=:exercise ").setParameter("exercise", Integer.toString(year)).getSingleResult();			               			       
				
				Number result2 = (Number) em
			       .createQuery(
			               "select sum(f.amountNetEuro) from Invoice f where f.type ='CN' and f.exercise=:exercise ").setParameter("exercise", Integer.toString(year)).getSingleResult();			               			       
				
				double res1,res2;
				if ( result1 == null) {
					res1 = 0.0;
				} else {
				   res1 = result1.doubleValue();
				}
				
				if ( result2 == null) {
					res2 = 0.0;
				} else {
				   res2 = result2.doubleValue();
				}
				
				//Faire la difference
				return res1-res2;
			}
			catch(javax.persistence.NoResultException e){	
				return 0.0;
			}
			catch(Exception e){	
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getTotalInvoicedForExercise..."));
			}finally{
				em.close();
			} 
	 }
	 
	 public double getTotalInvoicedForMission(Long idMission){
		 try{
				Number result1 = (Number) em
			       .createQuery(
			               "select sum(f.amountNetEuro) from Invoice f where f.type !='CN' and  f.project.id=:idMission ").setParameter("idMission", idMission).getSingleResult();			               			       
				
				Number result2 = (Number) em
			       .createQuery(
			               "select sum(f.amountNetEuro) from Invoice f where f.type ='CN' and  f.project.id=:idMission ").setParameter("idMission", idMission).getSingleResult();			               			       
				
				double res1,res2;
				if ( result1 == null) {
					res1 = 0.0;
				} else {
				   res1 = result1.doubleValue();
				}
				
				if ( result2 == null) {
					res2 = 0.0;
				} else {
				   res2 = result2.doubleValue();
				}
				
				//Faire la difference
				return res1-res2;
			}
			 catch(javax.persistence.NoResultException e){	
				 return 0.0;
			 }
			catch(Exception e){	
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getTotalInvoicedForMission..."));
			}finally{
				em.close();
			} 
	 }
	
	public double getTotalInvoicedForMonthAndManager(int month, int year, Long idManager){
		try{
			
			//Compute the dates intervals
			/*
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.YEAR, year);
		    calendar.set(Calendar.MONTH, month);
		    calendar.set(Calendar.DAY_OF_MONTH, 1);
		    Date firstDate = calendar.getTime();
		    
		    calendar.add(Calendar.MONTH, 1);
		    calendar.add(Calendar.DAY_OF_MONTH, -1);
		    Date secondDate = calendar.getTime();
		
			Number result1 = (Number) em
		       .createQuery(
		               "select sum(f.amountNetEuro) from Invoice f where f.type !='CN' and f.project.annualBudget.budgetManager.id = :idManager and f.dateFacturation BETWEEN :d1 AND :d2")
		               .setParameter("d1", firstDate).setParameter("d2", secondDate).setParameter("idManager", idManager).getSingleResult();
			
			Number result2 = (Number) em
		       .createQuery(
		               "select sum(f.amountNetEuro) from Invoice f where f.type ='CN' and f.project.annualBudget.budgetManager.id = :idManager and f.dateFacturation BETWEEN :d1 AND :d2")
		               .setParameter("d1", firstDate).setParameter("d2", secondDate).setParameter("idManager", idManager).getSingleResult();
			*/
			
			Number result1 = (Number) em
		       .createQuery(
		               "select sum(f.amountNetEuro) from Invoice f where f.type !='CN' and f.project.annualBudget.budgetManager.id = :idManager and f.exercise = :year and f.mois = :mois")
		               .setParameter("mois", Integer.toString(month)).setParameter("year", Integer.toString(year)).setParameter("idManager", idManager).getSingleResult();
			
			Number result2 = (Number) em
		       .createQuery(
		               "select sum(f.amountNetEuro) from Invoice f where f.type ='CN' and f.project.annualBudget.budgetManager.id = :idManager and f.exercise = :year and f.mois = :mois")
		               .setParameter("mois", Integer.toString(month)).setParameter("year", Integer.toString(year)).setParameter("idManager", idManager).getSingleResult();
		       
			double res1,res2;
			if ( result1 == null) {
				res1 = 0.0;
			} else {
			   res1 = result1.doubleValue();
			}
			
			if ( result2 == null) {
				res2 = 0.0;
			} else {
			   res2 = result2.doubleValue();
			}
			
			//Faire la difference
			return res1-res2;

		}
		catch(javax.persistence.NoResultException e){	
			return 0.0; 
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getTotalInvoicedForMonthAndManager..."));

		}finally{
			em.close();
		}
		
	}
	
	
	 public double getTotalInvoicedForMonthAndAssociate(int month, int year, Long responsable){

		 try{
			 	//Compute the dates intervals
				Calendar calendar = Calendar.getInstance();
			    calendar.set(Calendar.YEAR, year);
			    calendar.set(Calendar.MONTH, month);
			    calendar.set(Calendar.DAY_OF_MONTH, 1);
			    Date firstDate = calendar.getTime();
			    
			    calendar.add(Calendar.MONTH, 1);
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    Date secondDate = calendar.getTime();
			
				Number result1 = (Number) em
			       .createQuery(
			               "select sum(f.amountNetEuro) from Invoice f where f.type !='CN' and f.project.annualBudget.associeSignataire.id = :idAssociate and f.dateFacturation BETWEEN :d1 AND :d2")
			               .setParameter("d1", firstDate).setParameter("d2", secondDate).setParameter("idAssociate", responsable).getSingleResult();
			       
				Number result2 = (Number) em
			       .createQuery(
			               "select sum(f.amountNetEuro) from Invoice f where f.type ='CN' and f.project.annualBudget.associeSignataire.id = :idAssociate and f.dateFacturation BETWEEN :d1 AND :d2")
			               .setParameter("d1", firstDate).setParameter("d2", secondDate).setParameter("idAssociate", responsable).getSingleResult();
			       
				double res1,res2;
				if ( result1 == null) {
					res1 = 0.0;
				} else {
				   res1 = result1.doubleValue();
				}
				
				if ( result2 == null) {
					res2 = 0.0;
				} else {
				   res2 = result2.doubleValue();
				}
				
				//Faire la difference
				return res1-res2;

			}
			 catch(javax.persistence.NoResultException e){	
				return 0.0; 
			 }
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in FactureDao :  getTotalInvoicedForMonthAndAssociate..."));

			}finally{
				em.close();
			}
	 }
 	
   public double calculateTotalExtraCostsForMission(Mission project){
	   /*
	   Number result = (Number) em
       .createQuery(
               "select count(*) from Invoice f where f.exercise = :year")
       .setParameter("year", year).getSingleResult();
		if ( result == null) {
		   return 0;
		} else {
		   return result.intValue();
		}
		*/
	   return 0.0;
   }
    

	
	

}
