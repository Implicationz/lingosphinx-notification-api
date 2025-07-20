package com.lingosphinx.notification.repository;

import com.lingosphinx.notification.domain.NotificationChannelType;
import com.lingosphinx.notification.domain.NotificationJob;
import com.lingosphinx.notification.domain.NotificationJobStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface NotificationJobRepository extends JpaRepository<NotificationJob, Long>, JpaSpecificationExecutor<NotificationJob> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<NotificationJob> findAllByChannel_TypeAndStatus(NotificationChannelType type, NotificationJobStatus status);
}