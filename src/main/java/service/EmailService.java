package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.config.AsyncHelper;
import idb2camp.b2campjufrin.dto.request.EmailSenderRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @SneakyThrows
    public void sendCommonHtmlMessage(EmailSenderRequest request) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            Map<String, Object> thymeleafMap = new HashMap<>();
            thymeleafMap.put("recipientName", request.getRecipientName());
//            thymeleafMap.put("destinationEmail", request.destinationMail());
            thymeleafMap.put("message", request.getMessage());
            thymeleafMap.put("cardMap", request.getCardMap());

            context.setVariables(thymeleafMap);
            helper.setFrom("no_reply@gmail.com");
            helper.setTo(request.getDestinationMail());
            helper.setSubject(request.getSubject());

            String html = templateEngine.process("common-mail-template.html", context);
            helper.setText(html, true);

            log.info("Sending email: {} with html body: {}", request, html);

            AsyncHelper.executeAsync(() -> emailSender.send(message));
        } catch (MessagingException e) {
            log.error("[sendHtmlMessage] {}, {}", e, request.getDestinationMail());
        }
    }
}
