package yunsseong.shortenurl.common.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UrlErrorCode implements ErrorCode {
    NOT_EXIST_URL(HttpStatus.BAD_REQUEST, "존재하지 않는 URL 입니다."),
    MALFORMED_URL(HttpStatus.BAD_REQUEST, "잘못된 구조의 URL 입니다."),
    UNREACHABLE_URL(HttpStatus.BAD_REQUEST, "연결할 수 없는 URL 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
