package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.data.InvoiceData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchInvoiceParam;

public class InvoiceView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<InvoiceData>  invoices;
	private SearchInvoiceParam param;
	private List<Option> statusOptions;
	private List<Option> exercicesOptions;
	private List<Option> typeOptions;
	
	
	public InvoiceView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InvoiceView(List<InvoiceData>  invoices, SearchInvoiceParam param,
			List<Option> statusOptions, 
			List<Option> exercicesOptions,
			List<Option> typeOptions) {
		super();
		this.invoices = invoices;
		this.param = param;
		this.statusOptions = statusOptions;
		this.exercicesOptions = exercicesOptions;
		this.typeOptions = typeOptions;
	}


	public List<Option> getStatusOptions() {
		return statusOptions;
	}
	
	public void setStatusOptions(List<Option> statusOptions) {
		this.statusOptions = statusOptions;
	}
	

	public List<InvoiceData> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<InvoiceData> invoices) {
		this.invoices = invoices;
	}

	public SearchInvoiceParam getParam() {
		return param;
	}

	public void setParam(SearchInvoiceParam param) {
		this.param = param;
	}

	public List<Option> getExercicesOptions() {
		return exercicesOptions;
	}

	public void setExercicesOptions(List<Option> exercicesOptions) {
		this.exercicesOptions = exercicesOptions;
	}

	public List<Option> getTypeOptions() {
		return typeOptions;
	}

	public void setTypeOptions(List<Option> typeOptions) {
		this.typeOptions = typeOptions;
	}

}
