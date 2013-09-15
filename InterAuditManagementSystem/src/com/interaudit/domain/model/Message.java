package com.interaudit.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "MESSAGE",schema="interaudit")
public class Message implements Serializable , Comparable<Message>{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator=" MessageSeq")
	@SequenceGenerator(name=" MessageSeq",sequenceName="MESSAGE_SEQ", allocationSize=1)
	*/
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Message parent;
	
	private String subject;
	private String contents;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Employee from;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Employee to;
	private Date createDate;
	private Date sentDate;
	private String emailsList;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Mission mission;
	
	private boolean read = false;
	
	/**
	 * Default constructor
	 */
	public Message() {
		super();
	}
	
	
	
	public Message(Message parent, String subject, String contents,
			Employee from, /*Employee to,*/String emailsList) {
		super();
		this.parent = parent;
		this.subject = subject;
		this.contents = contents;
		this.from = from;
		//this.to = to;
		this.createDate = new Date();
		this.read = false;
		this.emailsList = emailsList;
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Message)  )){
			return false;
		}
		else{
			return this.getId().equals(((Message)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public int compareTo(Message o)
    {
		Message m = (Message)o;
		return createDate.compareTo(m.createDate);
    }




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Message getParent() {
		return parent;
	}
	public void setParent(Message parent) {
		this.parent = parent;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Employee getFrom() {
		return from;
	}
	public void setFrom(Employee from) {
		this.from = from;
	}
	public Employee getTo() {
		return to;
	}
	public void setTo(Employee to) {
		this.to = to;
	}



	public Mission getMission() {
		return mission;
	}



	public void setMission(Mission mission) {
		this.mission = mission;
	}



	public Date getCreateDate() {
		return createDate;
	}



	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}



	public Date getSentDate() {
		return sentDate;
	}



	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	
	  public String getContentsTextHtmlFormatted (){
	    	if(this.contents == null){
	    		return null;
	    	}else{        		
	    		String result = this.contents.replaceAll("\r\n", "<br>");
	    		return searchAndReplaceUrl(result);
	    		
	    		
	    	}
	    }
	    
	    /**
	     * @param inputString
	     * @return
	     */
	    public static String searchAndReplaceUrl(String inputString){
			CharSequence inputStr = inputString;
		    
			//String patternStr ="(?:(?:ht|f)tp(?:s?)\\:\\/\\/){1}(?:\\w+:\\w+@)?(?:(?:[-\\w]+\\.)+(?:aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z]{2}))(?::[\\d]{1,5})?(?:(?:(?:/(?:[-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|/)+|\\?|#)?(?:(?:\\?(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)(?:&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*(?:#(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?";
			
			//String patternStr = "(?:(?:ht|f)tp(?:s?):[a-zA-Z0-9/:?=&-.]+)+";
			
			//String patternStr = "(?:(?:ht|f)tp(?:s?):[a-zA-Z0-9/:?=&-.%]+)+";
			
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



		public boolean isRead() {
			return read;
		}



		public void setRead(boolean read) {
			this.read = read;
		}



		public String getEmailsList() {
			return emailsList;
		}



		public void setEmailsList(String emailsList) {
			this.emailsList = emailsList;
		}

}
