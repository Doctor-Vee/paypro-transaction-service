package com.reloadly.paypro.transactionservice.constant;

public class EventTopicConstant {

    private static final String BASE_TOPIC = "fvsqlil0-";

    public static final String TRANSACTION_COMPLETED = BASE_TOPIC + "transaction-completed";

    public static final String USER_CREATION = BASE_TOPIC + "user-creation";

    public static final String USER_UPDATE = BASE_TOPIC + "user-update";

}
