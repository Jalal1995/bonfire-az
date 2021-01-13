package com.bonfire.az.bonfireaz.model.sec.oauth;

import com.bonfire.az.bonfireaz.jwt.JwtService;
import com.bonfire.az.bonfireaz.model.entity.XUser;
import com.bonfire.az.bonfireaz.model.response.UserRs;
import com.bonfire.az.bonfireaz.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.bonfire.az.bonfireaz.jwt.Const.PREFIX;

@Component
@RequiredArgsConstructor
@Log4j2
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ObjectMapper mapper;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) auth.getPrincipal();
        String email = oAuth2User.getEmail();
        String fullName = oAuth2User.getFullName();
        log.info("Customer's email: {}", email);
        log.info("Customer's fullname: {}", fullName);

        Optional<XUser> opUser = userService.findOpUserByEmail(email);
        XUser xuser = opUser
                .orElseGet(() -> userService.register(email, "", fullName));
        String token = jwtService.generateToken(xuser.getId());
        UserRs user = modelMapper.map(xuser, UserRs.class);

        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("user", user);
        map.put("token", PREFIX + token);
        resp.getWriter().write(mapper.writeValueAsString(map));
      //  resp.sendRedirect("http://www.youtube.com");
    }
}
