package com.qriositylog.algorio.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final CustomOAuth2UserService userService;

    @PostMapping("/auth/google")
    public void loadUser(@RequestBody String idTokenString) { userService.loadUser(idTokenString); }
}
