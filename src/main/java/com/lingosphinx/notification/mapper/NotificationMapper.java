package com.lingosphinx.notification.mapper;

import com.lingosphinx.notification.domain.Notification;
import com.lingosphinx.notification.domain.NotificationChannel;
import com.lingosphinx.notification.domain.NotificationJob;
import com.lingosphinx.notification.dto.NotificationChannelDto;
import com.lingosphinx.notification.dto.NotificationDto;
import com.lingosphinx.notification.dto.NotificationJobDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "receiver", ignore = true)
    NotificationDto toDto(Notification entity);
    @Mapping(target = "receiver", ignore = true)
    Notification toEntity(NotificationDto dto);

    @Mapping(target = "notification", ignore = true)
    NotificationJobDto toDto(NotificationJob entity);
    @Mapping(target = "notification", ignore = true)
    NotificationJob toEntity(NotificationJobDto dto);

    @Mapping(target = "receiver", ignore = true)
    NotificationChannelDto toDto(NotificationChannel entity);
    @Mapping(target = "receiver", ignore = true)
    NotificationChannel toEntity(NotificationChannelDto dto);


    @Mapping(target = "receiver", ignore = true)
    void toEntityFromDto(NotificationDto dto, @MappingTarget Notification entity);
}