package com.lingosphinx.notification.service;

import com.lingosphinx.notification.domain.*;
import com.lingosphinx.notification.dto.NotificationDto;
import com.lingosphinx.notification.event.NotificationCreatedEvent;
import com.lingosphinx.notification.exception.ResourceNotFoundException;
import com.lingosphinx.notification.mapper.NotificationMapper;
import com.lingosphinx.notification.repository.NotificationRepository;
import com.lingosphinx.notification.repository.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final ReceiverRepository receiverRepository;
    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    protected Stream<NotificationChannel> allowedChannel(Receiver receiver, List<NotificationChannelType> allowedChannelTypes) {

        var allowedChannels = receiver.getChannels().stream()
                .filter(channel -> channel.getStatus().equals(NotificationChannelStatus.ACTIVE));

        if (!allowedChannelTypes.isEmpty()) {
            allowedChannels = allowedChannels
                    .filter(channel -> allowedChannelTypes.contains(channel.getType()));
        }

        return allowedChannels
                .sorted(Comparator.comparingInt(NotificationChannel::getPriority));
    }
    @Override
    public NotificationDto create(NotificationDto notificationDto) {
        var userId = notificationDto.getReceiver();
        var receiver = receiverRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found for userId=" + userId));

        var allowedChannelTypes = notificationDto.getAllowedChannelTypes();

        var notification = notificationMapper.toEntity(notificationDto);
        notification.setReceiver(receiver);

        var allowedChannels = allowedChannel(receiver, allowedChannelTypes).toList();
        if (!allowedChannels.isEmpty()) {
            var highestPriority = allowedChannels.get(0).getPriority();
            var jobs = allowedChannels.stream()
                    .takeWhile(channel -> channel.getPriority() == highestPriority)
                    .map(channel -> NotificationJob.builder()
                            .channel(channel)
                            .notification(notification)
                            .build())
                    .toList();
            notification.setJobs(jobs);
        }


        var savedNotification = notificationRepository.save(notification);
        log.info("Notification created successfully: id={}", savedNotification.getId());

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        publisher.publishEvent(new NotificationCreatedEvent(notification));
                    }
                }
        );

        return notificationMapper.toDto(savedNotification);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationDto readById(Long id) {
        var notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        log.info("Notification read successfully: id={}", id);
        return notificationMapper.toDto(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> readAll() {
        var result = notificationRepository.findAll().stream()
                .map(notificationMapper::toDto)
                .toList();
        log.info("All notifications read successfully, count={}", result.size());
        return result;
    }

    @Override
    public NotificationDto update(Long id, NotificationDto notificationDto) {
        var existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        notificationMapper.toEntityFromDto(notificationDto, existingNotification);
        log.info("Notification updated successfully: id={}", existingNotification.getId());
        return notificationMapper.toDto(existingNotification);
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
        log.info("Notification deleted successfully: id={}", id);
    }
}