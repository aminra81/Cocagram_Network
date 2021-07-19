package ir.sharif.aminra.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DatabaseDisconnectException extends Exception{
    public DatabaseDisconnectException(Throwable cause) {
        super(cause);
    }
}