package yunsseong.shortenurl.common.util;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class RandomNumber {
    private final Random random = new Random();

    public int generateRandomNumber(int end) {
        return random.nextInt(end);
    }
}
