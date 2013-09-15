package com.interaudit.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.interaudit.domain.model.AddtionalFeeInvoice;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.DeductionFeeInvoice;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Payment;
import com.interaudit.domain.model.RemindInvoice;
import com.interaudit.domain.model.data.MissionInfo;
import com.interaudit.domain.model.data.MissionToCloseData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.exc.ExcessiveAmountException;
import com.interaudit.service.param.SearchBalanceAgeeParam;
import com.interaudit.service.param.SearchInvoiceParam;
import com.interaudit.service.param.SearchInvoiceReminderParam;
import com.interaudit.service.param.SearchPaymentParam;
import com.interaudit.service.view.BalanceAgeeView;
import com.interaudit.service.view.InvoiceReminderView;
import com.interaudit.service.view.InvoiceView;
import com.interaudit.service.view.PaymentsView;

/**
 * Model describing service methods that must be provided in order to handle
 * details of a Project.
 * 
 * @author Valentin Carnu
 * 
 */
public interface IFactureService {
	public BalanceAgeeView buildBalanceAgeeView(SearchBalanceAgeeParam param);
	public void checkAllUnpaidAndSentInvoices();
	public List<MissionToCloseData> listBudgetsToClose(Integer currentYear);
	public RemindInvoice markAsSentReminderInvoice(Long reminderId,Employee currentUser);
	public void updateExerciceAndBudget(Invoice facture);
	public int getMaxYearSequence(String year);
	public Long addFraisToInvoice(Long invoiceId,String codeFrais,String lang);
	public void removeFraisFromInvoice(Long fraisId);
	public Invoice updateInvoiceAmountTotal(Long factureId);
	public Map<String, List<Invoice>> buildMapReminders();
	public RemindInvoice createFactureReminder(Employee creator,Long customerId,String lang);	
	
	
	public Long addDeductionToInvoice(Long invoiceId,String code,String lang); 
	public void removeDeductionFromInvoice(Long id);
	public DeductionFeeInvoice updateDeductionLibelle(Long id, String libelle);
	public DeductionFeeInvoice updateInvoiceDeductionValue(Long id,Double value);
	public Invoice approveInvoice(Long factureId ,Employee currentUser );
	public Invoice markAsSentInvoice(Long factureId ,Employee currentUser );
	
	
	public Payment buildPaymentFromFacture(Long invoiceid);
	public int findAdvancesNotSentForMission(Long projectId);
	    
	/**
	 * @param missionId
	 * @param libelle
	 * @return
	 */
	public Invoice createFactureForMission(Long projectId,Employee creator,String type,double tva,double honoraires,int delaiPaiement,String language,Long bankId,String exercice);
	public Invoice createNoteDeCreditForMission(Invoice facture,Employee creator,String type,double tva,double honoraires,boolean interne,boolean forceCreation)throws ExcessiveAmountException;
	
	// get a invoice by its id
    public Invoice getOne(Long id);

    // get all invoices
    public List<Invoice> getAll();
    
    public Invoice getOneDetachedFromReference(String reference);
    
    public Invoice getOneDetached(Long id);
    
    double getTotalInvoicedForContract(Long id, String year,String mandat);
    
    public double getTotalInvoicedForMonthAndManager(int month, int year, Long responsable);
    
    public double getTotalInvoicedForMonthAndAssociate(int month, int year, Long responsable);
    
    public double getTotalInvoicedForExercise( int year);
    
    public double calculateTotalAmountNet(Invoice facture);
    public double calculateTotalAmount(Invoice facture);
    public double calculateTotalAmountTotalVat(Invoice facture);
    
    public MissionInfo buildMissionInfo(Mission mission,Customer customer);
    
    public AddtionalFeeInvoice updateFraisLibelle(Long fraisId,String libelle);

    public Invoice updateInvoiceLibDelai(Long invoiceId,String libelle);

    public Invoice updateInvoiceLibHonoraires(Long invoiceId,String libelle);

    public Invoice updateInvoiceAdresse(Long invoiceId, String addresse);

    public AddtionalFeeInvoice updateInvoiceFraisValue(Long fraisId ,Double value);

    public Invoice updateInvoiceHonoraires(Long invoiceId,Double value);

    public Invoice updateInvoiceTva(Long invoiceId ,Double value );

    public Invoice updateInvoiceLang(Long invoiceId,String lang);
    
    public Date updateInvoiceDateFacturation(Long invoiceId,String lang);
    
    public Invoice updateInvoiceCustomer(Long invoiceId ,Long customerId);
    

    
    
    /**
     * @param model
     * @return
     */
    public List<Invoice> getAllLibelleLike(String model);

    // save an invoice
    public Invoice saveOne(Invoice facture);

    // update  an invoice
    public Invoice updateOne(Invoice facture);
    
    // update  an invoice
    public RemindInvoice updateOneReminder(RemindInvoice reminder);
    
    public RemindInvoice saveOneReminder(RemindInvoice reminder,Employee sender);

    // delete  an invoice by its id
    public void deleteOne(Long id);
    
    // delete  a payment by its id
    public void deletePayment(Long paymentId);   
    
    public void deleteOneFee(Long id);

    /**
     * 
     * @param userId
     * @return
     */
    public List<Invoice> getAllFactureByCustomerId(Long customerId);
    
    public long getTotalCount(SearchInvoiceParam param);
    public InvoiceView  searchInvoices( SearchInvoiceParam param,boolean usePagination ,int firstPos,int LINEPERPAGE);
    
    public InvoiceView  getInvoiceFromIdentifier(SearchInvoiceParam param,boolean usePagination ,int firstPos,int LINEPERPAGE);
    
  
    
    public List<Option> getInvoiceStatusOptions();
    
    public List<Option> getInvoiceTypeOptions();
    
    public PaymentsView  searchPayments( SearchPaymentParam param);
    
    public PaymentsView  getPaymentFromIdentifier(SearchPaymentParam param);
    
    public PaymentsView  getPaymentFromInvoiceIdentifier(SearchPaymentParam param);
    
    public void updateInvoiceStatusafterPayment(Invoice facture);
    
    public double getTotalInvoicedForMission(Long idMission);
    
    public List<Invoice> getAllUnpaidInvoices();
    
    public InvoiceReminderView  searchInvoiceReminders(SearchInvoiceReminderParam param);
    
    public InvoiceReminderView  getsearchInvoiceRemindersFromIdentifier(SearchInvoiceReminderParam param);
    
    
    public RemindInvoice getOneRemindInvoiceDetached(Long id);
    
    public abstract boolean findFinalBillForMission(Long idMission);
    
    public Invoice updateInvoiceCodeTvaZero(Long invoiceId, String tvaZeroLibCode);
    
    public Invoice changeExcludedFromBalanceAgeeStatus(Long invoiceId);


   
}
