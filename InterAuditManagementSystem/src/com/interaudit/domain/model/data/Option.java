package com.interaudit.domain.model.data;

import java.io.Serializable;

public class Option implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String name;
    private String longName;
    private String selected = "false";
    private boolean needUpdate;
    
    public Option(String id,String name){
    	this.id = id;
        this.name = name;
    }
    
    public Option(String id,String name,boolean needUpdate){
    	this(id,name);
        this.needUpdate = needUpdate;
    }
    
    public Option(String id,String name,String selected){
    	this.id = id;
        this.name = name;
        this.selected = selected;
    }
    
    public Option(String id,String name,String longName,String selected){
    	this.id = id;
        this.name = name;
        this.longName = longName;
        this.selected = selected;
    }
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public boolean isNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}
    
    
}
