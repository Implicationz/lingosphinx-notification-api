package com.lingosphinx.notification.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NotificationJob {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    private Notification notification;
    
    @ManyToOne(optional = false)
    private NotificationChannel channel;

    @Builder.Default
    @Column(nullable = false)
    private int trialCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private NotificationJobStatus status = NotificationJobStatus.PENDING;

}