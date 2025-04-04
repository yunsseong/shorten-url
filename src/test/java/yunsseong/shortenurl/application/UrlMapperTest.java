package yunsseong.shortenurl.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yunsseong.shortenurl.common.exception.CustomException;
import yunsseong.shortenurl.common.exception.error_code.UrlErrorCode;
import yunsseong.shortenurl.domain.limit.ShortenKeyLengthLimit;

class UrlMapperTest {
    private final ShortenKeyLengthLimit limit = new ShortenKeyLengthLimit();

    @Test
    @DisplayName("새 원본 URL과 단축 URL 키 맵핑 테스트")
    void makeNewMappingTest() {
        // given
        RandomNumberGenerator randNumGen = new RandomNumberGenerator();
        ShortenUrlKeyGenerator keyGen = new ShortenUrlKeyGenerator(randNumGen, limit);
        UrlMapper urlMapper = new UrlMapper(keyGen);
        String originalUrl = "http://a.com";

        // when
        String mappedKey = urlMapper.makeNewMapping(originalUrl);
        String foundUrl = urlMapper.requestOriginalUrl(mappedKey);

        // then
        Assertions.assertThat(foundUrl).isEqualTo(originalUrl);
    }

    @Test
    @DisplayName("없는 단축 URL 키로 원본 URL 조회 시 예외 발생 테스트")
    void getOriginalUrlByInvalidKey() {
        // given
        RandomNumberGenerator randNumGen = new RandomNumberGenerator();
        ShortenUrlKeyGenerator keyGen = new ShortenUrlKeyGenerator(randNumGen, limit);
        UrlMapper urlMapper = new UrlMapper(keyGen);
        String invalidKey = "ABC12345";
        String exceptionMessage = UrlErrorCode.NOT_EXIST_URL.getMessage();

        // when
        Exception exception = assertThrows(CustomException.class,
                () -> urlMapper.requestOriginalUrl(invalidKey));

        // then
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("URL 조회 횟수 가져오기 기능 테스트")
    void UrlAccessCountTest() {
        // given
        RandomNumberGenerator randNumGen = new RandomNumberGenerator();
        ShortenUrlKeyGenerator keyGen = new ShortenUrlKeyGenerator(randNumGen, limit);
        UrlMapper urlMapper = new UrlMapper(keyGen);
        String originalUrl = "http://a.com";
        Long expectAccessCount = 2L;

        // when
        String mappedKey = urlMapper.makeNewMapping(originalUrl);
        urlMapper.requestOriginalUrl(mappedKey);
        urlMapper.requestOriginalUrl(mappedKey);
        Long accessCount = urlMapper.getAccessCount(mappedKey);

        // then
        assertEquals(expectAccessCount, accessCount);
    }
}