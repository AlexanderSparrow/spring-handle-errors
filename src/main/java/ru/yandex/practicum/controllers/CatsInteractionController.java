package ru.yandex.practicum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.HappinessOverflowException;
import ru.yandex.practicum.exception.IncorrectCountException;

import java.util.Map;

@RestController
@RequestMapping("/cats")
public class CatsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        // проверка на избалованность
        if (happiness >= 10) {
            throw new HappinessOverflowException(happiness);
        }
        happiness++;
        return Map.of("talk", "Мяу");
    }

    @GetMapping("/pet")
    public Map<String, String> pet(@RequestParam(required = false) final Integer count) {
        if (count == null) {
            throw new IncorrectCountException("Параметр count равен null.");
        }
        if (count <= 0) {
            throw new IncorrectCountException("Параметр count имеет отрицательное значение.");
        }
        // проверка на избалованность
        if (happiness + count > 10) {
            throw new HappinessOverflowException(happiness);
        }
        happiness += count;
        return Map.of("talk", "Муррр. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    @ExceptionHandler
    public Map<String, String> handle(final IncorrectCountException e) {
        return Map.of("error", "Ошибка с параметром count.", "errorMessage", e.getMessage());
    }

    // Обработчик для IncorrectCountException с кодом ответа 400
    @ExceptionHandler(IncorrectCountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Код ответа 400
    public ErrorResponse handleIncorrectCount(final IncorrectCountException e) {
        return new ErrorResponse(
                "Ошибка с параметром count.", e.getMessage()
        );
    }
}