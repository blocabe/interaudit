package com.interaudit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class HtmlUtil {
    private static final Logger logger = Logger.getLogger(HtmlUtil.class);

    public static String convertToHtml(final String plainTextMessage) {
        return "<html><body>" + convertToHtmlFragment(plainTextMessage)
                + "</body></html>";
    }

    public static String convertToHtmlFragment(final String plainTextMessage) {
        String result = escapeBrackets(plainTextMessage);
        result = convertEolToHtmlEol(result);
        result = searchAndReplaceUrl(result);
        return result;
    }

    public static String escapeBrackets(final String plainTextMessage) {
        return plainTextMessage.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    public static String convertEolToHtmlEol(final String plainTextMessage) {
        final String result1 = plainTextMessage.replaceAll("\r\n", "<br/>");
        logger.debug("'" + plainTextMessage + "' replaced with '" + result1
                + "'");
        final String result2 = result1.replaceAll("\n\r", "<br/>");
        logger.debug("'" + result1 + "' replaced with '" + result2 + "'");
        final String result3 = result2.replaceAll("\n", "<br />");
        logger.debug("'" + result2 + "' replaced with '" + result3 + "'");

        logger.info("Returning '" + result3 + "' for '" + plainTextMessage
                + "'");
        return result3;

    }

    public static String searchAndReplaceUrl(final String inputString) {

        final CharSequence inputStr = inputString;
        final String patternStr = "(?:ht|f)tps?:[a-zA-Z0-9_/:?=&\\-.%;!$£^\"{}|@#¬]+";

        // Compile regular expression
        final Pattern pattern = Pattern.compile(patternStr);
        final Matcher matcher = pattern.matcher(inputStr);

        // Replace all occurrences of pattern in input
        final StringBuffer buf = new StringBuffer();

        while ((matcher.find())) {
            // Get the match result
            String replaceStr = matcher.group();

            replaceStr = " <a href=\"" + replaceStr + "\" target=\"_blank\">"
                    + replaceStr + "</a> ";

            // Insert replacement
            matcher.appendReplacement(buf, replaceStr);
        }

        matcher.appendTail(buf);

        // Get result
        return buf.toString();
    }

}
