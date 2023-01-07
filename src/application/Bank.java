package application;

import java.text.ParseException;
import java.util.Locale;
import model.utils.Util;

public class Bank {
    public static void main (String[] args) throws ParseException {
        Locale.setDefault(Locale.US);

        Util.menu();
    }
}
