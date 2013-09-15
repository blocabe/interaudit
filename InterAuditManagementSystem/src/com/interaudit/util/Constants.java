package com.interaudit.util;

import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public final class Constants {
    private Constants() {
        
    }
    
    public static final String ALL_VALUES = "*";

    public static final Format YEAR_SEQUENCE_FORMATTER = new MessageFormat("{0,number,000}");
    public static final String REPORT_OUTPUT_FORMAT_HTML = "html";
    public static final String REPORT_OUTPUT_FORMAT_PDF = "pdf";

    public static final Map<String, String> BIRT_OUTPUT_FORMATS = new HashMap<String, String>();
    /*
    static {
        BIRT_OUTPUT_FORMATS.put(REPORT_OUTPUT_FORMAT_HTML, RenderOption.OUTPUT_FORMAT_HTML);
        BIRT_OUTPUT_FORMATS.put(REPORT_OUTPUT_FORMAT_PDF, RenderOption.OUTPUT_FORMAT_PDF);
    }
    */

    public static final Integer VERSION_NUMBER_FIRST_VALUE = 1;

    //The below group of constants should refer to the contents of R_USER_ROLES reference table
    public static final String ROLE_NAME_SENIOR_MANAGER = "SENIOR MANAGER";
    public static final String ROLE_NAME_SENIOR = "SENIOR";
    public static final String ROLE_NAME_ASSOCIE = "ASSOCIE";
    public static final String ROLE_NAME_SECRETAIRE = "SECRETAIRE";
    public static final String ROLE_NAME_MANAGER = "MANAGER";
    public static final String ROLE_NAME_ASSISTANT = "ASSISTANT";
    public static final String ROLE_NAME_ASSISTANT_MANAGER = "ASSISTANT MANAGER";
    public static final String ROLE_NAME_ADMIN = "ADMIN";
    public static final String ROLE_NAME_SUPERVISOR = "SUPERVISOR";
    
    public static final String ROLE_NAME_SENIOR_MANAGER_CODE = "SM";
    public static final String ROLE_NAME_SENIOR_CODE = "SE";
    public static final String ROLE_NAME_ASSOCIE_CODE = "ASS";
    public static final String ROLE_NAME_SECRETAIRE_CODE = "SEC";
    public static final String ROLE_NAME_MANAGER_CODE = "M";
    public static final String ROLE_NAME_ASSISTANT_CODE = "AS";
    public static final String ROLE_NAME_ASSISTANT_MANAGER_CODE = "AM";
    public static final String ROLE_NAME_ADMIN_CODE = "ADMIN";
    public static final String ROLE_NAME_SUPERVISOR_CODE = "SP";
    

    //The below group of constants should refer to the contents of R_PROJECT_STATUS reference table
    public static final String PROJECT_STATUS_CODE_REQUESTED = "RQST";
    public static final String PROJECT_STATUS_CODE_ACCEPTED = "ACCP";
    public static final String PROJECT_STATUS_CODE_COMPLETED = "COMP";
    public static final String PROJECT_STATUS_CODE_REJECTED = "RJCD";
    public static final String PROJECT_STATUS_CODE_SUSPENDED = "SUSP";
    public static final String PROJECT_STATUS_CODE_CANCELLED = "CANC";
    public static final String PROJECT_STATUS_CODE_ONGOING = "ONGO";
    public static final String PROJECT_STATUS_CODE_HOLD = "HOLD";

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat DEFAULT_DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

   
    
    //The below group should refer to the R_TIMESHEET_TYPE reference table
    public static final Long TIMESHEET_ROW_TYPE_ID_PROJECT = 1L;
    public static final Long TIMESHEET_ROW_TYPE_ID_ACTION_PLAN = 2L;
   
    //The below group should refer to the R_TIMESHEET_STATUS reference table
    public static final Long TIMESHEET_STATUS_ID_DRAFT = 1L;
    public static final Long TIMESHEET_STATUS_ID_SUBMITTED = 2L;
    public static final Long TIMESHEET_STATUS_ID_VALIDATED = 3L;
       
    public static final String WEEK_ASSIGNMENT_ACTIVITY_CODE_PROJECT = "PJ";
    public static final String WEEK_ASSIGNMENT_ACTIVITY_CODE_CONGE = "CONGE";
    public static final String WEEK_ASSIGNMENT_ACTIVITY_CODE_FERIE = "FERIE";
    public static final String WEEK_ASSIGNMENT_ACTIVITY_CODE_MALADIE = "MALADIE ";
    
    //Status of the factures
   
    
//  Status of the factures
    public static final String FACTURE_TYPE_ACOMPTE = "ACOMPTE";
    public static final String FACTURE_TYPE_FINAL = "FINAL";
    
    
    //The below group should refer to the R_TIMESHEET_STATUS reference table
    public static final String TIMESHEET_STATUS_STRING_DRAFT = "DRAFT";
    public static final String TIMESHEET_STATUS_STRING_SUBMITTED = "SUBMITTED";
    public static final String TIMESHEET_STATUS_STRING_VALIDATED = "VALIDATED";
    
    public static final String WEEKPLANNING_STATUS_STRING_DRAFT = "DRAFT";
    public static final String WEEKPLANNING_STATUS_STRING_SUBMITTED = "SUBMITTED";
    
    
    

}
