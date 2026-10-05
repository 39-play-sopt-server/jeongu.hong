package org.sopt.model;

public enum Category {
    PLAY,
    SOPT,
    DANCE;

    public static boolean exists(int number) {
        return number >= 1 && number <= values().length;
    }

    public static Category from(int number) {
        return values()[number - 1];
    }
}
