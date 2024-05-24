package kelompok1.KedaiIceCream.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class AuthenticationFailureHandlerUtil implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // Handle the authentication failure here
        // For example, you can set an error message in the session or redirect to a custom error page
        request.getSession().setAttribute("error_message", "Invalid username or password");
        response.sendRedirect("/auth/login");
    }
}