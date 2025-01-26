package passwordmanager;

import java.util.Arrays;

import org.passay.*;

public class Passwords {
	
	public static boolean isStrongPassword(String password) {
	    if (password == null || password.length() < 8) return false;
	    
	    // parameter is list of rules to validate
        PasswordValidator validator = new PasswordValidator(
                Arrays.asList(
                		//min and max length
                        new LengthRule(8, 128),
                        // character rules to enforce (character data and number)
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1)
                )
        );
        RuleResult result = validator.validate(new PasswordData(new String(password)));
        return result.isValid();
	}
	
	public static String generateSecurePassword() {
        
        // create character rule for lower case
        CharacterRule LCR = new CharacterRule(EnglishCharacterData.LowerCase, 6);
        // create character rule for upper case
        CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase, 4); 
        // create character rule for digit
        CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit, 2);
        // create character rule for lower case
        CharacterRule SR = new CharacterRule(EnglishCharacterData.Special, 2);       
        
        PasswordGenerator passGen = new PasswordGenerator();
        // generate password with provided rules
        String password = passGen.generatePassword(14, SR, LCR, UCR, DR);
        
        return password;
    }  
}
