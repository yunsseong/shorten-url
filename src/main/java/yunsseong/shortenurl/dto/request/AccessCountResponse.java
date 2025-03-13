package yunsseong.shortenurl.dto.request;

public record AccessCountResponse(String shortenUrl, String originalUrl, Long accessCount) {
}
