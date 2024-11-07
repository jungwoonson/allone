package live.allone.utils;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class PagingResponse<T> {

    private final List<T> data;
    private final int page;
    private final int size;
    private final int total;
}
