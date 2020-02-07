package pe.edu.utp.util;

public class TypesUtil {
    public static String breakLine(String phrase, int chars){
        return phrase.replaceAll("(.{"+ chars +"})", "$1\n");
    }
}
