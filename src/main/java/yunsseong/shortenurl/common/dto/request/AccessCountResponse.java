package yunsseong.shortenurl.common.dto.request;

public record AccessCountResponse(String shortenUrl, String originalUrl, Long accessCount) {
}
