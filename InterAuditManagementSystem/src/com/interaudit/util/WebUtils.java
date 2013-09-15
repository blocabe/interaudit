package com.interaudit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.http.HttpServletRequest;

import com.interaudit.web.controllers.LoginController;


/**
 * 
 * @author Mitrea Alexandru
 * 
 */
public final class WebUtils {

	private WebUtils() {
	}

	public static String getApplicationVersion(final HttpServletRequest request) {
		String softwareVersion = "";
		try {
			// Keep the version number in session to be displayed in the footer
			Class<?> clazz = LoginController.class;
			String sep = System.getProperty("file.separator");

			String className = clazz.getSimpleName();
			String classFileName = className + ".class";
			String pathToThisClass = clazz.getResource(classFileName).toString();

			int mark = pathToThisClass.indexOf("!");
			String pathToManifest = "";

			if (mark != -1) {
				pathToManifest = pathToThisClass.toString().substring(0, mark + 1);

				// Remove zip beginning (if exists)
				if (pathToManifest.startsWith("zip:")) {
					pathToManifest = pathToManifest.substring(4);
				}
				pathToManifest = pathToManifest.substring(0, pathToManifest.indexOf("WEB-INF"));
				pathToManifest = pathToManifest + sep + "META-INF" + sep + "MANIFEST.MF";

			} else {

				if (pathToThisClass.startsWith("file:/")) {
					pathToManifest = pathToThisClass.substring(5);
					pathToManifest = pathToManifest.substring(0, pathToManifest.indexOf("WEB-INF"));
					pathToManifest = pathToManifest + sep + "META-INF" + sep + "MANIFEST.MF";
				}
			}

			File file = new File(pathToManifest);
			InputStream input = new FileInputStream(file);
			Manifest manifest = new Manifest(input);
			Attributes attr = manifest.getMainAttributes();
			softwareVersion = attr.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
			input.close();
		} catch (final Exception ex) {
			System.out.println("Could not read manisfest file: " + ex.getMessage());
		}

		return softwareVersion;
	}
	
	public static void cleanSession(final HttpServletRequest request) {
        
        //Cleaning the budget controller objects
        //request.getSession(false).removeAttribute("viewbudget");                
        //request.getSession(false).removeAttribute("viewExercise");                        
        request.getSession(false).removeAttribute("successMessage");
        request.getSession(false).removeAttribute("actijava-scripts");                
        request.getSession(false).removeAttribute("profitability_manager");
        request.getSession(false).removeAttribute("profitability_associe");
        request.getSession(false).removeAttribute("profitability_years");
        
        //Cleaning the create bank controller objects
        request.getSession(false).removeAttribute("accountNumberErrorMessage");                
        request.getSession(false).removeAttribute("codeErrorMessage");
        request.getSession(false).removeAttribute("emailErrorMessage");
        
        //cleaning the create contract controller
        request.getSession(false).removeAttribute("allContractOptions");
        
        //Cleaning the create contact controller objects
        request.getSession(false).removeAttribute("companyNameErrorMessage");                
        request.getSession(false).removeAttribute("allContactOptions");
        request.getSession(false).removeAttribute("emailErrorMessage");
        
        //Cleaning the create customer controller objects
        request.getSession(false).removeAttribute("allCustomerOptions");
        request.getSession(false).removeAttribute("invalidContracttypeErrorMessage");
        request.getSession(false).removeAttribute("invalidCustomerManagerErrorMessage");
        request.getSession(false).removeAttribute("invalidCustomerAssociateErrorMessage");                
        request.getSession(false).removeAttribute("invalidCustomerOriginErrorMessage");
        request.getSession(false).removeAttribute("invalidCustomerCountryErrorMessage");
        request.getSession(false).removeAttribute("invalidAmountFormatErrorMessage");
        
        //Cleaning the create declaration controller objects
        request.getSession(false).removeAttribute("alreadyRegisteredErrorMessage");
        request.getSession(false).removeAttribute("companyNameErrorMessage");
        
        //Cleaning the create employee controller objects
        request.getSession(false).removeAttribute("allEmployeeOptions");
        request.getSession(false).removeAttribute("loginErrorMessage");
        
        //Cleaning the create exercise controller objects
        request.getSession(false).removeAttribute("exerciseErrorMessage");
        
        //Cleaning the create invoice controller objects
        request.getSession(false).removeAttribute("contactsOptions");                
        request.getSession(false).removeAttribute("invalidAmountFormatErrorMessage");
        request.getSession(false).removeAttribute("invalidBankErrorMessage");
        
        //Cleaning the create message controller objects
        request.getSession(false).removeAttribute("invalidRecipientErrorMessage");
        
        //Cleaning the create payment controller objects
        request.getSession(false).removeAttribute("bankCodeErrorMessage");                
        request.getSession(false).removeAttribute("factureReferenceErrorMessage");
        request.getSession(false).removeAttribute("invalidAmountFormatErrorMessage");
        request.getSession(false).removeAttribute("actijava-scripts");
        
        //Cleaning the create invoice controller objects
        request.getSession(false).removeAttribute("viewInvoiceDetails");
        request.getSession(false).removeAttribute("viewCustomerName");
        request.getSession(false).removeAttribute("invoiceErrorMessage");
        request.getSession(false).removeAttribute("listTimesheetsToValidate");
        request.getSession(false).removeAttribute("hasError");
        
        //Cleaning the mission controller objects
        request.getSession(false).removeAttribute("viewmissions");
        request.getSession(false).removeAttribute("viewPlanning");
        request.getSession(false).removeAttribute("viewMissionDetails");
        request.getSession(false).removeAttribute("viewActivityPerWeekPerEmployee");
        request.getSession(false).removeAttribute("viewAgendaPerEmployee");
        request.getSession(false).removeAttribute("viewTimesheetReports");
        request.getSession(false).removeAttribute("messages");
        
        //Cleaning the timesheet controller objects
        request.getSession(false).removeAttribute("viewTimesheetPerEmployee");
        request.getSession(false).removeAttribute("viewTimesheetList");
        
}



}
