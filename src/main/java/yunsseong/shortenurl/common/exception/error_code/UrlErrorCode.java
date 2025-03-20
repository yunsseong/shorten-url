package yunsseong.shortenurl.common.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UrlErrorCode implements ErrorCode {
    NOT_EXIST_URL(HttpStatus.BAD_REQUEST, "존재하지 않는 URL 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
