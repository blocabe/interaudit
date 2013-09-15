package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class MessageData implements Serializable , Comparable<MessageData>{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public MessageData(Long id, String subject, String contents, String from,
			Long fromId, String to, Long toId, Date createDate, Date sentDate,
			Long missionId, String customerName, boolean read) {
		super();
		this.id = id;
		this.subject = subject;
		this.contents = contents;
		this.from = from;
		this.fromId = fromId;
		this.to = to;
		this.toId = toId;
		this.createDate = createDate;
		this.sentDate = sentDate;
		this.missionId = missionId;
		this.customerName = customerName;
		this.read = read;
	}




	private Long id;
	
	
	
	private String subject;
	private String contents;
	
	
	private String from;
	private Long fromId;
	
	private String to;
	private Long toId;
	
	private Date createDate;
	private Date sentDate;
	
	private Long missionId;
	private String customerName;
	
	private boolean read = false;
	
	/**
	 * Default constructor
	 */
	public MessageData() {
		super();
	}
	
	
	
	
	public int compareTo(MessageData o)
    {
		MessageData m = (MessageData)o;
		return createDate.compareTo(m.createDate);
    }




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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




		public String getFrom() {
			return from;
		}




		public void setFrom(String from) {
			this.from = from;
		}




		public Long getFromId() {
			return fromId;
		}




		public void setFromId(Long fromId) {
			this.fromId = fromId;
		}




		public String getTo() {
			return to;
		}




		public void setTo(String to) {
			this.to = to;
		}




		public Long getToId() {
			return toId;
		}




		public void setToId(Long toId) {
			this.toId = toId;
		}




		public Long getMissionId() {
			return missionId;
		}




		public void setMissionId(Long missionId) {
			this.missionId = missionId;
		}




		public String getCustomerName() {
			return customerName;
		}




		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

}
