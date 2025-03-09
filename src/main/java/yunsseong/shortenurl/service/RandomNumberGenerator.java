package yunsseong.shortenurl.service;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class RandomNumberGenerator {
    private final Random random = new Random();

    public int generateRandomNumber(int end) {
        return random.nextInt(end);
    }
}
