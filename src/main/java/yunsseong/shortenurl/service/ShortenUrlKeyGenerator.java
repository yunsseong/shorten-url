package yunsseong.shortenurl.service;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yunsseong.shortenurl.limit.Limitable;

@Service
@RequiredArgsConstructor
public class ShortenUrlKeyGenerator {
    private final RandomNumberGenerator randNumGen;
    private final Limitable limit;
    private final String stringPool = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Set<String> urlKeySet = new HashSet<>();
    private long maxLimit = 0L;

    public String getUniqueShortenUrlKey() {
        if (urlKeySet.size() == maxLimit) {
            throw new RuntimeException("키를 생성할 수 없습니다.");
        }

        String shortenUrlKey = "";
        do {
            shortenUrlKey = generateShortenUrlKey();
        } while (urlKeySet.contains(shortenUrlKey));
        urlKeySet.add(shortenUrlKey);
        return shortenUrlKey;
    }

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

    public long getMaxLimit() {
        if (maxLimit == 0) {
            maxLimit = (long) Math.pow(stringPool.length(), limit.getLimit());
        }
        return maxLimit;
    }
}
