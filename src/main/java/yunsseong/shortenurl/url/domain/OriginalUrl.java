package yunsseong.shortenurl.url.domain;

public class OriginalUrl {
    private String url;
    private Long accessCount = 0L;

    public OriginalUrl(String url) {
        this.url = url;
    }

    public String getUrlWithCountUp() {
        countUp();
        return url;
    }

    public String getUrl() {
        return url;
    }

    public Long getAccessCount() {
        return accessCount;
    }

    private void countUp() {
        accessCount++;
    }
}
