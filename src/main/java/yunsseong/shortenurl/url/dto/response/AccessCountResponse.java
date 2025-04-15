package yunsseong.shortenurl.url.dto.response;

public record AccessCountResponse(String shortenUrl, String originalUrl, Long accessCount) {
}
