package mss301.fa25.s4.content_service.constant;

import lombok.Getter;

@Getter
public enum GradeLevel {
    GRADE_1(1, "Grade 1"),
    GRADE_2(2, "Grade 2"),
    GRADE_3(3, "Grade 3"),
    GRADE_4(4, "Grade 4"),
    GRADE_5(5, "Grade 5"),
    GRADE_6(6, "Grade 6"),
    GRADE_7(7, "Grade 7"),
    GRADE_8(8, "Grade 8"),
    GRADE_9(9, "Grade 9"),
    GRADE_10(10, "Grade 10"),
    GRADE_11(11, "Grade 11"),
    GRADE_12(12, "Grade 12");

    private final int level;
    private final String displayName;

    GradeLevel(int level, String displayName) {
        this.level = level;
        this.displayName = displayName;
    }

    public static GradeLevel fromLevel(int level) {
        for (GradeLevel grade : values()) {
            if (grade.level == level) {
                return grade;
            }
        }
        throw new IllegalArgumentException("Invalid grade level: " + level);
    }
}