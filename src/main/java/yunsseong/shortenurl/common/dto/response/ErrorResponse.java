package yunsseong.shortenurl.common.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(HttpStatus status, String message, LocalDateTime timestamp) {
        this.status = status.value();
        this.message = message;
        this.timestamp = timestamp;
    }
}
