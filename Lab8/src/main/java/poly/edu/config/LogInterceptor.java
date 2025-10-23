package poly.edu.config;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import poly.edu.entity.Account;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        Account user = null;
        if (session != null) {
            Object u = session.getAttribute("user");
            if (u instanceof Account) user = (Account) u;
        }
        String uri = request.getRequestURI();
        String who = (user != null) ? user.getFullname() + " (" + user.getUsername() + ")" : "anonymous";
        LocalDateTime now = LocalDateTime.now();
        // Ghi log: URI, thời gian, họ tên
        log.info("[SECURED PAGE ACCESS] uri='{}', time='{}', user='{}'", uri, now, who);
        return true;
    }
}
