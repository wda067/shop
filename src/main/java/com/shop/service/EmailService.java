package com.shop.service;

import com.shop.domain.Member;
import com.shop.domain.order.Order;
import com.shop.dto.request.PaymentRequest;
import com.shop.exception.EmailSendFailure;
import com.shop.exception.MemberNotFound;
import com.shop.repository.member.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.text.NumberFormat;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableRetry
public class EmailService {

    private final JavaMailSender mailSender;
    private final MemberRepository memberRepository;
    private final TemplateEngine templateEngine;
    private final ApplicationEventPublisher eventPublisher;

    /*
    이메일 전송은 네트워크 I/O 작업으로 시간이 걸릴 수 있으므로 비동기로 처리하는 것이 좋다.
    @Async는 메서드를 별도의 스레드에서 실행하도록 하여 비동기 처리를 한다.
     */
    @Async("asyncExecutor")
    public void send(Long memberId, PaymentRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Member member = memberRepository.findById(memberId)
                    .orElseThrow(MemberNotFound::new);

            helper.setTo(member.getEmail());
            helper.setSubject("결제 완료 안내");
            String emailContent = generateEmailContent(request);
            helper.setText(emailContent, true);

            mailSender.send(message);
            log.info("이메일 전송 성공");
        } catch (MessagingException e) {
            throw new EmailSendFailure();
        }
    }

    private String generateEmailContent(PaymentRequest request) {
        Context context = new Context();
        context.setVariable("orderId", request.getOrderId());
        long amount = Long.parseLong(request.getAmount());
        String formattedAmount = NumberFormat.getInstance(Locale.KOREA).format(amount);
        context.setVariable("amount", formattedAmount);
        return templateEngine.process("payment-complete-email", context);
    }

    @Retryable(
            retryFor = {EmailSendFailure.class},
            backoff = @Backoff(delay = 2000)
    )
    public void sendOrderConfirmation(String email, String orderName) {
        try {
            log.info("주문 완료 이메일 전송 요청");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("주문 완료 안내");
            helper.setText(orderName, true);

            mailSender.send(message);
            log.info("이메일 전송 성공");
        } catch (MessagingException e) {
            throw new EmailSendFailure();
        }
    }

    //@Async("asyncExecutor")
    //@Retryable(
    //        retryFor = {EmailSendFailure.class},
    //        backoff = @Backoff(delay = 2000)
    //)
    //public void sendOrderConfirmation(Member member, Order order) {
    //    try {
    //        log.info("주문 완료 이메일 전송 요청");
    //        MimeMessage message = mailSender.createMimeMessage();
    //        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    //
    //        helper.setTo(member.getEmail());
    //        helper.setSubject("주문 완료 안내");
    //        helper.setText(order.getOrderName(), true);
    //
    //        throw new EmailSendFailure(); // 테스트를 위한 예외 발생
    //        // mailSender.send(message);
    //        // log.info("이메일 전송 성공");
    //    } catch (Exception e) {
    //        eventPublisher.publishEvent(new EmailSendFailureEvent()); // 예외 이벤트 발행
    //    }
    //}
}
