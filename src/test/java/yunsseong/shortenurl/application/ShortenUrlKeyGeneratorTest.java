package yunsseong.shortenurl.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yunsseong.shortenurl.common.exception.CustomException;
import yunsseong.shortenurl.common.exception.error_code.KeyErrorCode;
import yunsseong.shortenurl.domain.limit.Limitable;
import yunsseong.shortenurl.domain.limit.ShortenKeyLengthLimit;

class ShortenUrlKeyGeneratorTest {

    @Test
    @DisplayName("단축된 URL의 키는 8글자로 생성되어야한다.")
    void shortenUrlKeyLengthTest() {
        // given
        RandomNumberGenerator randNumGen = new RandomNumberGenerator();
        Limitable limit = new ShortenKeyLengthLimit();
        ShortenUrlKeyGenerator shortenUrlKeyGenerator = new ShortenUrlKeyGenerator(randNumGen, limit);

        // when
        String shortenUrlKey = shortenUrlKeyGenerator.generateShortenUrlKey();

        // then
        assertThat(shortenUrlKey.length()).isEqualTo(limit.getLimit());
        System.out.println("shortenUrlKey = " + shortenUrlKey);
    }

    @Test
    @DisplayName("단축된 URL의 키는 고유해야한다.")
    void uniqueShortenUrlKeyTest() {
        // given
        RandomNumberGenerator randNumGen = new RandomNumberGenerator();
        Limitable limit = new ShortenKeyLengthLimit(3);
        ShortenUrlKeyGenerator shortenUrlKeyGenerator = new ShortenUrlKeyGenerator(randNumGen, limit);
        List<String> result = new ArrayList<>();

        // when
        for (int i = 0; i < shortenUrlKeyGenerator.getMaxLimit(); i++) {
            result.add(shortenUrlKeyGenerator.getUniqueShortenUrlKey());
        }

        // then
        assertThat(result.size()).isEqualTo(shortenUrlKeyGenerator.getMaxLimit());
        assertThat(result.stream().distinct().count()).isEqualTo(result.size());
        System.out.println("result.size() = " + result.size());
    }

    @Test
    @DisplayName("생성 가능한 키를 모두 소진 한 후 새로운 키를 요청하면 에러를 반환해야한다.")
    void exhaustAllKeyTest() {
        // given
        RandomNumberGenerator randNumGen = new RandomNumberGenerator();
        Limitable limit = new ShortenKeyLengthLimit(3);
        ShortenUrlKeyGenerator shortenUrlKeyGenerator = new ShortenUrlKeyGenerator(randNumGen, limit);
        String expectMessage = KeyErrorCode.EXHAUST_ALL_KEY.getMessage();

        // when
        Exception exception = assertThrows(CustomException.class, () -> {
            for (int i = 0; i < shortenUrlKeyGenerator.getMaxLimit() + 1; i++) {
                shortenUrlKeyGenerator.getUniqueShortenUrlKey();
            }
        });

        // then
        assertEquals(expectMessage, exception.getMessage());
    }
}