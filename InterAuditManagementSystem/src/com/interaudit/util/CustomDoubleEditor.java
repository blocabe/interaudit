package com.interaudit.util;

import java.beans.PropertyEditorSupport;

public class CustomDoubleEditor extends PropertyEditorSupport {

	public CustomDoubleEditor() { 
	} 

	public String getAsText() { 
	Double d = (Double) getValue(); 
	return d.toString(); 
	} 

	public void setAsText(String str) { 
	if( str == "" || str == null ) 
	setValue(0); 
	else {
		try{
			setValue(Double.parseDouble(str)); 
		}catch(NumberFormatException nfe){
			setValue(0);
		}
		
	}
	
	} 
}
