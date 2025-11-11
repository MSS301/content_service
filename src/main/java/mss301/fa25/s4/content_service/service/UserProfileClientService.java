package mss301.fa25.s4.content_service.service;

import mss301.fa25.s4.content_service.dto.response.UserProfileResponse;

public interface UserProfileClientService {
    /**
     * Get user profile ID from auth-service using accountId (UUID from JWT)
     * @param accountId UUID from JWT token
     * @return UserProfile ID (Integer)
     */
    Integer getUserProfileId(String accountId);
    
    /**
     * Get full user profile from auth-service
     * @param accountId UUID from JWT token
     * @return UserProfileResponse
     */
    UserProfileResponse getUserProfile(String accountId);
    
    /**
     * Get user profile by profile ID
     * @param profileId UserProfile ID
     * @return UserProfileResponse
     */
    UserProfileResponse getUserProfileById(Integer profileId);
}
