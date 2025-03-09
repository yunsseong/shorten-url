package yunsseong.shortenurl.limit;

import org.springframework.stereotype.Component;

@Component
public class ShortenKeyLengthLimit implements Limitable{
    private final int limit = 8;
    @Override
    public int getLimit() {
        return limit;
    }
}
