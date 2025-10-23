package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import poly.edu.service.MailService;

@Controller
public class MailController {

    @Autowired
    MailService mailService;

    @ResponseBody
    @RequestMapping("/mail/send")
    public String send() {
        try {
            mailService.send("receiver@gmail.com", "🛍 WebShop Test", "Chào bạn! Đây là email test từ Spring Boot.");
            return "✅ Mail của bạn đã được gửi đi!";
        } catch (Exception e) {
            return "❌ Gửi mail thất bại: " + e.getMessage();
        }
    }
}
