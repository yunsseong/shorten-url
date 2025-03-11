package yunsseong.shortenurl.api;

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
import yunsseong.shortenurl.dto.request.UrlRequest;
import yunsseong.shortenurl.service.UrlMapper;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final UrlMapper urlMapper;

    @GetMapping("/{key}")
    public void getOriginalUrl(@PathVariable String key, HttpServletResponse response) throws IOException {
        String originalUrl = urlMapper.getOriginalUrlByKey(key);
        response.sendRedirect(originalUrl);
    }

    @PostMapping
    public ResponseEntity<String> registerUrl(@RequestBody UrlRequest urlRequest, HttpServletRequest request) {
        String mappedKey = urlMapper.makeNewMapping(urlRequest.originalUrl());
        String shortenUrl = request.getRequestURL() + mappedKey;
        return ResponseEntity.ok(shortenUrl);
    }
}
