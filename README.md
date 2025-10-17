# Content Service

Content Service is a microservice responsible for managing educational content including subjects, chapters, curriculum lessons, teacher lessons, ratings, comments, and files.

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.3.5
- **Spring Cloud**: 2023.0.1
- **Database**: PostgreSQL
- **Service Discovery**: Netflix Eureka
- **Inter-service Communication**: OpenFeign
- **ORM**: Spring Data JPA (Hibernate)
- **Validation**: Jakarta Validation
- **Mapping**: MapStruct 1.5.5
- **Lombok**: 1.18.30
- **Build Tool**: Maven

## Architecture

This service follows a layered architecture:

```
├── controller/      # REST API endpoints
├── service/         # Business logic (interface + implementation)
├── repository/      # Data access layer
├── entity/          # JPA entities
├── dto/            # Data Transfer Objects
│   ├── request/    # Request DTOs
│   └── response/   # Response DTOs
├── mapper/         # MapStruct mappers
├── configuration/  # Spring configurations
├── exception/      # Custom exceptions and error handling
└── enums/          # Enumeration types
```

## Features

### 1. Subject Management
- Create, read, update, delete subjects
- Search subjects by name
- Track chapter count per subject

### 2. Chapter Management
- Organize content by grade level (1-12)
- Link chapters to subjects
- Order chapters within subjects
- Track lesson count per chapter

### 3. Curriculum Lesson Management
- Create standardized curriculum lessons
- Associate lessons with chapters
- Maintain lesson order
- Store lesson descriptions

### 4. Teacher Lesson Management
- Teachers can create custom lessons
- Link to curriculum lessons (optional)
- Lesson status workflow: DRAFT → GENERATING → GENERATED → PUBLISHED → ARCHIVED
- Track view counts
- Associate with classes
- Get lesson analytics (views, ratings, comments, files)

### 5. Lesson Rating System
- Students can rate lessons (1-5 stars)
- One rating per student per lesson
- Calculate average ratings
- Track rating counts

### 6. Lesson Comment System
- Students can comment on lessons
- Update and delete comments
- Timestamps for all comments
- Order comments by creation date

### 7. Lesson File Management
- Upload files for lessons
- Track file metadata (name, URL, MIME type, size)
- Associate files with uploaders
- Support for multiple files per lesson

## API Endpoints

### Subjects
```
POST   /content/subjects              - Create subject
GET    /content/subjects              - Get all subjects
GET    /content/subjects?name={name}  - Search subjects
GET    /content/subjects/{id}         - Get subject by ID
PUT    /content/subjects/{id}         - Update subject
DELETE /content/subjects/{id}         - Delete subject (soft delete)
```

### Chapters
```
POST   /content/chapters                           - Create chapter
GET    /content/chapters                           - Get all chapters
GET    /content/chapters?subjectId={id}            - Get by subject
GET    /content/chapters?grade={grade}             - Get by grade
GET    /content/chapters?subjectId={id}&grade={g}  - Get by subject and grade
GET    /content/chapters/{id}                      - Get chapter by ID
PUT    /content/chapters/{id}                      - Update chapter
DELETE /content/chapters/{id}                      - Delete chapter
```

### Curriculum Lessons
```
POST   /content/curriculum-lessons                 - Create lesson
GET    /content/curriculum-lessons                 - Get all lessons
GET    /content/curriculum-lessons?chapterId={id}  - Get by chapter
GET    /content/curriculum-lessons/{id}            - Get lesson by ID
PUT    /content/curriculum-lessons/{id}            - Update lesson
DELETE /content/curriculum-lessons/{id}            - Delete lesson
```

### Teacher Lessons
```
POST   /content/teacher-lessons                    - Create lesson
GET    /content/teacher-lessons                    - Get all lessons
GET    /content/teacher-lessons?teacherId={id}     - Get by teacher
GET    /content/teacher-lessons?status={status}    - Get by status
GET    /content/teacher-lessons?classId={id}       - Get by class
GET    /content/teacher-lessons/{id}               - Get lesson by ID
PUT    /content/teacher-lessons/{id}               - Update lesson
DELETE /content/teacher-lessons/{id}               - Delete lesson
POST   /content/teacher-lessons/{id}/view          - Increment view count
GET    /content/teacher-lessons/{id}/analytics     - Get lesson analytics
```

### Lesson Ratings
```
POST   /content/lesson-ratings                     - Rate a lesson
GET    /content/lesson-ratings?lessonId={id}       - Get ratings by lesson
GET    /content/lesson-ratings?studentId={id}      - Get ratings by student
GET    /content/lesson-ratings/{id}                - Get rating by ID
PUT    /content/lesson-ratings/{id}                - Update rating
DELETE /content/lesson-ratings/{id}                - Delete rating
```

### Lesson Comments
```
POST   /content/lesson-comments                    - Create comment
GET    /content/lesson-comments?lessonId={id}      - Get comments by lesson
GET    /content/lesson-comments?studentId={id}     - Get comments by student
GET    /content/lesson-comments/{id}               - Get comment by ID
PUT    /content/lesson-comments/{id}               - Update comment
DELETE /content/lesson-comments/{id}               - Delete comment
```

### Lesson Files
```
POST   /content/lesson-files                       - Upload file
GET    /content/lesson-files?lessonId={id}         - Get files by lesson
GET    /content/lesson-files?uploaderId={id}       - Get files by uploader
GET    /content/lesson-files/{id}                  - Get file by ID
DELETE /content/lesson-files/{id}                  - Delete file
```

## Database Schema

### Key Entities
- **Subject**: Educational subjects (e.g., Math, Science)
- **Chapter**: Chapters within subjects, organized by grade
- **CurriculumLesson**: Standard curriculum lessons
- **TeacherLesson**: Custom teacher-created lessons
- **LessonRating**: Student ratings for lessons
- **LessonComment**: Student comments on lessons
- **LessonFile**: Files attached to lessons

### Entity Status
All entities support soft deletion via `EntityStatus` enum:
- `ACTIVE`: Entity is available
- `INACTIVE`: Entity is soft-deleted

## Configuration

### Application Properties
```properties
spring.application.name=content_service
server.port=8083
server.servlet.context-path=/content

# Database
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Eureka
eureka.client.service-url.defaultZone=http://localhost:8888/eureka
```

### Environment Variables
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/content_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password
```

## Error Handling

The service uses a global exception handler that returns standardized error responses:

```json
{
  "code": 2001,
  "message": "Subject not found",
  "result": null
}
```

### Error Codes
- `2001`: Subject not found
- `2002`: Subject already exists
- `2005`: Chapter not found
- `2006`: Curriculum lesson not found
- `2007`: Teacher lesson not found
- `2008`: Unauthorized access to lesson
- `2009`: Invalid lesson status
- `2010`: Lesson file not found
- `2018`: Lesson rating not found
- `2019`: Rating already exists
- `2020`: Lesson comment not found

## Building and Running

### Prerequisites
- Java 21
- Maven 3.8+
- PostgreSQL 14+
- Eureka Server running on port 8888

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

Or run the JAR:
```bash
java -jar target/content_service-0.0.1-SNAPSHOT.jar
```

## Integration with Other Services

### User Service (via Feign - To be implemented)
- Fetch teacher names
- Fetch student names
- Fetch class information
- Validate user permissions

### AI Service (Future)
- Generate lesson content
- Auto-create slides
- Content recommendations

### Transaction Service (Future)
- Track premium content access
- Payment integration

## Grade Levels

The system supports grades 1-12 with the `GradeLevel` enum:
```java
GRADE_1, GRADE_2, ..., GRADE_12
```

## Lesson Status Workflow

Teacher lessons follow this status workflow:
1. **DRAFT**: Teacher is working on the lesson
2. **GENERATING**: AI is generating content (if applicable)
3. **GENERATED**: AI generation completed
4. **PUBLISHED**: Available to students
5. **ARCHIVED**: No longer active

## Best Practices

1. **Soft Delete**: Use `EntityStatus.INACTIVE` instead of hard deletes
2. **Timestamps**: All entities track creation and update times
3. **Validation**: Use Jakarta Validation annotations on DTOs
4. **Logging**: Use SLF4J with appropriate log levels
5. **Transaction Management**: Use `@Transactional` for write operations
6. **Error Handling**: Throw `AppException` with appropriate `ErrorCode`

## Testing

```bash
# Run all tests
mvn test

# Run with coverage
mvn clean test jacoco:report
```

## API Response Format

All API responses follow this structure:
```json
{
  "code": 1000,
  "message": "Success",
  "result": {
    // Response data
  }
}
```

## Future Enhancements

- [ ] File upload to S3/MinIO integration
- [ ] AI-powered content generation
- [ ] Advanced analytics dashboard
- [ ] Content versioning
- [ ] Collaborative editing
- [ ] Real-time notifications
- [ ] Content recommendations
- [ ] Multi-language support
- [ ] Content approval workflow
- [ ] Lesson templates

## Contributing

1. Follow the existing code structure
2. Use MapStruct for entity-DTO mapping
3. Add proper validation to all request DTOs
4. Include logging in all service methods
5. Write unit tests for new features
6. Update this README for significant changes

## Contact

[sonnamsonnam402@gmail.com]