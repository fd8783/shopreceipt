package Helper;

import java.util.ResourceBundle;

public class StringHelper {
    private final static ResourceBundle strBundle = ResourceBundle.getBundle("strings");

    public static String getString(String key){
        return strBundle.getString(key);
    }
}