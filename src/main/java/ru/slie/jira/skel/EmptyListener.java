package ru.slie.jira.skel;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmptyListener implements InitializingBean, DisposableBean {
    private final EventPublisher eventPublisher;
    private static final Logger log = LoggerFactory.getLogger(EmptyListener.class);

    @Autowired
    public EmptyListener(@ComponentImport EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @Override
    public void destroy() {
        eventPublisher.unregister(this);
    }

    @Override
    public void afterPropertiesSet() {
        eventPublisher.register(this);
    }

    @EventListener
    public void onIssueEvent(final IssueEvent issueEvent) {
        log.info("Event listener fired: " + issueEvent.getIssue().getKey());
    }
}
