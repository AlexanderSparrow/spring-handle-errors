package ru.yandex.practicum.controllers;

// Класс для представления ошибки
class ErrorResponse {
    private final String error;
    private final String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }

    // Геттеры для полей
    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
