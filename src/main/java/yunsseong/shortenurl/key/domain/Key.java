package yunsseong.shortenurl.key.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yunsseong.shortenurl.common.util.RandomNumber;
import yunsseong.shortenurl.common.exception.CustomException;
import yunsseong.shortenurl.common.exception.error_code.KeyErrorCode;
import yunsseong.shortenurl.limit.Limitable;

@Service
@RequiredArgsConstructor
public class Key {
    private final RandomNumber randNumGen;
    private final Limitable limit;
    private final String stringPool = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Set<String> urlKeySet = new HashSet<>();
    private long maxLimit = 0L;

    public String getKey() {
        if (urlKeySet.size() == getMaxLimit()) {
            throw new CustomException(KeyErrorCode.EXHAUST_ALL_KEY);
        }

        String key = "";
        do {
            key = generateKey();
        } while (urlKeySet.contains(key));
        urlKeySet.add(key);
        return key;
    }

    public String generateKey() {
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
