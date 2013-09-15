package com.interaudit.web.controllers.documents.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.data.AnnualBudgetData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.view.ExerciseGeneralView;

@SuppressWarnings("deprecation")
public class BudgetExcelView extends AbstractExcelView{
	
	private static final String[] titles = {
        "Numero", "Nom du client", "Type", "Origine", "Prevu", "Reporte","Inactif","Facturation","Ass","Man","Commentaire"};
	
	private static final String[] titleResultatMensuelManager ={
		"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin","Juillet","Aout","Septembre","Octobre","Novembre","Decembre"
	};
	
	private static final int[] titlesSize = {
        10, 50, 15, 15, 12, 12,12,15,10,10,50};
	
	
	private static final String[] titlesBugetGeneral = {
        "Prevision", "Report", "Total", "Inactifs", "Facture"};
	
	private static final String[] titlesObjectifParAssocies = {
        "Associe", "Percentage", "Amount"};
	
	private static final String[] titlesObjectifParManagers = {
        "Manager", "Percentage", "Amount per year", "Amount per month"};
	
	
	


	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map model, HSSFWorkbook classeur,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			Map<String, CellStyle> styles = createStyles(classeur);

			// on récupère la liste des budgets du modele
			List<AnnualBudgetData>  budgets = (List<AnnualBudgetData>) model.get("budgetExcelView");
			List<Exercise>  exercises = (List<Exercise>) model.get("exercisesView");
			ExerciseGeneralView exerciseGeneralView = (ExerciseGeneralView) model.get("objectifs");
			
			// on créée une feuille dans le classeur
			HSSFSheet feuille = classeur.createSheet("Budgets");
			
			//turn off gridlines
			feuille.setDisplayGridlines(false);
			feuille.setPrintGridlines(false);
			feuille.setFitToPage(true);
			feuille.setHorizontallyCenter(true);
	        PrintSetup printSetup = feuille.getPrintSetup();
	        printSetup.setLandscape(true);

	        //the following three statements are required only for HSSF
	        feuille.setAutobreaks(true);
	        printSetup.setFitHeight((short) 1);
	        printSetup.setFitWidth((short)1);
	        
	        
	        //title row
	        HSSFRow titleRow = feuille.createRow(0);
	        titleRow.setHeightInPoints(45);
	        HSSFCell titleCell = titleRow.createCell(0);
	        titleCell.setCellValue("Budget Général");
	        titleCell.setCellStyle(styles.get("title"));
	        feuille.addMergedRegion(CellRangeAddress.valueOf("$A$1:$K$1"));
	        
		    //the header row: centered text in 48pt font
	        HSSFRow headerRow = feuille.createRow(1);
	        headerRow.setHeightInPoints(12.75f);
	        for (int i = 0; i < titles.length; i++) {
	        	feuille.setColumnWidth(i, titlesSize[i]*256); //the column is 5 characters wide
	        	HSSFCell cell = headerRow.createCell(i);
	            cell.setCellValue(titles[i]);
	            cell.setCellStyle(styles.get("header"));
	        }
	        
	        //freeze the first row
	        feuille.createFreezePane(0, 1);
	        

	        HSSFRow row;
        	HSSFCell cell;
        	int rownum = 2;        	
        	for(AnnualBudgetData budget : budgets){
            	
            	row = feuille.createRow(rownum);
            	rownum++;
            	
            	for (int columnIndex = 0; columnIndex < titles.length; columnIndex++) {
                	cell = row.createCell(columnIndex);
                	String styleName = null;                
                	 //"cell_b_centered" : "cell_normal_centered";

                	switch(columnIndex){
	                    case 0:	                        
	                            styleName = "cell_b_centered";
	                            cell.setCellValue(budget.getCustomerCode());	                        
	                            break;
	                    case 1:
		                    	styleName = "cell_bb";
	                            cell.setCellValue(budget.getCustomerName());
	                            break;
	                            
	                    case 2:
	                    		styleName = "cell_b_centered";
		                        cell.setCellValue(budget.getMissionType());
		                        break;
		                        
	                    case 3:
		                    	styleName = "cell_b_centered";
	                            cell.setCellValue(budget.getOrigin());
	                            break;
	                            
	                    case 4: 
		                    	styleName = "cell_b_centered_and_colored";
		                    	if( budget.isFiable() == true ){
		                    		cell.setCellValue(budget.getExpectedAmount() );
		                    	}
	                            
	                            break;
	                    
	                    case 5: 
		                    	styleName = "cell_b_centered_and_colored";
	                            cell.setCellValue(budget.getReportedAmount());
	                            break;
	                        
	                    case 6:
		                    	styleName = "cell_b_centered";
		                    	if( budget.isFiable() == false ){
		                    		cell.setCellValue(budget.getExpectedAmount());
		                    	}
	                            break;
	                            
	                    case 7:
	                    	    styleName = "cell_b_centered_and_colored";
		                        cell.setCellValue(budget.getEffectiveAmount());
		                        break;
		                        
	                    case 8:
		                    	styleName = "cell_b_centered";
	                            cell.setCellValue(budget.getAssociate());
	                            break;
	                            
	                    case 9: 
		                    	styleName = "cell_b_centered";
	                            cell.setCellValue(budget.getManager());
	                            break;
	                    
	                    case 10: 
		                    	styleName = "cell_b";
	                            cell.setCellValue(budget.getComments());
	                            break;
	                    
	                   
	                }

                	cell.setCellStyle(styles.get(styleName));
            }
        }
	       
     //Create the second sheet
     createSheetObjectif( classeur,  exerciseGeneralView , styles );
	}
	
	
	private void createSheetObjectif(HSSFWorkbook classeur, ExerciseGeneralView exerciseGeneralView ,Map<String, CellStyle> styles ){
		//Create the sheet Objectifs && Resultats
    	// on créée une feuille dans le classeur
		HSSFSheet feuille = classeur.createSheet("Objectifs & Resultats");
		
		//turn off gridlines
		feuille.setDisplayGridlines(false);
		feuille.setPrintGridlines(false);
		feuille.setFitToPage(true);
		feuille.setHorizontallyCenter(true);
        PrintSetup printSetup = feuille.getPrintSetup();
        printSetup.setLandscape(true);

        //the following three statements are required only for HSSF
        feuille.setAutobreaks(true);
        printSetup.setFitHeight((short)1);
        printSetup.setFitWidth((short)1);
        
        
        //title row
        HSSFRow titleRow = feuille.createRow(0);
        titleRow.setHeightInPoints(45);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Objectifs & Resultats");
        titleCell.setCellStyle(styles.get("title"));
        feuille.addMergedRegion(CellRangeAddress.valueOf("$A$1:$E$1"));
        
        HSSFRow titleBudgetGenarlRow = feuille.createRow(1);
        titleBudgetGenarlRow.setHeightInPoints(12.75f);
        //feuille.setColumnWidth(0, titlesBugetGeneral.length*35*256); //the column is 5 characters wide
    	HSSFCell cell0 = titleBudgetGenarlRow.createCell(0);
    	cell0.setCellValue("Budget general");
    	cell0.setCellStyle(styles.get("header"));
    	feuille.addMergedRegion(CellRangeAddress.valueOf("$A$2:$B$2"));
        
	    //the header row: centered text in 48pt font
        HSSFRow headerRow = feuille.createRow(2);
        headerRow.setHeightInPoints(12.75f);
        for (int i = 0; i < titlesBugetGeneral.length; i++) {
        	feuille.setColumnWidth(i, 20*256); //the column is 5 characters wide
        	HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(titlesBugetGeneral[i]);
            cell.setCellStyle(styles.get("header"));
        }
        
        //freeze the first row
        feuille.createFreezePane(0, 2);
        HSSFCell cell = null;
        HSSFRow row = feuille.createRow(3);
        
       // feuille.setColumnWidth(0, 35*256); //the column is 5 characters wide
    	cell = row.createCell(0);
        cell.setCellValue(exerciseGeneralView.getExpectedBudget());
        cell.setCellStyle(styles.get("cell_b_centered"));
        
        //feuille.setColumnWidth(1, 35*256); //the column is 5 characters wide
    	cell = row.createCell(1);
        cell.setCellValue(exerciseGeneralView.getReportedBudget());
        cell.setCellStyle(styles.get("cell_b_centered"));
        
        //feuille.setColumnWidth(2, 35*256); //the column is 5 characters wide
    	cell = row.createCell(2);
        cell.setCellValue(exerciseGeneralView.getReportedBudget() + exerciseGeneralView.getExpectedBudget());
        cell.setCellStyle(styles.get("cell_b_centered"));
        
        //feuille.setColumnWidth(3, 35*256); //the column is 5 characters wide
    	cell = row.createCell(3);
        cell.setCellValue(exerciseGeneralView.getTotalInactifsBudget());
        cell.setCellStyle(styles.get("cell_b_centered"));
        
        
        //feuille.setColumnWidth(4, 35*256); //the column is 5 characters wide
    	cell = row.createCell(4);
        cell.setCellValue(exerciseGeneralView.getTotalBudget());
        cell.setCellStyle(styles.get("cell_b_centered"));
        
        
        //Objectifs par Associe
        
        HSSFRow titleObjectifParAssocieRow = feuille.createRow(5);
        titleObjectifParAssocieRow.setHeightInPoints(12.75f);
        //feuille.setColumnWidth(0, titlesBugetGeneral.length*35*256); //the column is 5 characters wide
    	cell0 = titleObjectifParAssocieRow.createCell(0);
    	cell0.setCellValue("Objectifs par associé");
    	cell0.setCellStyle(styles.get("header"));
    	feuille.addMergedRegion(CellRangeAddress.valueOf("$A$6:$B$6"));
        
	    //the header row: centered text in 48pt font
        headerRow = feuille.createRow(6);
        headerRow.setHeightInPoints(12.75f);
        for (int i = 0; i < titlesObjectifParAssocies.length; i++) {
        	//feuille.setColumnWidth(i, 35*256); //the column is 5 characters wide
        	cell = headerRow.createCell(i);
            cell.setCellValue(titlesObjectifParAssocies[i]);
            cell.setCellStyle(styles.get("header"));
        }
        
        int rownum = 7;
   
        for(Option option : exerciseGeneralView.getAssocieOptions()){
        	
        	row = feuille.createRow(rownum);
        	
        	for(int i = 0; i < titlesObjectifParAssocies.length; i++){
        		
        		//feuille.setColumnWidth(i, 35*256); //the column is 5 characters wide
            	cell = row.createCell(i);
        	
	        	switch (i){
		        	case 0:
		        		cell.setCellStyle(styles.get("cell_b_centered_and_colored"));		        		
		        		cell.setCellValue(option.getName());
		        	break;
		        	case 1:
		        		cell.setCellStyle(styles.get("cell_b_centered"));
		        		cell.setCellValue(exerciseGeneralView.getObjectifsPerAssocie().get(option.getName()).getPercentage() + "%");
		        	break;
		        	case 2:
		        		cell.setCellStyle(styles.get("cell_b_centered"));
		        		cell.setCellValue(exerciseGeneralView.getObjectifsPerAssocie().get(option.getName()).getAnnualAmount());
		        	break;
	        	}
            
	        	
            
        	}
        	rownum++;
        }
        
        
        
        //Objectifs par Manager        
        HSSFRow titleObjectifManagerRow = feuille.createRow(++rownum);
        titleObjectifManagerRow.setHeightInPoints(12.75f);
        //feuille.setColumnWidth(0, titlesBugetGeneral.length*35*256); //the column is 5 characters wide
    	cell0 = titleObjectifManagerRow.createCell(0);
    	cell0.setCellValue("Objectifs par manager");
    	cell0.setCellStyle(styles.get("header"));
    	//feuille.addMergedRegion(CellRangeAddress.valueOf("$A$6:$B$6"));
        
	    //the header row: centered text in 48pt font
        headerRow = feuille.createRow(++rownum);
        headerRow.setHeightInPoints(12.75f);
        for (int i = 0; i < titlesObjectifParManagers.length; i++) {
        	//feuille.setColumnWidth(i, 35*256); //the column is 5 characters wide
        	cell = headerRow.createCell(i);
            cell.setCellValue(titlesObjectifParManagers[i]);
            cell.setCellStyle(styles.get("header"));
        }
        
        rownum++;
   
        for(Option option : exerciseGeneralView.getManagerOptions()){
        	
        	row = feuille.createRow(rownum);
        	
        	for(int i = 0; i < titlesObjectifParManagers.length; i++){
        		
        		//feuille.setColumnWidth(i, 35*256); //the column is 5 characters wide
            	cell = row.createCell(i);
        	
	        	switch (i){
		        	case 0:
		        		cell.setCellStyle(styles.get("cell_b_centered_and_colored"));
		        		cell.setCellValue(option.getName());
		        	break;
		        	case 1:
		        		cell.setCellStyle(styles.get("cell_b_centered"));
		        		cell.setCellValue(exerciseGeneralView.getObjectifsPerManager().get(option.getName()).getPercentage() + " %");
		        	break;
		        	case 2:
		        		cell.setCellStyle(styles.get("cell_b_centered"));
		        		cell.setCellValue(exerciseGeneralView.getObjectifsPerManager().get(option.getName()).getAnnualAmount());
		        	break;
		        	case 3:
		        		cell.setCellStyle(styles.get("cell_b_centered"));
		        		cell.setCellValue(exerciseGeneralView.getObjectifsPerManager().get(option.getName()).getGrossMonthlyAmount());
		        	break;
	        	}
            
	        	
            
        	}
        	rownum++;
        }
        
        
        /*****************************************/
      //Objectifs par Manager        
        HSSFRow titleResultatMensuelManagerRow = feuille.createRow(++rownum);
        titleResultatMensuelManagerRow.setHeightInPoints(12.75f);
       // feuille.setColumnWidth(0, titlesBugetGeneral.length*35*256); //the column is 5 characters wide
    	cell0 = titleResultatMensuelManagerRow.createCell(0);
    	cell0.setCellValue("Resultats mensuel par manager");
    	cell0.setCellStyle(styles.get("header"));
        
	    //the header row: centered text in 48pt font
        headerRow = feuille.createRow(++rownum);
        headerRow.setHeightInPoints(12.75f);
       // feuille.setColumnWidth(0, 10*256); //the column is 5 characters wide
    	cell = headerRow.createCell(0);        
        cell.setCellStyle(styles.get("header"));
        for (int i = 0; i < titleResultatMensuelManager.length; i++) {
        	//feuille.setColumnWidth(i + 1, 35*256); //the column is 5 characters wide
        	cell = headerRow.createCell(i + 1);
            cell.setCellValue(titleResultatMensuelManager[i]);
            cell.setCellStyle(styles.get("header"));
        }
        
        rownum++;
        
        for(Option option : exerciseGeneralView.getManagerOptions()){
        	
        	row = feuille.createRow(rownum);
        	//feuille.setColumnWidth(0, 10*256); //the column is 5 characters wide
        	cell = row.createCell(0);
        	cell.setCellStyle(styles.get("cell_b_centered_and_colored"));
    		cell.setCellValue(option.getName());
        	
        	for(int i = 0; i < 12; i++){
        		
        		//feuille.setColumnWidth(1+ i, 20*256); //the column is 5 characters wide
            	cell = row.createCell(1 + i);
            	cell.setCellStyle(styles.get("cell_b_centered"));
            	cell.setCellValue(exerciseGeneralView.getResultsPerManager().get(option.getName())[i]);
        	}
        	rownum++;
        }
        
        
        
	}
	
	/**
     * create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(HSSFWorkbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        DataFormat df = wb.createDataFormat();

        CellStyle style;
        
   
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)30);
        titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);
        
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("header_date", style);

        Font font1 = wb.createFont();
        //font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(font1);
        styles.put("cell_b", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font1);
        styles.put("cell_b_centered", style);
        
        
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(font1);
        styles.put("cell_b_centered_and_colored", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_b_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_g", style);

        Font font2 = wb.createFont();
        font2.setColor(IndexedColors.BLUE.getIndex());
        //font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(font2);
        styles.put("cell_bb", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_bg", style);

        Font font3 = wb.createFont();
        font3.setFontHeightInPoints((short)14);
        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
        //font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font3);
        style.setWrapText(true);
        styles.put("cell_h", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        styles.put("cell_normal", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        styles.put("cell_normal_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_normal_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setIndention((short)1);
        style.setWrapText(true);
        styles.put("cell_indented", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_blue", style);

        return styles;
    }

    private static CellStyle createBorderedStyle(HSSFWorkbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

}
