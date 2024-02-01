package com.htwberlin.userservice.core.domain.service.impl;

import com.htwberlin.userservice.core.domain.model.Address;
import com.htwberlin.userservice.core.domain.model.User;
import com.htwberlin.userservice.core.domain.service.interfaces.IUserService;

import jakarta.servlet.http.HttpSession;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {
    private Keycloak keycloak;

    public UserService(Keycloak keycloak) {
      this.keycloak = keycloak;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public String getUsername() {
        Authentication auth = this.getAuthentication();
        String username = auth.getName();
        return username;
    }

    @Override
    public Map<String, String> getCartId(HttpSession session) {
        String id;
        String username = this.getUsername();

        if (username == null) id = session.getId();
        else id = this.getKeycloakId();

        return Map.of("cartId", id);
    }

    @Override
    public void logoutUser(HttpSession session, String realm) {
        this.logoutKeycloakUser(realm);
        session.invalidate();
        SecurityContextHolder.clearContext();
    }

    private SecurityContext getSecurityContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context;
    }

    private Authentication getAuthentication() {
        Authentication auth = this.getSecurityContext().getAuthentication();
        return auth;
    }

    private OAuth2User getOAuth2User() {
        Authentication auth = this.getAuthentication();
        OAuth2User user = (OAuth2User) auth.getPrincipal();
        return user;
    }

    private String getKeycloakId() {
        OAuth2User user = this.getOAuth2User();
        String keycloakId = user.getAttribute("sub");
        return keycloakId;
    }

    private UserResource getKCUserResourceById(String realm, String id) {
        RealmResource realmResource = this.getKCRealmResourceByName(realm);
        UserResource userResource = realmResource.users().get(id);
        return userResource;
    }

    private void logoutKeycloakUser(String realm) {
        String id = this.getKeycloakId();
        UserResource user = this.getKCUserResourceById(realm, id);
        user.logout();
    }

    @Override
    public void loginUser(String email, String password) {
        String kcServerUrl = "tmp";
        String kcRealm = "tmp";
        String kcClientId = "tmp";
        String token;
        Keycloak loginKeycloak = KeycloakBuilder.builder()
            .serverUrl(kcServerUrl)
            .realm(kcRealm)
            .clientId(kcClientId)
            .username(email)
            .password(password)
            .grantType("password")
            .build();
        token = loginKeycloak.tokenManager().getAccessToken().getToken();
        loginKeycloak.close();
        SecurityContext context = this.getSecurityContext();
        context.setAuthentication(null);
    }

    @Override
    public Map<String, String> setAddress(Address address, String realm) {
        String id = this.getKeycloakId();
        UserResource userResource = this.getKCUserResourceById(realm, id);
        UserRepresentation user = userResource.toRepresentation();

        Map<String, List<String>> updatedAttributes = this.mapAddressToUserAttributes(address, user);
        user.setAttributes(updatedAttributes);
        userResource.update(user);

        return address.toMap();
    }

    private Map<String, List<String>> mapAddressToUserAttributes(Address address, UserRepresentation user) {
        Map<String, String> addressMap = address.toMap();
        Map<String, List<String>> attributes = user.getAttributes();

        if (attributes == null) {
            attributes = new HashMap<>();
        }

        for (Map.Entry<String, String> entry : addressMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            attributes.put(key, List.of(value));
        }

        return attributes;
    }

    @Override
    public void deleteAddress(Address adress) {

    }

    private RealmResource getKCRealmResourceByName(String realmName) {
        RealmResource realmResource = this.keycloak.realm(realmName);
        return realmResource;
    }
}
