package yunsseong.shortenurl.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yunsseong.shortenurl.common.exception.CustomException;
import yunsseong.shortenurl.common.exception.error_code.UrlErrorCode;
import yunsseong.shortenurl.common.util.RandomNumber;
import yunsseong.shortenurl.key.domain.Key;
import yunsseong.shortenurl.limit.Limit;
import yunsseong.shortenurl.url.domain.UrlMapper;
import yunsseong.shortenurl.url.infrastructure.KeyRepository;

class UrlMapperTest {
    private final Limit limit = new Limit();

    @Test
    @DisplayName("새 원본 URL과 단축 URL 키 맵핑 테스트")
    void makeNewMappingTest() {
        // given
        RandomNumber randNumGen = new RandomNumber();
        Key keyGen = new Key(randNumGen, limit);
        KeyRepository keyRepository = new KeyRepository();
        UrlMapper urlMapper = new UrlMapper(keyGen, keyRepository);
        String originalUrl = "http://a.com";

        // when
        String key = urlMapper.register(originalUrl);
        String foundUrl = urlMapper.access(key);

        // then
        Assertions.assertThat(foundUrl).isEqualTo(originalUrl);
    }

    @Test
    @DisplayName("없는 단축 URL 키로 원본 URL 조회 시 예외 발생 테스트")
    void getOriginalUrlByInvalidKey() {
        // given
        RandomNumber randNumGen = new RandomNumber();
        Key keyGen = new Key(randNumGen, limit);
        KeyRepository keyRepository = new KeyRepository();
        UrlMapper urlMapper = new UrlMapper(keyGen, keyRepository);
        String invalidKey = "ABC12345";
        String exceptionMessage = UrlErrorCode.NOT_EXIST_URL.getMessage();

        // when
        Exception exception = assertThrows(CustomException.class,
                () -> urlMapper.access(invalidKey));

        // then
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("URL 조회 횟수 가져오기 기능 테스트")
    void UrlAccessCountTest() {
        // given
        RandomNumber randNumGen = new RandomNumber();
        Key keyGen = new Key(randNumGen, limit);
        KeyRepository keyRepository = new KeyRepository();
        UrlMapper urlMapper = new UrlMapper(keyGen, keyRepository);
        String originalUrl = "http://a.com";
        Long expectAccessCount = 2L;

        // when
        String key = urlMapper.register(originalUrl);
        urlMapper.access(key);
        urlMapper.access(key);
        Long accessCount = urlMapper.getAccessCount(key);

        // then
        assertEquals(expectAccessCount, accessCount);
    }
}