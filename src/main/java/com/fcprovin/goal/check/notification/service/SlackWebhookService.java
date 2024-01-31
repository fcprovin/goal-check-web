package com.fcprovin.goal.check.notification.service;

import com.fcprovin.goal.check.domain.attend.Attend;
import com.fcprovin.goal.check.domain.goal.Goal;
import com.fcprovin.goal.check.notification.payload.AttendPayload;
import com.fcprovin.goal.check.notification.payload.GoalPayload;
import com.fcprovin.goal.check.notification.payload.WebhookPayload;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Slf4j
@Service
public class SlackWebhookService {

    @Value("${slack.webhook.url}")
    private String webhookUrl;

    private final Slack slack;

    public SlackWebhookService() {
        this.slack = Slack.getInstance();
    }

    public void execute(Attend attend) {
        send(getPayload(AttendPayload.of(attend)));
    }

    public void execute(Goal goal) {
        send(getPayload(GoalPayload.of(goal)));
    }

    private void send(Payload goal) {
        try {
            WebhookResponse response = slack.send(webhookUrl, goal);
            log.info("response = {}", response);
        } catch (IOException e) {
            log.error("Slack webhook Error", e);
        }
    }

    private Payload getPayload(WebhookPayload payload) {
        return payload(p -> p.text(payload.getTitle()).attachments(payload.getAttachments()));
    }
}
