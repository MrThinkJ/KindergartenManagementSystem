package com.group3.kindergartenmanagementsystem.payload;

import com.group3.kindergartenmanagementsystem.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildDTO {
    private Integer id;
    private String fullName;
    private Integer age;
    private LocalDate birthDay;
    private Integer height;
    private Integer weight;
    private Boolean gender;
    private String hobby;
    private Integer parentId;
    private Integer teacherId;
    private Integer classroomId;
}
