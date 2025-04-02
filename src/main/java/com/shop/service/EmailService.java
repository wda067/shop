package com.shop.service;

import com.shop.exception.EmailSendFailure;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger orderLogger = LoggerFactory.getLogger("OrderLogger");

    private final JavaMailSender mailSender;

    //이메일 전송은 네트워크 I/O 작업으로 시간이 걸릴 수 있으므로 비동기로 처리하는 것이 좋다.
    public void sendOrderConfirmation(String email, String orderName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("주문 완료 안내");
            helper.setText(orderName, true);

            mailSender.send(message);
            orderLogger.info("이메일 전송 성공");
        } catch (MessagingException e) {
            throw new EmailSendFailure();
        }
    }
}
