package yunsseong.shortenurl.url.domain;

public class OriginalUrl {
    private final String url;
    private Long accessCount = 0L;

    public OriginalUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        increaseAccessCount();
        return url;
    }

    public String viewUrl() {
        return url;
    }

    public Long getAccessCount() {
        return accessCount;
    }

    private void increaseAccessCount() {
        accessCount++;
    }
}
