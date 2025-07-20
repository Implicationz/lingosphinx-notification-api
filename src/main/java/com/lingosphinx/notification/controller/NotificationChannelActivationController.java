package com.lingosphinx.notification.controller;

import com.lingosphinx.notification.dto.NotificationChannelActivationDto;
import com.lingosphinx.notification.dto.NotificationChannelDto;
import com.lingosphinx.notification.service.NotificationChannelActivationService;
import com.lingosphinx.notification.service.NotificationChannelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification-channel-activation")
@RequiredArgsConstructor
@Tag(name = "NotificationChannelActivation API")
public class NotificationChannelActivationController {

    private final NotificationChannelActivationService notificationChannelActivationService;

    @PostMapping
    public ResponseEntity<NotificationChannelDto> create(@RequestBody @Valid NotificationChannelActivationDto notificationChannelActivation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationChannelActivationService.activateByCurrentUser(notificationChannelActivation));
    }
}