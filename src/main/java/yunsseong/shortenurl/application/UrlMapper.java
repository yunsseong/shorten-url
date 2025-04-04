package yunsseong.shortenurl.application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yunsseong.shortenurl.common.exception.CustomException;
import yunsseong.shortenurl.common.exception.error_code.UrlErrorCode;
import yunsseong.shortenurl.domain.url.OriginalUrl;

@Service
@RequiredArgsConstructor
public class UrlMapper {

    private final ShortenUrlKeyGenerator keyGen;
    private final Map<String, OriginalUrl> urlMap = new ConcurrentHashMap<>();

    public String makeNewMapping(String url) {
        String key = keyGen.getUniqueShortenUrlKey();
        urlMap.put(key, new OriginalUrl(url));
        return key;
    }

    private OriginalUrl getOriginalUrl(String key) {
        OriginalUrl foundOriginalUrl = urlMap.get(key);
        if (foundOriginalUrl == null) {
            throw new CustomException(UrlErrorCode.NOT_EXIST_URL);
        }
        return foundOriginalUrl;
    }

    public String requestOriginalUrl(String key) {
        return getOriginalUrl(key).getUrlWithCountUp();
    }

    public String findOriginalUrlByKey(String key) {return getOriginalUrl(key).getUrl();}

    public Long getAccessCount(String key) {
        return getOriginalUrl(key).getAccessCount();
    }
}
