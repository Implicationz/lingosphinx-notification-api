package com.lingosphinx.notification.dto;

import com.lingosphinx.notification.domain.NotificationChannelType;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationDto {

    private Long id;
    private UUID receiver;
    String title;
    String message;
    private List<NotificationChannelType> allowedChannelTypes = new ArrayList<>();
    private List<NotificationJobDto> jobs = new ArrayList<>();

}