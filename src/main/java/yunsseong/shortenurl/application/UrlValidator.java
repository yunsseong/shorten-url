package yunsseong.shortenurl.application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.springframework.stereotype.Service;
import yunsseong.shortenurl.common.exception.CustomException;
import yunsseong.shortenurl.common.exception.error_code.UrlErrorCode;

@Service
public class UrlValidator {

    public boolean isValidUrl(String requestUrl) {
        try {
            URL url = new URI(requestUrl).toURL();
            url.openConnection().connect();
            return true;
        } catch (MalformedURLException | URISyntaxException | IllegalArgumentException e) {
            throw new CustomException(UrlErrorCode.MALFORMED_URL);
        } catch (IOException e) {
            throw new CustomException(UrlErrorCode.UNREACHABLE_URL);
        }
    }
}
