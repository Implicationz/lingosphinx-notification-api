package com.lingosphinx.notification.controller;

import com.lingosphinx.notification.dto.NotificationDto;
import com.lingosphinx.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Tag(name = "Notification API")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationDto> create(@RequestBody @Valid NotificationDto notification) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(notification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> readAll() {
        return ResponseEntity.ok(notificationService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> update(@PathVariable Long id, @RequestBody @Valid NotificationDto notification) {
        return ResponseEntity.ok(notificationService.update(id, notification));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}