package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.RemindInvoice;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchInvoiceReminderParam;

public class InvoiceReminderView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<RemindInvoice>  invoiceReminders;
	private SearchInvoiceReminderParam param;	
	private List<Option> exercicesOptions;
	
	
	
	public InvoiceReminderView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InvoiceReminderView(List<RemindInvoice>  invoiceReminders, SearchInvoiceReminderParam param,
			List<Option> exercicesOptions) {
		super();
		this.invoiceReminders = invoiceReminders;
		this.param = param;		
		this.exercicesOptions = exercicesOptions;
		
	}


	

	

	public List<Option> getExercicesOptions() {
		return exercicesOptions;
	}

	public void setExercicesOptions(List<Option> exercicesOptions) {
		this.exercicesOptions = exercicesOptions;
	}

	public List<RemindInvoice> getInvoiceReminders() {
		return invoiceReminders;
	}

	public void setInvoiceReminders(List<RemindInvoice> invoiceReminders) {
		this.invoiceReminders = invoiceReminders;
	}

	public SearchInvoiceReminderParam getParam() {
		return param;
	}

	public void setParam(SearchInvoiceReminderParam param) {
		this.param = param;
	}

	

}
