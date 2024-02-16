package com.github.stasmalykhin.coffeemachine.util;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Шаблон ответа пользователю.
 *
 * @author Stanislav Malykhin
 */
@Getter
@Setter
public class CustomResponse {
    private String message;
    private Instant currentTime;

    public CustomResponse(String message) {
        this.message = message;
        this.currentTime = Instant.now();
    }
}
