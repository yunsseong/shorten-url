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
import yunsseong.shortenurl.common.util.RandomNumber;
import yunsseong.shortenurl.key.domain.Key;
import yunsseong.shortenurl.limit.Limitable;
import yunsseong.shortenurl.limit.Limit;

class KeyTest {

    @Test
    @DisplayName("단축된 URL의 키는 8글자로 생성되어야한다.")
    void shortenUrlKeyLengthTest() {
        // given
        RandomNumber randNumGen = new RandomNumber();
        Limitable limit = new Limit();
        Key key = new Key(randNumGen, limit);

        // when
        String shortenUrlKey = key.generateShortenUrlKey();

        // then
        assertThat(shortenUrlKey.length()).isEqualTo(limit.getLimit());
        System.out.println("shortenUrlKey = " + shortenUrlKey);
    }

    @Test
    @DisplayName("단축된 URL의 키는 고유해야한다.")
    void uniqueShortenUrlKeyTest() {
        // given
        RandomNumber randNumGen = new RandomNumber();
        Limitable limit = new Limit(3);
        Key key = new Key(randNumGen, limit);
        List<String> result = new ArrayList<>();

        // when
        for (int i = 0; i < key.getMaxLimit(); i++) {
            result.add(key.getUniqueShortenUrlKey());
        }

        // then
        assertThat(result.size()).isEqualTo(key.getMaxLimit());
        assertThat(result.stream().distinct().count()).isEqualTo(result.size());
        System.out.println("result.size() = " + result.size());
    }

    @Test
    @DisplayName("생성 가능한 키를 모두 소진 한 후 새로운 키를 요청하면 에러를 반환해야한다.")
    void exhaustAllKeyTest() {
        // given
        RandomNumber randNumGen = new RandomNumber();
        Limitable limit = new Limit(3);
        Key key = new Key(randNumGen, limit);
        String expectMessage = KeyErrorCode.EXHAUST_ALL_KEY.getMessage();

        // when
        Exception exception = assertThrows(CustomException.class, () -> {
            for (int i = 0; i < key.getMaxLimit() + 1; i++) {
                key.getUniqueShortenUrlKey();
            }
        });

        // then
        assertEquals(expectMessage, exception.getMessage());
    }
}