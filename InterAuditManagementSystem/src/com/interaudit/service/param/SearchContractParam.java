package com.interaudit.service.param;

public class SearchContractParam {
    private String expression;
    private String startingLetterName;
    
    public SearchContractParam() {
		super();
		
	}
	public SearchContractParam(String expression,String startingLetterName) {
		super();
		this.expression = expression;
		this.startingLetterName=startingLetterName;
	}

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

	public String getStartingLetterName() {
		return startingLetterName;
	}

	public void setStartingLetterName(String startingLetterName) {
		this.startingLetterName = startingLetterName;
	}

	
    
}
