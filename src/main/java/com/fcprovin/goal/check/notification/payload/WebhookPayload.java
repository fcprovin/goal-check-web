package com.fcprovin.goal.check.notification.payload;

import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public interface WebhookPayload {

    String getTitle();
    String getColor();
    List<Field> getFields();

    default List<Attachment> getAttachments() {
        return List.of(com.slack.api.model.Attachment.builder()
                .color(getColor())
                .fields(getFields())
                .build());
    }

    default Field getField(String title, String value) {
        return Field.builder()
                    .title(title)
                    .value(value)
                    .valueShortEnough(false)
                    .build();
    }

    default Field getField(String title, LocalDateTime value) {
        return Field.builder()
                    .title(title)
                    .value(dateformat(value))
                    .valueShortEnough(false)
                    .build();
    }

    default String dateformat(LocalDateTime value) {
        return value.format(ofPattern("yyyy-MM-dd HH:mm"));
    }
}
