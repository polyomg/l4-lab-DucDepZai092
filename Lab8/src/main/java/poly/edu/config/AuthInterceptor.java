package poly.edu.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import poly.edu.entity.Account;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        String qs = request.getQueryString();
        String fullUri = qs == null ? uri : uri + "?" + qs;

        Account user = (Account) session.getAttribute("user");
        // Yêu cầu đăng nhập cho các đường dẫn nhất định
        if (uri.startsWith("/account/change-password") ||
            uri.startsWith("/account/edit-profile") ||
            uri.startsWith("/order/")) {
            if (user == null) { // chưa đăng nhập
                session.setAttribute("securityUri", fullUri);
                response.sendRedirect("/auth/login");
                return false;
            }
        }

        // Yêu cầu vai trò admin cho /admin/** trừ /admin/home/index
        if (uri.startsWith("/admin") && !uri.startsWith("/admin/home/index")) {
            if (user == null || !user.isAdmin()) { // không phải admin
                session.setAttribute("securityUri", fullUri);
                response.sendRedirect("/auth/login");
                return false;
            }
        }

        return true;
    }
}
