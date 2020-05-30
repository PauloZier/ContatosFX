package org.contatosapp.util;

import javax.swing.text.MaskFormatter;

public class TextUtils {
    
    private TextUtils() {
    
    }
    
    public static String maskText(String text, String mask) {

        try {
            
            MaskFormatter formatter = new MaskFormatter(mask);
            
            formatter.setValueContainsLiteralCharacters(false);
            
            return formatter.valueToString(text);
            
        } catch (Exception ex) {
            
            return text;
            
        }
        
    }
    
    public static String maskFone(String text) {
        
        text = getOnlyNumbersAndLetters(text);
        
        switch (text.length()) {
        
            case 10:
                return maskText(text, "(##) ####-####");
            
            case 11:
                return maskText(text, "(##) #####-####");
                
            default:
                return text;
        }
            
    }
    
    public static String getOnlyNumbers(String text) {
    
        return text.replaceAll("[^0-9]", "");
        
    }
    
    public static String getOnlyNumbersAndLetters(String text) {
    
        return text.replaceAll("[^0-9a-zA-Z]", "");
        
    }
    
}
