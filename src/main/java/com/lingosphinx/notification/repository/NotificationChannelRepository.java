package com.lingosphinx.notification.repository;

import com.lingosphinx.notification.domain.NotificationChannel;
import com.lingosphinx.notification.domain.NotificationChannelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;
import java.util.UUID;

public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Long>, JpaSpecificationExecutor<NotificationChannel> {
    Optional<NotificationChannel> findByReceiver_UserIdAndType(UUID userId, NotificationChannelType type);
}
