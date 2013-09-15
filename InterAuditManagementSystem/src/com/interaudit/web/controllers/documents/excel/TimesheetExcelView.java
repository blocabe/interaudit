package com.interaudit.web.controllers.documents.excel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetCell;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.service.view.WeeklyTimeSheetView;



public class TimesheetExcelView extends AbstractExcelView{
	
	
	private static final String[] titles = {
        "Nom du client",	"Numero", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun",
	        "Total\nHrs", "Overtime\nHrs", "Regular\nHrs"
	};
	/*
	private static Object[][] sample_data = {
	        {"Yegor Kozlov", "YK", 5.0, 8.0, 10.0, 5.0, 5.0, 7.0, 6.0},
	        {"Gisella Bronzetti", "GB", 4.0, 3.0, 1.0, 3.5, null, null, 4.0},
	};

	*/
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map model, HSSFWorkbook classeur,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		WeeklyTimeSheetView view = (WeeklyTimeSheetView) model.get("timesheetView");
		
		Timesheet timesheet = view.getTimesheet();
    	
			//Create the styles first
		Map<String, CellStyle> styles = createStyles(classeur);

		HSSFSheet sheet = classeur.createSheet("Timesheet");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Weekly Timesheet");
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$L$1"));

        //header row
        HSSFRow headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        HSSFCell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        Set<TimesheetRow> rows = timesheet.getRows();
        for(TimesheetRow row : rows){
        	
        	if(row.getActivity() != null){
        		
        		HSSFRow Excelrow = sheet.createRow(rownum++);
        		TimesheetCell timesheetCell = null;
        		for (int j = 0; j < titles.length; j++) {
                	
        			HSSFCell cell = Excelrow.createCell(j);
                	
                	switch(j){
                	
	                	case 0:
	                		cell.setCellStyle(styles.get("cell"));
	                		cell.setCellValue(row.getLabel());
	                		break;
	                		
	                	case 1:
	                		cell.setCellStyle(styles.get("cell"));
	                		cell.setCellValue(row.getCustNumber());
	                		break;
	                		
	                	case 2:	 
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("1");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}	                		
	                		break;
	                		
	                	case 3:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("2");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}	                	
	                		break;
	                		
	                	case 4:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("3");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}	                		
	                		break;
	                		
	                	case 5:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("4");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}
	                		break;
	                	
	                	case 6:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("5");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}
	                		break;
	                		
	                	case 7:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("6");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}
	                		break;
	                	
	                	case 8:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("7");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}
	                		break;
	                		
	                	case 9:
	                		cell.setCellStyle(styles.get("formula"));
	                		cell.setCellValue(row.getTotalOfHours());
	                		break;
	                	
	                	case 10:
	                		cell.setCellStyle(styles.get("cell"));
	                		cell.setCellValue(row.getTotalOfExtraHours());
	                		break;
	                		
	                	case 11:
	                		cell.setCellStyle(styles.get("formula"));
	                		cell.setCellValue(row.getTotalOfHours() + row.getTotalOfExtraHours());
	                		break;
	                		
                	}
                	
        		}
        		
        	}
        
        }
        rownum++;
        for(TimesheetRow row : rows){
        	
        	if(row.getActivity() == null){
        		
        		HSSFRow Excelrow = sheet.createRow(rownum++);
        		TimesheetCell timesheetCell = null;
        		for (int j = 0; j < titles.length; j++) {
                	
        			HSSFCell cell = Excelrow.createCell(j);
                	
                	switch(j){
                	
	                	case 0:
	                		cell.setCellStyle(styles.get("cell"));
	                		cell.setCellValue(row.getLabel());
	                		break;
	                		
	                	case 1:
	                		cell.setCellStyle(styles.get("cell"));
	                		cell.setCellValue(row.getCustNumber());
	                		break;
	                		
	                	case 2:	
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("1");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}	                		
	                		break;
	                		
	                	case 3:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("2");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}	                	
	                		break;
	                		
	                	case 4:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("3");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}	                		
	                		break;
	                		
	                	case 5:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("4");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}
	                		break;
	                	
	                	case 6:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("5");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}
	                		break;
	                		
	                	case 7:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("6");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}
	                		break;
	                	
	                	case 8:
	                		cell.setCellStyle(styles.get("cell"));
	                		timesheetCell = row.getCellsMap().get("7");
	                		if(timesheetCell != null){
	                			cell.setCellValue(timesheetCell.getValue());
	                		}
	                		break;
	                		
	                	case 9:
	                		cell.setCellStyle(styles.get("formula"));
	                		cell.setCellValue(row.getTotalOfHours());
	                		break;
	                	
	                	case 10:
	                		cell.setCellStyle(styles.get("cell"));
	                		cell.setCellValue(row.getTotalOfExtraHours());
	                		break;
	                		
	                	case 11:
	                		cell.setCellStyle(styles.get("formula"));
	                		cell.setCellValue(row.getTotalOfHours() + row.getTotalOfExtraHours());
	                		break;
	                		
                	}
        		}
        	}
        
        }
        /*
        for (int i = 0; i < 10; i++) {
        	HSSFRow row = sheet.createRow(rownum++);
            for (int j = 0; j < titles.length; j++) {
            	HSSFCell cell = row.createCell(j);
                if(j == 9){
                    //the 10th cell contains sum over week days, e.g. SUM(C3:I3)
                    String ref = "C" +rownum+ ":I" + rownum;
                    cell.setCellFormula("SUM("+ref+")");
                    cell.setCellStyle(styles.get("formula"));
                } else if (j == 11){
                    cell.setCellFormula("J" +rownum+ "-K" + rownum);
                    cell.setCellStyle(styles.get("formula"));
                } else {
                    cell.setCellStyle(styles.get("cell"));
                }
            }
        }
          */
        //row with totals below
        HSSFRow sumRow = sheet.createRow(rownum++);
        sumRow.setHeightInPoints(35);
        HSSFCell cell;
        cell = sumRow.createCell(0);
        cell.setCellStyle(styles.get("formula"));
        cell = sumRow.createCell(1);
        cell.setCellValue("Total Hrs:");
        cell.setCellStyle(styles.get("formula"));

        for (int j = 2; j < 9; j++) {
            cell = sumRow.createCell(j);
            float totalHours = calculateTotalForDay(j-1,timesheet.getRows());
           // String ref = (char)('A' + j) + "3:" + (char)('A' + j) + "12";
           // cell.setCellFormula("SUM(" + ref + ")");
           // if(j >= 9) cell.setCellStyle(styles.get("formula_2"));
            //else cell.setCellStyle(styles.get("formula"));
            cell.setCellStyle(styles.get("formula"));
            cell.setCellValue(totalHours);
        }
        
       float totalNormalHours = calculateTotalNormalHours(timesheet.getRows());
       float totalExtraHours = calculateTotalExtraHours(timesheet.getRows());
       
       cell = sumRow.createCell(9);
       cell.setCellStyle(styles.get("formula_2"));
       cell.setCellValue(totalNormalHours);
       
       cell = sumRow.createCell(10);
       cell.setCellStyle(styles.get("formula_2"));
       cell.setCellValue(totalExtraHours);
       
       cell = sumRow.createCell(11);
       cell.setCellStyle(styles.get("formula_2"));
       cell.setCellValue(totalExtraHours + totalNormalHours);
        
        
        rownum++;
        sumRow = sheet.createRow(rownum++);
        sumRow.setHeightInPoints(25);
        cell = sumRow.createCell(0);
        cell.setCellValue("Total Regular Hours");
        cell.setCellStyle(styles.get("formula"));
        cell = sumRow.createCell(1);
        cell.setCellValue(totalNormalHours);
        //cell.setCellFormula("L13");
        cell.setCellStyle(styles.get("formula_2"));
        sumRow = sheet.createRow(rownum++);
        sumRow.setHeightInPoints(25);
        cell = sumRow.createCell(0);
        cell.setCellValue("Total Overtime Hours");
        cell.setCellStyle(styles.get("formula"));
        cell = sumRow.createCell(1);
        //cell.setCellFormula("K13");
        cell.setCellValue(totalExtraHours);
        cell.setCellStyle(styles.get("formula_2"));
       
/*
        //set sample data
        for (int i = 0; i < sample_data.length; i++) {
        	HSSFRow row = sheet.getRow(2 + i);
            for (int j = 0; j < sample_data[i].length; j++) {
                if(sample_data[i][j] == null) continue;

                if(sample_data[i][j] instanceof String) {
                    row.getCell(j).setCellValue((String)sample_data[i][j]);
                } else {
                    row.getCell(j).setCellValue((Double)sample_data[i][j]);
                }
            }
        }
		*/
        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 30*256); //30 characters wide
        for (int i = 2; i < 9; i++) {
            sheet.setColumnWidth(i, 6*256);  //6 characters wide
        }
        sheet.setColumnWidth(10, 10*256); //10 characters wide

		    
			
			
	}
	
	
	private float calculateTotalForDay(int indexDay,Set<TimesheetRow> rows){
		  TimesheetCell timesheetCell = null;
		  float ret = 0.0f;
		  for(TimesheetRow row : rows){
			  timesheetCell = row.getCellsMap().get(Integer.toString(indexDay));
			  if(timesheetCell != null){
				  ret += timesheetCell.getValue();
			  }
		  }
		  
		  return ret;
	}
	
	private float calculateTotalNormalHours(Set<TimesheetRow> rows){
		 
		  float ret = 0.0f;
		  for(TimesheetRow row : rows){
			  
				  ret += row.getTotalOfHours();
			 
		  }
		  
		  return ret;
	}
	
	
	private float calculateTotalExtraHours(Set<TimesheetRow> rows){
		 float ret = 0.0f;
		  for(TimesheetRow row : rows){
			  
				  ret += row.getTotalOfExtraHours();
			 
		  }
		  
		  return ret;
	}
	
	
	/**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(HSSFWorkbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)11);
        monthFont.setColor(IndexedColors.BLACK.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);

        return styles;
    }

	
	
	
    

}
