package com.interaudit.web.controllers.documents.excel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.interaudit.domain.model.Event;
import com.interaudit.domain.model.EventPlanning;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.view.AnnualPlanningView;



public class PlanningExcelView extends AbstractExcelView{
	
	
	private static final String[]  months = {
        "January", "February", "March","April", "May", "June","July", "August",
        "September","October", "November", "December"};

	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map model, HSSFWorkbook classeur,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
			//Create the styles first
			Map<String, CellStyle> styles = createStyles(classeur);
			
			Calendar calendar = Calendar.getInstance();


			// on récupère la liste des budgets du modele
		    List<AnnualPlanningView> data = (List<AnnualPlanningView>) model.get("data");
			
			for(AnnualPlanningView view : data){
				
				calendar.set(Calendar.YEAR, Integer.parseInt(view.getYear()));
				/*calendar.set(Calendar.MONTH, Integer.parseInt(view.getMonth()));*/
	            
				// on créée une feuille dans le classeur
				/*HSSFSheet sheet = classeur.createSheet(months[Integer.parseInt(view.getMonth())]);*/
				HSSFSheet sheet = null;
				
				//turn off gridlines
	            sheet.setDisplayGridlines(false);
	            sheet.setPrintGridlines(false);
	            sheet.setFitToPage(true);
	            sheet.setHorizontallyCenter(true);
	            PrintSetup printSetup = sheet.getPrintSetup();
	            printSetup.setLandscape(true);
	            
	            //the following three statements are required only for HSSF
	            sheet.setAutobreaks(true);
	            printSetup.setFitHeight((short)1);
	            printSetup.setFitWidth((short)1);
	            
	            //the header row: centered text in 48pt font
	            HSSFRow headerRow = sheet.createRow(0);
	            headerRow.setHeightInPoints(80);
	            HSSFCell titleCell = headerRow.createCell(0);
	           /* titleCell.setCellValue(months[Integer.parseInt(view.getMonth())] + " " + view.getYear());*/
	            titleCell.setCellStyle(styles.get("title"));
	            //sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$N$1"));
	            sheet.addMergedRegion(new CellRangeAddress((short)0,(short) 0, (short)0,(short) (view.getEmployeeOptions().size()+2)));
	            //sheet.addMergedRegion(new Region((short)0,(short) 0, (short)0,(short) (view.getEmployeeOptions().size()+2)));
	            

	          //Create the headers
	            HSSFRow employeeRow = sheet.createRow(1);

	            sheet.setColumnWidth(0, 5*256); //the column is 5 characters wide
                sheet.setColumnWidth(1, 10*256); //the column is 13 characters wide
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
                HSSFCell employeeCell0 = employeeRow.createCell(0);
                employeeCell0.setCellValue("Week/Date");
                employeeCell0.setCellStyle(styles.get("employee"));
				
				
	            for (int i = 0; i < view.getEmployeeOptions().size(); i++) {
	                //set column widths, the width is measured in units of 1/256th of a character width
	                sheet.setColumnWidth((i*2 )+ 2, 5*256); //the column is 5 characters wide
	                sheet.setColumnWidth((i*2) + 3, 13*256); //the column is 13 characters wide
	                //sheet.addMergedRegion(new CellRangeAddress(1, 1, i*2, i*2+1));
	                sheet.addMergedRegion(new CellRangeAddress(1, 1, (i*2 )+ 2, (i*2) + 3));
	               
	                HSSFCell employeeCell = employeeRow.createCell((i + 1 )*2);
	                employeeCell.setCellValue(view.getEmployeeOptions().get(i).getName());
	                employeeCell.setCellStyle(styles.get("employee"));
	            }

				//Add a row for each date
	            //int cnt = 1, day=1;
	            DateFormat dateFormat = new SimpleDateFormat("dd-MM", new Locale("fr","FR"));
	            int rownum = 2;
	            int maxDateInteger = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	            for (int currentDate = 1; currentDate <= maxDateInteger; currentDate++) {
	            	
	            	 
	            	calendar.set(Calendar.DAY_OF_MONTH, currentDate);
	            	/*if(calendar.get(Calendar.MONTH) > Integer.parseInt(view.getMonth())) break;*/
	            	
	            	boolean isWeekend = ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	            	HSSFRow row = sheet.createRow(rownum);
	            	if( isWeekend ){
	            		row.setHeightInPoints(10);
	            	}
	            	else{
	            		row.setHeightInPoints(45);
	            	}
	                
	               
	                sheet.setColumnWidth(0, 5*256); //the column is 5 characters wide
	                sheet.setColumnWidth(1, 13*256); //the column is 13 characters wide
	                sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 1));
	                String currentDateFormated = dateFormat.format(calendar.getTime());
	                int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
	                HSSFCell cell0 = row.createCell(0);
	                cell0.setCellStyle(styles.get("weekend_left"));
	                
	                
	                HSSFCell cell1 = row.createCell(1);
	                cell1.setCellStyle(styles.get("weekend_right"));
	                if( !isWeekend ){
	                	cell0.setCellValue(weekNumber + " / "+currentDateFormated);
	                }
	                
	                
	               
	               
	                
	                
	                for (int i = 0; i < view.getEmployeeOptions().size(); i++) {
	                	 //set column widths, the width is measured in units of 1/256th of a character width
		                sheet.setColumnWidth((i*2 )+ 2, 5*256); //the column is 5 characters wide
		                sheet.setColumnWidth((i*2) + 3, 13*256); //the column is 13 characters wide
		                
		                
		                HSSFCell dayCell_1 = row.createCell((i*2 )+ 2);
		                HSSFCell dayCell_2 = row.createCell((i*2) + 3);
		                
		                
		               if(isWeekend){
		            	   dayCell_1.setCellStyle(styles.get("grey_left"));
	                        dayCell_2.setCellStyle(styles.get("grey_right"));
		               }
		               else{
		            	    dayCell_1.setCellStyle(styles.get("workday_left"));
	                        dayCell_2.setCellStyle(styles.get("workday_right"));
	                       
	                        Option userOption = view.getEmployeeOptions().get(i);
	                        String key = userOption.getId()+"-"+currentDate;				       				
		       				List<EventPlanning> events = null;//view.getActivities().get(key);
		       				
		       				if(events != null ){
		       					StringBuffer buffer = new StringBuffer("");
		       					for(EventPlanning event : events){
		       						/*
		       						double hours = (event.getEndHour() - event.getStartHour()) * 0.25;
		       						int endIndex = event.getType().length() <= 10 ?event.getType().length(): 10;
		       						String temp = event.getType().substring(0,endIndex).toLowerCase() + " [ " +hours +" ]";
		       				  		buffer.append( temp + "\n");
		       				  		*/
		       				  	
		       					}
		       					
		       					dayCell_1.setCellValue(buffer.toString().toLowerCase());
		       				}
	       			
		               }
		               
		                sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, (i*2 )+ 2, (i*2) + 3));
	                }
	                	               
	                rownum++;	               
	            }

			}
		    
			
			
	}
	
	
	
    /**
     * cell styles used for formatting calendar sheets
     */
    private static Map<String, CellStyle> createStyles(HSSFWorkbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();

        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)40);
        titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)12);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        monthFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
       // style.setBorderBottom(CellStyle.BORDER_MEDIUM);
       // style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
       // style.setBorderTop(CellStyle.BORDER_MEDIUM);
       // style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        style.setFont(monthFont);
        styles.put("employee", style);

        Font dayFont = wb.createFont();
        dayFont.setFontHeightInPoints((short)10);
        dayFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(dayFont);
        styles.put("weekend_left", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("weekend_right", style);

        
        Font contentFont = wb.createFont();
        contentFont.setFontHeightInPoints((short)10);
        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contentFont.setColor(IndexedColors.DARK_BLUE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(contentFont);
        style.setWrapText(true);
        styles.put("workday_left", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("workday_right", style);

        style = wb.createCellStyle();
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("grey_left", style);

        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        styles.put("grey_right", style);

        return styles;
    }

}
