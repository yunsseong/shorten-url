package yunsseong.shortenurl.url.infrastructure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import yunsseong.shortenurl.common.exception.CustomException;
import yunsseong.shortenurl.common.exception.error_code.UrlErrorCode;
import yunsseong.shortenurl.url.domain.OriginalUrl;

@Repository
public class KeyRepository {
    final Map<String, OriginalUrl> map = new ConcurrentHashMap<>();

    public void put(String key, OriginalUrl originalUrl) {
        map.put(key, originalUrl);
    }

    public OriginalUrl findOriginalUrlByKey(String key) {
        if(map.get(key) == null) {
            throw new CustomException(UrlErrorCode.NOT_EXIST_URL);
        }
        return map.get(key);
    }
}
