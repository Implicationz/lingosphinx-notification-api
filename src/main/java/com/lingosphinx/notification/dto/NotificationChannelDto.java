package com.lingosphinx.notification.dto;

import com.lingosphinx.notification.domain.Notification;
import com.lingosphinx.notification.domain.NotificationChannelStatus;
import com.lingosphinx.notification.domain.NotificationChannelType;
import com.lingosphinx.notification.domain.Receiver;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationChannelDto {


    private Long id;
    private NotificationChannelType type = NotificationChannelType.FCM;
    private ReceiverDto receiver;
    private String token;
    @Builder.Default
    private int priority = 1;
    @Builder.Default
    private NotificationChannelStatus status = NotificationChannelStatus.ACTIVE;
    @Builder.Default
    private List<NotificationDto> notification = new ArrayList<>();

}