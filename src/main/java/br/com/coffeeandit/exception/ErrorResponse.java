package br.com.coffeeandit.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorResponse {

    private String message;
    private int code;
}
