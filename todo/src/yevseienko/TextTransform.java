package yevseienko;

public class TextTransform {
    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static String insertString(String originalString, String stringToBeInserted, int index) {
        String newString = originalString.substring(0, index + 1)
                + stringToBeInserted + originalString.substring(index + 1);
        return newString;
    }
}
