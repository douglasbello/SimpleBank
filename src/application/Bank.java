package application;

import java.text.ParseException;
import java.util.Locale;

import db.DB;
import model.utils.Menu;

public class Bank {
    public static void main (String[] args) throws ParseException {
        Locale.setDefault(Locale.US);
        Menu.menu();
    }
}
