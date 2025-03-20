package yunsseong.shortenurl.common.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UrlErrorCode implements ErrorCode {
    NOT_EXIST_URL(HttpStatus.BAD_REQUEST, "Url not exist");

    private final HttpStatus httpStatus;
    private final String message;
}
