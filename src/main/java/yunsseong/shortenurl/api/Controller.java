package yunsseong.shortenurl.api;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<String> getOriginalUrl(@PathVariable String key) {
        return ResponseEntity.ok(urlMapper.getOriginalUrlByKey(key));
    }

    @PostMapping
    public ResponseEntity<String> registerUrl(@RequestBody UrlRequest urlRequest, HttpServletRequest request) {
        String mappedKey = urlMapper.makeNewMapping(urlRequest.originalUrl());
        String shortenUrl = request.getRequestURL() + mappedKey;
        return ResponseEntity.ok(shortenUrl);
    }
}
