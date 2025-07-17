package com.lingosphinx.notification.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationJobDto {

    private Long id;
    private NotificationDto notification;
    private NotificationChannelDto channel;

}