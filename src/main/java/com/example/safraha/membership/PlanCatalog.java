package com.example.safraha.membership;

public class PlanCatalog {

    public static final String MONTHLY_10 = "MONTHLY_10";
    public static final String MONTHLY_15 = "MONTHLY_15";
    public static final String QUARTERLY = "QUARTERLY";

    public static int nightsFor(String plan) {
        return switch (plan) {
            case MONTHLY_10 -> 10;
            case MONTHLY_15 -> 15;
            case QUARTERLY -> 90;   // example
            default -> throw new IllegalArgumentException("Invalid plan");
        };
    }

    public static int durationDays(String plan) {
        return switch (plan) {
            case MONTHLY_10, MONTHLY_15 -> 30;
            case QUARTERLY -> 120;
            default -> throw new IllegalArgumentException("Invalid plan");
        };
    }
}

