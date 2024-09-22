package ru.yandex.practicum.exception;

public class HappinessOverflowException extends RuntimeException {
    private final int happinessLevel;

    public HappinessOverflowException(int happinessLevel) {
        this.happinessLevel = happinessLevel;
    }

    public int getHappinessLevel() {
        return happinessLevel;
    }
}