package com.palmlink.core.mail;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.palmlink.core.platform.scheduler.Job;
import com.palmlink.core.platform.scheduler.Scheduler;
import com.palmlink.core.util.TimeLength;

/**
 * @author Shihai.Fu
 */
public class MailSender {
    private final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private Scheduler scheduler;
    private final JavaMailSenderImpl sender = new JavaMailSenderImpl();

    public MailSender() {
        sender.setDefaultEncoding("UTF-8");
        sender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
    }

    public void send(Mail mail) {
        try {
            MimeMessage message = createMimeMessage(mail);
            logger.debug("start sending email");
            sender.send(message);
        } catch (Exception e) {
            throw new MailException(e);
        } finally {
            logger.debug("finish sending email");
        }
    }

    public void sendAsync(final Mail mail) {
        scheduler.triggerOnce(new Job() {
            @Override
            public void execute() throws Exception {
                send(mail);
            }
        });
    }

    private MimeMessage createMimeMessage(Mail mail) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(sender.createMimeMessage());
        logger.debug("subject={}", mail.getSubject());
        message.setSubject(mail.getSubject());
        logger.debug("from={}", mail.getFrom());
        message.setFrom(mail.getFrom(), mail.getNickName());
        String[] toAddresses = toAddressArray(mail.getToAddresses());
        logger.debug("to={}", Arrays.toString(toAddresses));
        message.setTo(toAddresses);
        message.setCc(toAddressArray(mail.getCCAddresses()));
        message.setBcc(toAddressArray(mail.getBCCAddresses()));
        message.setText(mail.getBody(), Mail.CONTENT_TYPE_HTML.equals(mail.getContentType()));
        message.setReplyTo(mail.getReplyTo());
        return message.getMimeMessage();
    }

    private String[] toAddressArray(List<String> addresses) {
        return addresses.toArray(new String[addresses.size()]);
    }

    public void setHost(String host) {
        if (!StringUtils.isBlank(host))
            sender.setHost(host);
    }

    public void setPort(Integer port) {
        if (port != null)
            sender.setPort(port);
    }

    public void setUsername(String username) {
        if (!StringUtils.isBlank(username)) {
            sender.setUsername(username);
            sender.getJavaMailProperties().put("mail.smtp.auth", "true");
        }
    }

    public void setPassword(String password) {
        if (!StringUtils.isBlank(password))
            sender.setPassword(password);
    }

    public void setTimeout(TimeLength timeout) {
        if (timeout != null)
            sender.getJavaMailProperties().put("mail.smtp.timeout", timeout.toMilliseconds());
    }

    @Inject
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
