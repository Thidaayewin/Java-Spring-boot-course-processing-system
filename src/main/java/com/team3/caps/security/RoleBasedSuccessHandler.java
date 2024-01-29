package com.team3.caps.security;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleBasedSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // retrieve the user role

        String location;

        String role = SecurityUtil.getfirstAuthority(authentication);

        switch (role) {
            case "ROLE_Admin":
                location = "/admin/student/list";
                break;
            case "ROLE_Lecturer":
                location = "/lecturer";
                break;
            case "ROLE_Student":
                location = "/student";
                break;
            default:
                // throw an exception for unknown role type
                location = "/";
        }
        response.sendRedirect(location);
    }
}
