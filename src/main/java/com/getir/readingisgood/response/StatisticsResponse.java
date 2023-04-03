package com.getir.readingisgood.response;

public class StatisticsResponse {
    private final String month;

    private final Long totalOrderCount;

    private final Long totalBookCount;

    private final Double totalPurchasedAmount;

    public StatisticsResponse(String month, Long totalOrderCount, Long totalBookCount, Double totalPurchasedAmount) {
        this.month = month;
        this.totalOrderCount = totalOrderCount;
        this.totalBookCount = totalBookCount;
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

    public String getMonth() {
        return month;
    }

    public Long getTotalOrderCount() {
        return totalOrderCount;
    }

    public Long getTotalBookCount() {
        return totalBookCount;
    }

    public Double getTotalPurchasedAmount() {
        return totalPurchasedAmount;
    }
}
