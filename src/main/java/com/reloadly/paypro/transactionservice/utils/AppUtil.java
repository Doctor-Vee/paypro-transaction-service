package com.reloadly.paypro.transactionservice.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Timestamp;

public class AppUtil {
    public static String generate20DigitTransactionReference() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String number = RandomStringUtils.randomNumeric(6);
        String dateTimeString = DateUtil.formatDate(now, "yyMMddHHmmss");
        return "PP" + dateTimeString + number;
    }
}
