package mss301.fa25.s4.content_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key", HttpStatus.BAD_REQUEST),

    // Subject errors
    SUBJECT_NOT_FOUND(2001, "Subject not found", HttpStatus.NOT_FOUND),
    SUBJECT_ALREADY_EXISTS(2002, "Subject already exists", HttpStatus.CONFLICT),

    // Grade errors
    GRADE_NOT_FOUND(2003, "Grade not found", HttpStatus.NOT_FOUND),
    GRADE_ALREADY_EXISTS(2004, "Grade with this level already exists", HttpStatus.CONFLICT),

    // Chapter errors
    CHAPTER_NOT_FOUND(2005, "Chapter not found", HttpStatus.NOT_FOUND),

    // Curriculum Lesson errors
    CURRICULUM_LESSON_NOT_FOUND(2006, "Curriculum lesson not found", HttpStatus.NOT_FOUND),

    // Teacher Lesson errors
    TEACHER_LESSON_NOT_FOUND(2007, "Teacher lesson not found", HttpStatus.NOT_FOUND),
    TEACHER_LESSON_UNAUTHORIZED(2008, "You are not authorized to access this lesson", HttpStatus.FORBIDDEN),
    INVALID_LESSON_STATUS(2009, "Invalid lesson status", HttpStatus.BAD_REQUEST),
    LESSON_NOT_DRAFT(2014, "Lesson is not in draft status", HttpStatus.BAD_REQUEST),

    // File errors
    LESSON_FILE_NOT_FOUND(2010, "Lesson file not found", HttpStatus.NOT_FOUND),
    FILE_UPLOAD_FAILED(2011, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FILE_TYPE(2012, "Invalid file type", HttpStatus.BAD_REQUEST),
    FILE_SIZE_EXCEEDED(2013, "File size exceeded maximum limit", HttpStatus.BAD_REQUEST),

    // Feedback errors
    INVALID_RATING(2016, "Rating must be between 1 and 5", HttpStatus.BAD_REQUEST),

    // Authentication & Authorization
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),

    // External Service errors
    USER_SERVICE_ERROR(3001, "Error communicating with User Service", HttpStatus.SERVICE_UNAVAILABLE),
    AI_SERVICE_ERROR(3002, "Error communicating with AI Service", HttpStatus.SERVICE_UNAVAILABLE),
    TRANSACTION_SERVICE_ERROR(3003, "Error communicating with Transaction Service", HttpStatus.SERVICE_UNAVAILABLE),

    // Rating errors
    LESSON_RATING_NOT_FOUND(2018, "Lesson rating not found", HttpStatus.NOT_FOUND),
    RATING_ALREADY_EXISTS(2019, "You have already rated this lesson", HttpStatus.CONFLICT),

    // Comment errors
    LESSON_COMMENT_NOT_FOUND(2020, "Lesson comment not found", HttpStatus.NOT_FOUND),
    COMMENT_UNAUTHORIZED(2021, "You are not authorized to modify this comment", HttpStatus.FORBIDDEN),
    
    // User Profile errors
    USER_PROFILE_NOT_FOUND(2022, "User profile not found in auth service", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}