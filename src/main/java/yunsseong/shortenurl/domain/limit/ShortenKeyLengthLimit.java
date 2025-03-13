package yunsseong.shortenurl.domain.limit;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ShortenKeyLengthLimit implements Limitable{
    private int limit = 8;

    public ShortenKeyLengthLimit(int limitLength) {
        this.limit = limitLength;
    }

    @Override
    public int getLimit() {
        return limit;
    }
}
