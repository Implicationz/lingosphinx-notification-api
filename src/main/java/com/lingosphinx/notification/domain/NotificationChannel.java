package com.lingosphinx.notification.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NotificationChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannelType type = NotificationChannelType.FCM;

    @ManyToOne(optional = false)
    private Receiver receiver;

    @Column(nullable = false)
    private String token;

    @Builder.Default
    @Column(nullable = false)
    private int priority = 1;

    @Builder.Default
    @Column(nullable = false)
    private NotificationChannelStatus status = NotificationChannelStatus.ACTIVE;
}