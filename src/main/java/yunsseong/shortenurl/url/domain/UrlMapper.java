package yunsseong.shortenurl.url.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yunsseong.shortenurl.key.domain.Key;
import yunsseong.shortenurl.url.dto.request.UrlComponent;
import yunsseong.shortenurl.url.dto.response.AccessCountResponse;
import yunsseong.shortenurl.url.infrastructure.KeyRepository;

@Service
@RequiredArgsConstructor
public class UrlMapper {

    private final Key key;
    private final KeyRepository keyRepository;

    public String register(String url) {
        String generatedKey = this.key.getKey();
        keyRepository.put(generatedKey, new OriginalUrl(url));
        return generatedKey;
    }

    private OriginalUrl find(String key) {
        return keyRepository.findOriginalUrlByKey(key);
    }

    public String access(String key) {
        return find(key).getUrl();
    }

    public Long getAccessCount(String key) {
        return find(key).getAccessCount();
    }

    public AccessCountResponse getUrlAccessCountInfo(String key, UrlComponent urlComponent) {
        OriginalUrl originalUrl = find(key);
        StringBuilder sb = new StringBuilder();
        String shortenUrl = sb.append(urlComponent.scheme())
                .append("://")
                .append(urlComponent.host())
                .append("/")
                .append(key).toString();

        return new AccessCountResponse(
                shortenUrl,
                originalUrl.viewUrl(),
                originalUrl.getAccessCount()
        );
    }

    public String findOriginalUrlByKey(String key) {return find(key).getUrl();}
}
