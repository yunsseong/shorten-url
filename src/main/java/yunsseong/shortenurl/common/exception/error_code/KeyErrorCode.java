package yunsseong.shortenurl.common.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum KeyErrorCode implements ErrorCode {
    EXHAUST_ALL_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "생성할 수 있는 키를 다 소진하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
