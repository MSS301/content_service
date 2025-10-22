package mss301.fa25.s4.content_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginatedResponse<T> {
    @Builder.Default
    int code = 1000;

    String message;
    java.util.List<T> content;
    PaginationMetadata pagination;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class PaginationMetadata {
        int page;
        int size;
        long totalElements;
        int totalPages;
        boolean first;
        boolean last;
        boolean hasNext;
        boolean hasPrevious;
        int numberOfElements;
    }

    public static <T> PaginatedResponse<T> of(Page<T> page) {
        PaginationMetadata metadata = PaginationMetadata.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .numberOfElements(page.getNumberOfElements())
                .build();

        return PaginatedResponse.<T>builder()
                .content(page.getContent())
                .pagination(metadata)
                .build();
    }
}
