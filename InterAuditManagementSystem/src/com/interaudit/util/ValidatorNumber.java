package com.interaudit.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ValidatorNumber {
	
	/**
	   * Validate the content of a string as a double value.
	   * @param doubleString The string to validate
	   * @return Double containing the value of the string; or null if the string cannot be validated as a double.
	   */
	  public static Number validateStringAsNumber(String doubleString) {
		if(doubleString == null)return null;
		Number result = null;
	    try {
	           java.text.NumberFormat nf = java.text.NumberFormat.getInstance(Locale.FRENCH);
	           result = nf.parse(doubleString);		           

	     } catch(java.text.ParseException  par_e){}
	       catch(NumberFormatException nfe) {}
	       return result;
	  }
	  
	  /**
	   * Validate the content of a string as a double value.
	   * @param doubleString The string to validate
	   * @return Double containing the value of the string; or null if the string cannot be validated as a double.
	   */
	  public static Double validateStringAsDouble(String doubleString) {		  
		if(doubleString == null)return null;
	    Double result = null;
	    try {
	           java.text.NumberFormat nf = java.text.NumberFormat.getInstance(Locale.FRENCH);
	           Number res = nf.parse(doubleString);
	           result = new java.lang.Double(res.doubleValue());

	     } catch(java.text.ParseException  par_e){}
	       catch(NumberFormatException nfe) {}
	       return result;
	  }
	  
	  public static boolean isValueFormatValid(String value){
		  char[] tab = {'a','A','b','B','f','F','d','D','i','I'};
		  if(value == null) return false;
		  for(int i=0; i<tab.length;i++){
			  int char_index = value.indexOf(tab[i]); 
			  if(char_index != -1){
				  return false;
			  }
		  }			  
		  return true;
	  }
	  
	  public static String replaceDecimaSeparatorInValue(String value){
		  
		  if(value == null) return null;
		  
		  int separator_index = value.lastIndexOf(','); 
		  
		  if(separator_index == -1) return value;
		  
		  StringBuffer buffer = new StringBuffer(value); 
		  
		  buffer.setCharAt(separator_index, '.');
		  
		  while( (separator_index = buffer.indexOf(",")) != -1 )buffer= buffer.deleteCharAt(separator_index);
		  
		  return buffer.toString();					
		}
	  
	  
	  public static String removeDecimaSeparatorInValue(String value){
		  
		  if(value == null) return null;
		  
		  int separator_index = value.lastIndexOf(','); 
		  
		  if(separator_index == -1) return value;
		  
		  StringBuffer buffer = new StringBuffer(value); 
		  
		  while( (separator_index = buffer.indexOf(",")) != -1 )buffer= buffer.deleteCharAt(separator_index);
		  
		  return buffer.toString();					
		}
	  
	  
	  public static double formatNumber(double value){
			
		  	String pattern = "###.### ";
			Locale loc = Locale.FRANCE;
			NumberFormat nf = NumberFormat.getNumberInstance(loc);
			DecimalFormat df = (DecimalFormat)nf;
			df.setMinimumFractionDigits(3);
			df.setDecimalSeparatorAlwaysShown(true);
			df.applyPattern(pattern);
			String output = df.format(value);				
			char decimal_separator = new java.text.DecimalFormatSymbols().getDecimalSeparator();	
			int separator_index = output.indexOf('.'); 
			if(separator_index == -1)
				separator_index = output.indexOf(',');
								
			output = output.trim();
			if(separator_index != -1){				
				int lenght = output.length();			
				int diff = lenght - separator_index;			
				if(diff > 2)
					output = output.substring(0, separator_index+3);			
			}
			
			
			StringBuffer buff = new StringBuffer(output);
			if(separator_index != -1){
				buff.setCharAt(separator_index, decimal_separator);
			}
			
			double ret = Double.valueOf(buff.toString()).doubleValue();
			return ret;
		}


}
