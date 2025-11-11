package mss301.fa25.s4.content_service.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mss301.fa25.s4.content_service.client.AuthServiceClient;
import mss301.fa25.s4.content_service.dto.response.ApiResponse;
import mss301.fa25.s4.content_service.dto.response.UserProfileResponse;
import mss301.fa25.s4.content_service.exception.AppException;
import mss301.fa25.s4.content_service.exception.ErrorCode;
import mss301.fa25.s4.content_service.service.UserProfileClientService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileClientServiceImpl implements UserProfileClientService {
    AuthServiceClient authServiceClient;

    @Override
    public Integer getUserProfileId(String accountId) {
        log.info("Fetching user profile ID from auth-service using /me endpoint");
        try {
            ApiResponse<UserProfileResponse> response = authServiceClient.getCurrentUserProfile();
            if (response.getResult() == null) {
                throw new AppException(ErrorCode.USER_PROFILE_NOT_FOUND);
            }
            log.info("Successfully fetched user profile ID: {}", response.getResult().getId());
            return response.getResult().getId();
        } catch (Exception e) {
            log.error("Error fetching user profile from auth-service: {}", e.getMessage());
            throw new AppException(ErrorCode.USER_PROFILE_NOT_FOUND);
        }
    }

    @Override
    public UserProfileResponse getUserProfile(String accountId) {
        log.info("Fetching user profile from auth-service using /me endpoint");
        try {
            ApiResponse<UserProfileResponse> response = authServiceClient.getCurrentUserProfile();
            if (response.getResult() == null) {
                throw new AppException(ErrorCode.USER_PROFILE_NOT_FOUND);
            }
            log.info("Successfully fetched user profile for accountId: {}", response.getResult().getAccountId());
            return response.getResult();
        } catch (Exception e) {
            log.error("Error fetching user profile from auth-service: {}", e.getMessage());
            throw new AppException(ErrorCode.USER_PROFILE_NOT_FOUND);
        }
    }

    @Override
    public UserProfileResponse getUserProfileById(Integer profileId) {
        log.info("Fetching user profile by ID: {}", profileId);
        try {
            ApiResponse<UserProfileResponse> response = authServiceClient.getUserProfileById(profileId);
            if (response.getResult() == null) {
                throw new AppException(ErrorCode.USER_PROFILE_NOT_FOUND);
            }
            log.info("Successfully fetched user profile: {}", response.getResult().getFullName());
            return response.getResult();
        } catch (Exception e) {
            log.error("Error fetching user profile by ID from auth-service: {}", e.getMessage());
            throw new AppException(ErrorCode.USER_PROFILE_NOT_FOUND);
        }
    }
}
