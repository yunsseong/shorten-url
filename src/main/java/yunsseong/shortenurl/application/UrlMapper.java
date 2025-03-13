package yunsseong.shortenurl.application;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yunsseong.shortenurl.domain.url.OriginalUrl;

@Service
@RequiredArgsConstructor
public class UrlMapper {

    private final ShortenUrlKeyGenerator keyGen;
    private Map<String, OriginalUrl> urlMap = new HashMap<>();

    public String makeNewMapping(String url) {
        String key = keyGen.getUniqueShortenUrlKey();
        urlMap.put(key, new OriginalUrl(url));
        return key;
    }

    private OriginalUrl getOriginalUrl(String key) {
        OriginalUrl foundOriginalUrl = urlMap.get(key);
        if (foundOriginalUrl == null) {
            throw new IllegalArgumentException("맵핑된 URL을 찾을 수 없습니다.");
        }
        return foundOriginalUrl;
    }

    public String getOriginalUrlByKey(String key) {
        return getOriginalUrl(key).getUrl();
    }

    public Long getAccessCount(String key) {
        return getOriginalUrl(key).getAccessCount();
    }
}
