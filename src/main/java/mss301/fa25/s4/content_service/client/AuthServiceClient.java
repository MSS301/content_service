package mss301.fa25.s4.content_service.client;

import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "auth-service", contextId = "authServiceClient", path = "/auth/user-profiles")
public interface AuthServiceClient {
    
    @GetMapping("/me")
    ApiResponse<UserProfileResponse> getCurrentUserProfile();
}
