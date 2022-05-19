package com.qriositylog.algorio.config.auth;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.qriositylog.algorio.config.auth.dto.OAuthAttributes;
import com.qriositylog.algorio.domain.user.Role;
import com.qriositylog.algorio.domain.user.User;
import com.qriositylog.algorio.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import org.springframework.stereotype.Service;

import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService {
    /* Replace login/oauth2/code/google controller since this is not available in my case */

    private final UserRepository userRepository;
    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new GsonFactory();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String CLIENT_ID;

    @Transactional
    public User addUser(String email, String name, String pictureUrl) {
        return User.builder()
                .name(name)
                .email(email)
                .picture(pictureUrl)
                .role(Role.USER)
                .build();
    }

    public void loadUser(String idTokenString) throws OAuth2AuthenticationException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                // if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(idTokenString);

            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            System.out.println("email: " + email);
            System.out.println("emailVerified: " + emailVerified);
            System.out.println("name: " + name);
            System.out.println("pictureUrl: " + pictureUrl);
            System.out.println("locale: " + locale);
            System.out.println("familyName: " + familyName);
            System.out.println("givenName: " + givenName);

            // Add or update user
            User user = userRepository.findByEmail(email).orElseGet(
                    () -> addUser(email, name, pictureUrl)
            );

        } catch (Exception e) {
            System.out.println("Invalid ID token.");
            System.out.println(e);
        }
/*        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(
                                user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()); */
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
