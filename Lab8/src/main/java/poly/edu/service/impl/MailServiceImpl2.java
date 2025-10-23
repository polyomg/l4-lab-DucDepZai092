package poly.edu.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import poly.edu.service.MailService2;

@Service("mailService2")
public class MailServiceImpl2 implements MailService2 {

    @Autowired
    JavaMailSender mailSender;

    // 🔹 Hàng đợi mail
    List<Mail> queue = new ArrayList<>();

    // 🔸 Gửi mail ngay
    @Override
    public void send(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            // 2.1 Người gửi
            helper.setFrom(mail.getFrom());
            helper.setReplyTo(mail.getFrom());

            // 2.2 Người nhận
            helper.setTo(mail.getTo());
            if (!isNullOrEmpty(mail.getCc())) helper.setCc(mail.getCc());
            if (!isNullOrEmpty(mail.getBcc())) helper.setBcc(mail.getBcc());

            // 2.3 Tiêu đề & nội dung
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true);

            // 2.4 File đính kèm (nếu có)
            String filenames = mail.getFilenames();
            if (!isNullOrEmpty(filenames)) {
                for (String filename : filenames.split("[,;]+")) {
                    File file = new File(filename.trim());
                    if (file.exists()) {
                        helper.addAttachment(file.getName(), file);
                    }
                }
            }

            // 3. Gửi mail
            mailSender.send(message);
            System.out.println("✅ Gửi mail thành công đến: " + mail.getTo());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isNullOrEmpty(String text) {
        return (text == null || text.trim().isEmpty());
    }

    // 🔹 Thêm mail vào hàng đợi
    @Override
    public void push(Mail mail) {
        queue.add(mail);
        System.out.println("📨 Đã thêm mail vào hàng đợi: " + mail.getTo());
    }

    // 🔹 Tự động gửi mail trong hàng đợi mỗi 0.5 giây
    @Scheduled(fixedDelay = 500)
    public void run() {
        while (!queue.isEmpty()) {
            try {
                this.send(queue.remove(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
