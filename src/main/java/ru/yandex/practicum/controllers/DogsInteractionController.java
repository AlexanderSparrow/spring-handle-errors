package ru.yandex.practicum.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.HappinessOverflowException;
import ru.yandex.practicum.exception.IncorrectCountException;

import java.util.Map;

@RestController
@RequestMapping("/dogs")
public class DogsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        // проверка на избалованность
        if (happiness >= 10) {
            throw new HappinessOverflowException(happiness);
        }
            happiness += 2;
            return Map.of("talk", "Гав!");
        }

    @GetMapping("/pet")
    public Map<String, String> pet(@RequestParam(required = false) final Integer count) {
        if (count == null) {
            throw new IncorrectCountException("Параметр count равен null.");
        }
        if (count <= 0) {
            throw new IncorrectCountException("Параметр count имеет отрицательное значение.");
        }// проверка на избалованность
        if (happiness + count > 10) {
            throw new HappinessOverflowException(happiness);
        }
        happiness += count;
        return Map.of("action", "Вильнул хвостом. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    // Обработчик для HappinessOverflowException
    @ExceptionHandler
    public Map<String, String> handleHappinessOverflow(final HappinessOverflowException e) {
        return Map.of(
                "happinessLevel", String.valueOf(e.getHappinessLevel()),
                "error", "Осторожно, вы так избалуете пёсика!"
        );
    }

    @ExceptionHandler
    public Map<String, String> handle(final IncorrectCountException e) {
        return Map.of(
                "error", "Ошибка с параметром count.",
                "errorMessage", e.getMessage()
        );
    }
}
