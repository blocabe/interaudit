package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.Declaration;

public class DeclarationsView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<Declaration>  declarations;
	
	
	public DeclarationsView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DeclarationsView(List<Declaration> declarations) {
		super();
		this.declarations = declarations;
	}


	public List<Declaration> getDeclarations() {
		return declarations;
	}


	public void setDeclarations(List<Declaration> declarations) {
		this.declarations = declarations;
	}

	



}
