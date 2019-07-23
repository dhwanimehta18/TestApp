package com.example.testapp.testapp.DatabaseExample.listDemo;


public class Constants {


    public static final boolean IS_DEBUGGABLE = true;


    public interface SharedPrefs {
        String USER = "user";
    }

    public interface BundleExtras {
        String USER_ID = "user_id";
    }

    public interface DateTimeFormats {
        String BIRTH_DATE_FORMAT = "dd MMM yyyy";
    }

    public interface OtherConsts {
        int DEFAULT_PAGE_LIMIT = 10;
    }

    public interface StringFormats {
        String PRICE_FORMAT = "%s%s";
    }
}