package poly.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 🔹 Bật chức năng lập lịch (Scheduled Tasks)
public class Java5Lab8Application {
    public static void main(String[] args) {
        SpringApplication.run(Java5Lab8Application.class, args);
    }
}
