package poly.edu.service;

import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;

public interface MailService2 {

    @Data
    @Builder
    public static class Mail {
        @Default
        private String from = "WebShop <web-shop@gmail.com>";
        private String to, cc, bcc, subject, body, filenames;
    }

    // 🔸 Gửi ngay lập tức
    void send(Mail mail);

    default void send(String to, String subject, String body) {
        Mail mail = Mail.builder()
                .to(to)
                .subject(subject)
                .body(body)
                .build();
        this.send(mail);
    }

    // 🔹 HÀNG ĐỢI (push)
    void push(Mail mail);

    default void push(String to, String subject, String body) {
        this.push(Mail.builder()
                .to(to)
                .subject(subject)
                .body(body)
                .build());
    }
}
