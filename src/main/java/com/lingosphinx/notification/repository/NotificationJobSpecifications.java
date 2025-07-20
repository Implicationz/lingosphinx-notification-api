package com.lingosphinx.notification.repository;

import org.springframework.data.jpa.domain.Specification;
import com.lingosphinx.notification.domain.*;

public class NotificationJobSpecifications {
    public static Specification<NotificationJob> channelActiveAndTypeAndStatusNotProcessing(
            NotificationChannelType type) {
        return (root, query, cb) -> {
            var channel = root.get("channel");
            return cb.and(
                    cb.equal(channel.get("status"), NotificationChannelStatus.ACTIVE),
                    cb.equal(channel.get("type"), type),
                    cb.notEqual(root.get("status"), NotificationJobStatus.PROCESSING)
            );
        };
    }
}