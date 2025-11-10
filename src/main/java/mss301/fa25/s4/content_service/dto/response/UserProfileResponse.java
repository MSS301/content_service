package mss301.fa25.s4.content_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    Integer id;
    String accountId;
    Integer schoolId;
    String fullName;
    LocalDate dateOfBirth;
    String avatarUrl;
    String role;
}
