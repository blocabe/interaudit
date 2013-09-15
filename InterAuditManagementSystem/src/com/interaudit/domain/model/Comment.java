package com.interaudit.domain.model;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENTS",schema="interaudit")
public class Comment implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	private Long id;
	

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_employee" , nullable = false)
	private Employee validator;
    
    private String text;
    protected Date created;
    protected Date sent;
    private String sender_address;
	private String receiver_address;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_timesheet" , nullable = false)
	private Timesheet timesheet;

    public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

    public Comment(String text, Employee validator,  String sender_address,
	 String receiver_address) {
		super();
		this.text = text;
		this.validator = validator;
		this.created = new Date();
		this.sender_address = sender_address;
		this.receiver_address = receiver_address;
	}
    
    
    public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Comment)  )){
			return false;
		}
		else{
			return this.getId().equals(((Comment)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}

	public String getTextHtmlFormatted (){
    	if(this.text == null){
    		return null;
    	}else{        		
    		String result = this.text.replaceAll("\r\n", "<br>");
    		return searchAndReplaceUrl(result);
   
    	}
    }
    
    /**
     * @param inputString
     * @return
     */
    public static String searchAndReplaceUrl(String inputString){
		CharSequence inputStr = inputString;
	    
		
		String patternStr = "(?:(?:ht|f)tp(?:s?):[a-zA-Z0-9_/:?=&-.%]+)+";

	    // Compile regular expression
	    Pattern pattern = Pattern.compile(patternStr);
	    Matcher matcher = pattern.matcher(inputStr);
	    
	    // Replace all occurrences of pattern in input
	    StringBuffer buf = new StringBuffer();
	   
	    while (( matcher.find())) {
	        // Get the match result
	        String replaceStr = matcher.group();
	        
	        replaceStr =" <A href='" + replaceStr +"' target=_blank>" + replaceStr + "</A> " ;
	    
	        // Insert replacement
	        matcher.appendReplacement(buf, replaceStr);
	    }
	    
	    matcher.appendTail(buf);
	    
	    // Get result
	    return buf.toString();	    
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getValidator() {
		return validator;
	}

	public void setValidator(Employee validator) {
		this.validator = validator;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Timesheet getTimesheet() {
		return timesheet;
	}

	public void setTimesheet(Timesheet timesheet) {
		this.timesheet = timesheet;
	}

	public Date getSent() {
		return sent;
	}

	public void setSent(Date sent) {
		this.sent = sent;
	}

	public String getSender_address() {
		return sender_address;
	}

	public void setSender_address(String sender_address) {
		this.sender_address = sender_address;
	}

	public String getReceiver_address() {
		return receiver_address;
	}

	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}
    

}
