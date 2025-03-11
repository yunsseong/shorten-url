package yunsseong.shortenurl.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yunsseong.shortenurl.limit.Limitable;
import yunsseong.shortenurl.limit.ShortenKeyLengthLimit;

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
    void UniqueShortenUrlKeyTest() {
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
}