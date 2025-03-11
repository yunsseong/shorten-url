package yunsseong.shortenurl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yunsseong.shortenurl.limit.Limitable;

@Service
@RequiredArgsConstructor
public class ShortenUrlKeyGenerator {
    private final String stringPool = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final RandomNumberGenerator randNumGen;
    private final Limitable limit;

    public String generateShortenUrlKey() {
        StringBuilder shortenUrlKey = new StringBuilder();
        do {
            String value = generateRandomValue();
            shortenUrlKey.append(value);
        } while (shortenUrlKey.length() != limit.getLimit());
        return shortenUrlKey.toString();
    }

    public String generateRandomValue() {
        int randomNum = randNumGen.generateRandomNumber(stringPool.length());
        return String.valueOf(stringPool.charAt(randomNum));
    }
}
