package com.group3.kindergartenmanagementsystem.payload;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime postDate;
}
