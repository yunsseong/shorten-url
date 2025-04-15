package yunsseong.shortenurl.limit;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class Limit implements Limitable{
    private int limit = 8;

    public Limit(int limitLength) {
        this.limit = limitLength;
    }

    @Override
    public int getLimit() {
        return limit;
    }
}
