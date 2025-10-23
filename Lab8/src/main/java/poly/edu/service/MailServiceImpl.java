package poly.edu.service;

import java.io.File;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    @Override
    public void send(Mail mail) {
        try {
            // 1. Tạo Mail
            MimeMessage message = mailSender.createMimeMessage();

            // 2. Tạo đối tượng hỗ trợ ghi nội dung Mail
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            // 2.1. Ghi thông tin người gửi
            helper.setFrom(mail.getFrom());
            helper.setReplyTo(mail.getFrom());

            // 2.2. Ghi thông tin người nhận
            helper.setTo(mail.getTo());
            if (!isNullOrEmpty(mail.getCc())) {
                helper.setCc(mail.getCc());
            }
            if (!isNullOrEmpty(mail.getBcc())) {
                helper.setBcc(mail.getBcc());
            }

            // 2.3. Ghi tiêu đề và nội dung
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true); // true = hỗ trợ HTML

            // 2.4. Đính kèm file (nếu có)
            String filenames = mail.getFilenames();
            if (!isNullOrEmpty(filenames)) {
                for (String filename : filenames.split("[,;]+")) {
                    File file = new File(filename.trim());
                    helper.addAttachment(file.getName(), file);
                }
            }

            // 3. Gửi Mail
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Gửi mail thất bại: " + e.getMessage(), e);
        }
    }

    private boolean isNullOrEmpty(String text) {
        return (text == null || text.trim().isEmpty());
    }
}
