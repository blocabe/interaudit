package com.interaudit.service.view;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.interaudit.domain.model.data.BalanceAgeeData;
import com.interaudit.service.param.SearchBalanceAgeeParam;

public class BalanceAgeeView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<BalanceAgeeData>  items =null;
	private SearchBalanceAgeeParam param;
	
	
	
	public BalanceAgeeView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


	public BalanceAgeeView(List<BalanceAgeeData> items,
			SearchBalanceAgeeParam param) {
		super();
		this.items = items;
		this.param = param;
	}




	public List<BalanceAgeeData> getItems() {
		 Collections.sort(items);
		 return items;

	}


	public void setItems(List<BalanceAgeeData> items) {
		this.items = items;
	}


	public SearchBalanceAgeeParam getParam() {
		return param;
	}


	public void setParam(SearchBalanceAgeeParam param) {
		this.param = param;
	}


	

}


