package ir.sharif.aminra.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientDisconnectException extends Exception {
    public ClientDisconnectException(Throwable cause) {
        super(cause);
    }
}
