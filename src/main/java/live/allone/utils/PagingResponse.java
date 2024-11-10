package live.allone.utils;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "목록")
    private final List<T> data;
    @Schema(description = "페이지", example = "1")
    private final int page;
    @Schema(description = "조회 건수", example = "10")
    private final int size;
    @Schema(description = "전체 건수", example = "75000")
    private final int total;
}
