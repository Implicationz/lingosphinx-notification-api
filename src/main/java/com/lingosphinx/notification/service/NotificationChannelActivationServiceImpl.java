package com.lingosphinx.notification.service;

import com.lingosphinx.notification.domain.NotificationChannel;
import com.lingosphinx.notification.domain.NotificationChannelStatus;
import com.lingosphinx.notification.domain.Receiver;
import com.lingosphinx.notification.dto.NotificationChannelActivationDto;
import com.lingosphinx.notification.dto.NotificationChannelDto;
import com.lingosphinx.notification.exception.ResourceNotFoundException;
import com.lingosphinx.notification.mapper.NotificationChannelMapper;
import com.lingosphinx.notification.repository.NotificationChannelRepository;
import com.lingosphinx.notification.repository.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationChannelActivationServiceImpl implements NotificationChannelActivationService {

    private final NotificationChannelRepository notificationChannelRepository;
    private final NotificationChannelMapper notificationChannelMapper;
    private final UserService userService;
    private final ReceiverRepository receiverRepository;

    @Override
    public NotificationChannelDto activateByCurrentUser(NotificationChannelActivationDto notificationChannelActivationDto) {
        var userId = userService.getCurrentUserId();

        var existingChannel = notificationChannelRepository
                .findByReceiver_UserIdAndType(userId, notificationChannelActivationDto.getType());

        if (existingChannel.isPresent()) {
            var channel = existingChannel.get();
            channel.setToken(notificationChannelActivationDto.getToken());
            var updatedChannel = notificationChannelRepository.save(channel);
            log.info("NotificationChannel token renewed for user={}: id={}", userId, updatedChannel.getId());
            return notificationChannelMapper.toDto(updatedChannel);
        }

        var receiver = receiverRepository.findByUserId(userId)
                .orElseGet(() -> {
                    var newReceiver = new Receiver();
                    newReceiver.setUserId(userId);
                    return receiverRepository.save(newReceiver);
                });

        var notificationChannel = NotificationChannel.builder()
                .type(notificationChannelActivationDto.getType())
                .token(notificationChannelActivationDto.getToken())
                .receiver(receiver)
                .build();

        var savedNotificationChannel = notificationChannelRepository.save(notificationChannel);
        log.info("NotificationChannel activated for user={}: id={}", userId, savedNotificationChannel.getId());
        return notificationChannelMapper.toDto(savedNotificationChannel);
    }
}