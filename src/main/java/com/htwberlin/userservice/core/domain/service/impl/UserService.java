package com.htwberlin.userservice.core.domain.service.impl;

import com.htwberlin.userservice.core.domain.model.Address;
import com.htwberlin.userservice.core.domain.model.Role;
import com.htwberlin.userservice.core.domain.model.User;
import com.htwberlin.userservice.core.domain.model.UserDTO;
import com.htwberlin.userservice.core.domain.service.interfaces.IUserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Value("${keycloak.server-url}")
    private String kcServerUrl;

    @Value("${keycloak.realm}")
    private String kcRealm;

    @Value("${keycloak.client-id}")
    private String kcClientId;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private Keycloak keycloak;

    public UserService(Keycloak keycloak) {
      this.keycloak = keycloak;
    }

    @Override
    public JwtAuthenticationConverter getJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access/roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return authenticationConverter;
    }

    @Override
    public UserDTO getUser(String accessToken, String refreshToken) {
        UserRepresentation userRepresentation = this.getUserRepresentation();
        Address address = this.getAddress(userRepresentation);
        String email = userRepresentation.getEmail();
        String firstName = userRepresentation.getFirstName();
        String lastName = userRepresentation.getLastName();
        String roleStr = this.getRole();
        Role role = Role.valueOf(roleStr);

        UserDTO user = UserDTO.builder()
            .email(email)
            .firstName(firstName)
            .lastName(lastName)
            .role(role)
            .address(address)
            .build();

        return user;
    }

    private String getRole() {
        UserResource userResource = this.getUserResource();
        RoleMappingResource roleMappingResource = userResource.roles();
        List<RoleRepresentation> roles = roleMappingResource.realmLevel().listEffective();
        String role = roles.get(0).getName();
        if (role.equals("default-roles-pcpartshop")) role = roles.get(1).getName();
        return role;
    }


    @Override
    public String getUsername() {
        Authentication auth = this.getAuthentication();
        String username = auth.getName();
        return username;
    }

    @Override
    public String getCartId(HttpSession session) {
        String id;
        String username = this.getUsername();
        LOGGER.debug("Username: " + username);

        if (username.equals("anonymousUser")) id = session.getId();
        else id = this.getKeycloakId();

        return id;
    }

    @Override
    public void logoutUser(HttpSession session) {
        this.logoutKeycloakUser();
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

//    private OAuth2User getPrincipal() {
//        Authentication auth = this.getAuthentication();
//        OAuth2User principal = (OAuth2User) auth.getPrincipal();
//        return principal;
//    }

    private Jwt getPrincipal() {
        JwtAuthenticationToken auth = (JwtAuthenticationToken) this.getAuthentication();
        Jwt principal = (Jwt) auth.getPrincipal();
        return principal;
    }

    private String getKeycloakId() {
        String keycloakId;
        Jwt principal = this.getPrincipal();
        keycloakId = principal.getClaim("sub");
        LOGGER.debug("Keycloak ID: " + keycloakId);
        return keycloakId;
    }

    private UserResource getKCUserResourceById(String id) {
        RealmResource realmResource = this.getKCRealmResourceByName(kcRealm);
        UserResource userResource = realmResource.users().get(id);
        return userResource;
    }

    private UserResource getUserResource() {
        String id = this.getKeycloakId();
        UserResource userResource = this.getKCUserResourceById(id);
        return userResource;
    }

    private UserRepresentation getUserRepresentation() {
        UserResource userResource = this.getUserResource();
        UserRepresentation userRepresentation = userResource.toRepresentation();
        return userRepresentation;
    }

    private void logoutKeycloakUser() {
        UserResource user = this.getUserResource();
        user.logout();
    }

    @Override
    public Map<String, String> loginUser(User user) {
        String accessToken, refreshToken;

        Keycloak kc = KeycloakBuilder.builder()
            .serverUrl(kcServerUrl)
            .realm(kcRealm)
            .clientId(kcClientId)
            .username(user.getEmail())
            .password(user.getPassword())
            .grantType("password")
            .build();

        AccessTokenResponse tokenRes = kc.tokenManager().getAccessToken();
        accessToken = tokenRes.getToken();
        refreshToken = tokenRes.getRefreshToken();
        kc.close();

        return Map.of("access_token", accessToken, "refresh_token", refreshToken);
    }

    @Override
    public Map<String, String> setAddress(Address address) {
        UserResource userResource = this.getUserResource();
        UserRepresentation user = userResource.toRepresentation();

        Map<String, List<String>> addressAttributes = this.mapAddressToUserAttributes(address, user);
        user.setAttributes(addressAttributes);
        userResource.update(user);

        return address.toMap();
    }

    private Map<String, List<String>> mapAddressToUserAttributes(Address address, UserRepresentation user) {
        Map<String, String> addressMap = address.toMap();
        Map<String, List<String>> attributes = user.getAttributes();

        if(this.isUserAttributesEmpty(attributes)) {
            attributes = new HashMap<>();
        }

        for (Map.Entry<String, String> entry : addressMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            attributes.put(key, List.of(value));
        }

        return attributes;
    }

    private Address mapUserAttributesToAddress(Map<String, List<String>> attributes) {
        String street = attributes.get("street").get(0);
        String houseNumber = attributes.get("houseNumber").get(0);
        String zipCode = attributes.get("zipCode").get(0);
        String city = attributes.get("city").get(0);
        String country = attributes.get("country").get(0);
        Address address = new Address(street, houseNumber, zipCode, city, country);
        return address;
    }

    private boolean isUserAttributesEmpty(Map<String, List<String>> attributes) {
        return attributes == null;
    }

    @Override
    public void deleteAddress() {
        String id = this.getKeycloakId();
        UserResource userResource = this.getKCUserResourceById(id);
        UserRepresentation user = userResource.toRepresentation();
//        user.
    }

    private Address getAddress(UserRepresentation user) {
        Address address;
        Map<String, List<String>> attributes = user.getAttributes();

        if (this.isUserAttributesEmpty(attributes)) address = null;
        else address = this.mapUserAttributesToAddress(attributes);

        return address;
    }

    private RealmResource getKCRealmResourceByName(String realmName) {
        RealmResource realmResource = this.keycloak.realm(realmName);
        return realmResource;
    }

    private RealmRepresentation getRealmRepresentationByName(String realmName) {
        RealmResource realmResource = this.getKCRealmResourceByName(realmName);
        RealmRepresentation realm = realmResource.toRepresentation();
        return realm;
    }
}
