package yunsseong.shortenurl.url.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yunsseong.shortenurl.url.dto.request.UrlComponent;
import yunsseong.shortenurl.url.dto.response.AccessCountResponse;
import yunsseong.shortenurl.url.dto.request.UrlRequest;
import yunsseong.shortenurl.url.domain.UrlMapper;

@RestController
@RequiredArgsConstructor
public class ShortenUrlController {

    private final UrlMapper urlMapper;

    @GetMapping("/{key}")
    public void accessUrl(@PathVariable String key, HttpServletResponse response) throws IOException {
        String originalUrl = urlMapper.access(key);
        response.sendRedirect(originalUrl);
    }

    @PostMapping
    public ResponseEntity<String> registerUrl(@RequestBody UrlRequest urlRequest, HttpServletRequest request) {
        String mappedKey = urlMapper.register(urlRequest.originalUrl());
        String shortenUrl = request.getRequestURL() + mappedKey;
        return ResponseEntity.ok(shortenUrl);
    }

    @GetMapping("/count/{key}")
    public ResponseEntity<AccessCountResponse> getAccessCount(@PathVariable String key, HttpServletRequest request) {
        UrlComponent urlComponent = new UrlComponent(request.getScheme(), request.getHeader("Host"));
        return ResponseEntity.ok(urlMapper.getUrlAccessCountInfo(key, urlComponent));
    }
}
