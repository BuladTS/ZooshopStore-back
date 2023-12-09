package ru.sfu.zooshopback.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    IN_STOCK(Code.IN_STOCK),
    ON_THE_WAY(Code.ON_THE_WAY),

    READY_TO_ISSUE(Code.READY_TO_ISSUE);


    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public static class Code {
        public static final String IN_STOCK = "IN STOCK";
        public static final String ON_THE_WAY = "ON THE WAY";
        public static final String READY_TO_ISSUE = "READY TO ISSUE";
    }
}
