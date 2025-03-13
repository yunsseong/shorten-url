package yunsseong.shortenurl.domain.url;

public class OriginalUrl {
    private String url;
    private Long accessCount = 0L;

    public OriginalUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        countUp();
        return url;
    }

    public Long getAccessCount() {
        return accessCount;
    }

    private void countUp() {
        accessCount++;
    }
}
