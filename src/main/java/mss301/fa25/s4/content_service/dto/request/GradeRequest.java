package mss301.fa25.s4.content_service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GradeRequest {
    @NotNull(message = "Grade level is required")
    @Min(value = 1, message = "Grade level must be between 1 and 12")
    @Max(value = 12, message = "Grade level must be between 1 and 12")
    Integer level;

    @Size(max = 50, message = "Grade name must not exceed 50 characters")
    String name;
}