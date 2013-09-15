package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.Bank;

public class BanksView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<Bank>  banks;
	
	
	
	public BanksView(List<Bank> banks) {
		super();
		this.banks = banks;
	}



	public BanksView() {
		super();
		// TODO Auto-generated constructor stub
	}



	public List<Bank> getBanks() {
		return banks;
	}



	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}



	
	

	
	
	

}
