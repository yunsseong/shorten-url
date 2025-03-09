package yunsseong.shortenurl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortenUrlKeyGenerator {
    private final String stringPool = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private final RandomNumberGenerator randomNumberGenerator;

    public String generateRandomValue() {
        int randomNum = randomNumberGenerator.generateRandomNumber(stringPool.length());
        return String.valueOf(stringPool.charAt(randomNum));
    }
}
