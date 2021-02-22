package com.reloadly.paypro.transactionservice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String formatDate(Date date, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }
}