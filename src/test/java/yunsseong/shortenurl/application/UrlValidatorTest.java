package yunsseong.shortenurl.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yunsseong.shortenurl.common.exception.CustomException;
import yunsseong.shortenurl.common.exception.error_code.UrlErrorCode;
import yunsseong.shortenurl.url.UrlValidator;

class UrlValidatorTest {

    @Test
    @DisplayName("유효한 URL을 입력하면 참을 반환해야한다.")
    void validUrlValidateTest() {
        // given
        UrlValidator urlValidator = new UrlValidator();
        String url = "http://google.com";
        boolean expect = true;

        // when
        boolean validateResult = urlValidator.isValidUrl(url);

        // then
        assertEquals(expect, validateResult);
    }

    @Test
    @DisplayName("유효하지 않은 URL을 입력하면 예외를 발생시켜야한다.")
    void invalidUrlValidateTest() {
        // given
        UrlValidator urlValidator = new UrlValidator();
        String url = "a";

        // when
        Exception exception = assertThrows(CustomException.class, () -> urlValidator.isValidUrl(url));

        // then
        assertEquals(UrlErrorCode.MALFORMED_URL.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("유효한 형식의 URL이지만 실제로 연결할 수 없는 경우 예외를 발생시켜야한다.")
    void unreachableUrlValidateTest() {
        // given
        UrlValidator urlValidator = new UrlValidator();
        String url = "http://thisistesturladdress.com/";

        // when
        Exception exception = assertThrows(CustomException.class, () -> urlValidator.isValidUrl(url));

        // then
        assertEquals(UrlErrorCode.UNREACHABLE_URL.getMessage(), exception.getMessage());
    }
}