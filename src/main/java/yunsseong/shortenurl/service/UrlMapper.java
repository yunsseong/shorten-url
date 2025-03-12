package yunsseong.shortenurl.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yunsseong.shortenurl.domain.OriginalUrl;

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

    public String getOriginalUrlByKey(String key) {
        OriginalUrl foundOriginalUrl = urlMap.get(key);
        if (foundOriginalUrl == null) {
            throw new IllegalArgumentException("맵핑된 URL을 찾을 수 없습니다.");
        }
        return foundOriginalUrl.getUrl();
    }
}
