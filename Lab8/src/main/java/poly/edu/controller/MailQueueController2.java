package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import poly.edu.service.MailService2;

@Controller
public class MailQueueController2 {

    @Autowired
    MailService2 mailService2;

    @ResponseBody
    @RequestMapping("/mail2/queue")
    public String sendMailQueue() {
        mailService2.push("receiver@gmail.com", "Lab8 - Bài 2 Queue Demo",
                "Nội dung: Mail này được xếp hàng đợi và sẽ gửi nền bằng @Scheduled");
        return "✅ Mail của bạn đã được **xếp vào hàng đợi (bài 2)**!";
    }
}
