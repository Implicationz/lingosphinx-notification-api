package com.lingosphinx.notification.mapper;

import com.lingosphinx.notification.domain.NotificationChannel;
import com.lingosphinx.notification.domain.Receiver;
import com.lingosphinx.notification.dto.NotificationChannelDto;
import com.lingosphinx.notification.dto.ReceiverDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificationChannelMapper {
    NotificationChannelDto toDto(NotificationChannel entity);
    NotificationChannel toEntity(NotificationChannelDto dto);

    @Mapping(target = "channels", ignore = true)
    ReceiverDto toDto(Receiver entity);
    @Mapping(target = "channels", ignore = true)
    Receiver toEntity(ReceiverDto dto);

    void toEntityFromDto(NotificationChannelDto dto, @MappingTarget NotificationChannel entity);
}