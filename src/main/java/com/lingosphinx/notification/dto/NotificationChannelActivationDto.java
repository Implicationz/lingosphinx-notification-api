package com.lingosphinx.notification.dto;

import com.lingosphinx.notification.domain.Notification;
import com.lingosphinx.notification.domain.NotificationChannelStatus;
import com.lingosphinx.notification.domain.NotificationChannelType;
import com.lingosphinx.notification.domain.Receiver;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationChannelActivationDto {

    private NotificationChannelType type = NotificationChannelType.FCM;
    private String token;
    @Builder.Default
    private int priority = 1;
}