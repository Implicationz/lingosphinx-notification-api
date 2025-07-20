package com.lingosphinx.notification.service;
import com.lingosphinx.notification.dto.NotificationChannelActivationDto;
import com.lingosphinx.notification.dto.NotificationChannelDto;

import java.util.List;

public interface NotificationChannelActivationService {
    NotificationChannelDto activateByCurrentUser(NotificationChannelActivationDto goal);
}
