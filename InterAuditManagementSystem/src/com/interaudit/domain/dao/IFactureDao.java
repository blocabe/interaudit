package com.interaudit.domain.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.interaudit.domain.model.AddtionalFeeInvoice;
import com.interaudit.domain.model.DeductionFeeInvoice;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.RemindInvoice;
import com.interaudit.domain.model.data.InvoiceData;
import com.interaudit.service.param.SearchBalanceAgeeParam;
import com.interaudit.service.param.SearchInvoiceParam;
import com.interaudit.service.param.SearchInvoiceReminderParam;
import com.interaudit.service.view.BalanceAgeeView;

public interface IFactureDao {

	 public BalanceAgeeView buildBalanceAgeeView(SearchBalanceAgeeParam param);
	// get a fee by its id
    public AddtionalFeeInvoice getOneFee(Long id);    
    public AddtionalFeeInvoice updateOneFee(AddtionalFeeInvoice fee);    
    public void deleteOneFee(Long id);
   
    public void deleteOneDeduction(Long id);
    public DeductionFeeInvoice getOneDeduction(Long id);
    public DeductionFeeInvoice updateOneDeduction(DeductionFeeInvoice fee);
    public int getNextSequenceForReminderForCustomer(Long idCustomer);
    public Invoice saveInvoiceReminderRelation(Long invoiceId,Long reminderId);
    
    // get a contact by its id
    public Invoice getOne(Long id);

    // get all contacts
    public List<Invoice> getAll();
    
    public List<Invoice> getAllUnpaidInvoices();
    
    public List<Invoice> getAllUnpaidAndSentInvoices();
    
    public List<Invoice> getAllUnpaidAndSentInvoicesForCustomer(Long customerId);
    
    public void getAdvancesForMission(Long idMission,List<Invoice> listInvoices);
    
    public List<Invoice> getNoteDeCreditForInvoice(String reference);
    
    public int findAdvancesNotSentForMission(Long projectId);
    
    /**
     * @param model
     * @return
     */
    public List<Invoice> getAllLibelleLike(String model);

    // save a contact
    public Invoice saveOne(Invoice facture);

    // update a contact
    public Invoice updateOne(Invoice contact);
    
    public RemindInvoice updateOneReminder(RemindInvoice reminder);
    
    public RemindInvoice saveOneReminder(RemindInvoice reminder);

    // delete a contact by its id
    public void deleteOne(Long id);
    
    
    
    public Invoice getOneDetachedFromReference(String reference);
    
    public Invoice getOneDetached(Long id);
    
    public RemindInvoice getOneRemindInvoiceDetached(Long id);

    /**
     * 
     * @param userId
     * @return
     */
    public List<Invoice> getAllFactureByCustomerId(Long customerId);
    
    public long getTotalCount(SearchInvoiceParam param);
    public List<InvoiceData>  searchInvoices(  SearchInvoiceParam param ,boolean usePagination ,int firstPos,int LINEPERPAGE);
    
    public List<RemindInvoice>  searchInvoiceReminders(SearchInvoiceReminderParam param);
    
 
    
    public int getMaxYearSequence(String year);
    public int getMaxYearSequenceForNc(String year);
    public int getMaxYearSequenceForDraft(String year);
    public int getMaxYearSequenceForNcDraft(String year);
    public int getMaxYearApprovedInvoiceSequence(String year);
    
    double  calculateTotalWorkedHoursForMission(Mission project);
 	
    double calculateTotalAdvancesForMission(Mission project);
 	
    double calculateTotalExtraCostsForMission(Mission project);
    
    public List<InvoiceData> getInvoiceFromIdentifier(String identifier);
    
    public double getTotalInvoicedForContract(Long id, String year,String mandat);
    
    public double getTotalInvoicedForMonthAndManager(int month, int year, Long responsable);
    
    public double getTotalInvoicedForMonthAndAssociate(int month, int year, Long responsable);

    public double getTotalInvoicedForExercise( int year);
    
    public double getTotalInvoicedForMission(Long idMission);
    
    public Map<String, String>  loadInvoiceTranslationsForLanguage(String language);
    
    
    public double calculateTotalNoteDeCreditForInvoice(String reference);
    public double calculateTotalPaidForInvoice(String reference);
	public abstract boolean findFinalBillForMission(Long idMission);
	
	public Date getDatePaymentForInvoice(Long idFacture);
    
   

}
