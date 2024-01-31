package com.htwberlin.userservice;

import com.htwberlin.userservice.core.domain.config.UserConfigurationProperties;
import com.htwberlin.userservice.core.domain.model.User;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
public class KeycloakLoader implements CommandLineRunner {

  @Value("${keycloak.server-url}")
  private String kcServerUrl;

  @Value("${keycloak.realm}")
  private String kcRealm;

  @Value("${keycloak.client-id}")
  private String kcClientId;

  @Value("${keycloak.redirect-uri}")
  private String kcRedirectUri;

  @Value("${roles.default-user-role}")
  private String userRole;

  @Value("${roles.default-admin-role}")
  private String adminRole;

  private UserConfigurationProperties userConfigurationProperties;

  private final Keycloak keycloak;
  private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakLoader.class);

  public KeycloakLoader(Keycloak keycloak, UserConfigurationProperties userConfigurationProperties) {
    this.keycloak = keycloak;
    this.userConfigurationProperties = userConfigurationProperties;
  }

  @Override
  public void run(String... args) {
    this.connectToKeycloak();
    if (this.realmExists(kcRealm)) {
      LOGGER.info("Realm exists. Stopping setup...");
    } else {
      LOGGER.info("Realm does not exist. Setting up...");
      this.setupKeycloak();
      LOGGER.info("Setup complete.");
    }
  }

  private void setupKeycloak() {
    RealmRepresentation realm = this.setupRealm(kcRealm);
    ClientRepresentation client = this.setupClient(kcClientId, List.of(kcRedirectUri));

    this.keycloak.realms().create(realm);
    this.keycloak.realm(kcRealm).clients().create(client);
    this.setupRoles(realm);

    this.setupInitialUsers(realm);
  }

  private void setupInitialUsers(RealmRepresentation realm) {
    LOGGER.debug("Setting up initial users");
    Response response;
    UserRepresentation user;
    String userId;
    List<User> credentials = this.userConfigurationProperties.getCredentials();
    for (User userProps : credentials) {
      user = this.setupUser(userProps);
      response = this.keycloak.realm(kcRealm).users().create(user);
      userId = CreatedResponseUtil.getCreatedId(response);
      this.keycloak.realm(realm.getRealm()).users().get(userId).roles().realmLevel().add(
          List.of(this.keycloak.realm(kcRealm).roles().get(userProps.getRole().name()).toRepresentation())
      );
    }
  }

  private void setupRoles(RealmRepresentation realm) {
    LOGGER.debug("Setting up roles");
    RoleRepresentation role;
    role = this.setupRole(userRole);
    this.keycloak.realm(kcRealm).roles().create(role);
    realm.setDefaultRole(role);
    role = this.setupRole(adminRole);
    this.keycloak.realm(kcRealm).roles().create(role);
    this.keycloak.realm(realm.getRealm()).update(realm);
    }

  private void connectToKeycloak() {
    int responseCode;
    boolean isReachable = false;
    while (!isReachable) {
      try {
        LOGGER.info("Attempting to connect to Keycloak server at: " + kcServerUrl);
        URL url = new URL(kcServerUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        responseCode = connection.getResponseCode();
        if (responseCode == 200) {
          isReachable = true;
        } else {
          LOGGER.error("Keycloak server is not reachable. Response code: " + responseCode);
          LOGGER.info("Retrying in a second...");
          Thread.sleep(1000);
        }
      } catch (IOException | InterruptedException e) {
        LOGGER.error("Keycloak server is not reachable. " + e.getMessage());
        LOGGER.info("Retrying in 5 seconds...");
        try {
          Thread.sleep(5000);
        } catch (InterruptedException ex) {
          LOGGER.error("Interrupted while waiting to retry connection to Keycloak server.");
          LOGGER.info("Closing thread");
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  private RealmRepresentation setupRealm(String name) {
    LOGGER.debug("Setting up realm " + name);
    RealmRepresentation realm = new RealmRepresentation();
    realm.setRealm(name);
    realm.setEnabled(true);
    realm.setRegistrationAllowed(true);
    realm.setRegistrationEmailAsUsername(true);
    realm.setVerifyEmail(false);
    realm.setResetPasswordAllowed(true);
    return realm;
  }

  private UserRepresentation setupUser(User userProps) {
    LOGGER.debug("Setting up user " + userProps.getEmail() + " with role " + userProps.getRole().name());
    UserRepresentation user = new UserRepresentation();
    CredentialRepresentation credentials = setupUserCredentials(userProps.getPassword());
    user.setEmail(userProps.getEmail());
    user.setEnabled(true);
    user.setFirstName(userProps.getFirstName());
    user.setLastName(userProps.getLastName());
    user.setEmailVerified(true);
    user.setCredentials(List.of(credentials));
    return user;
  }

  private CredentialRepresentation setupUserCredentials(String value) {
    CredentialRepresentation credentials = new CredentialRepresentation();
    credentials.setType(CredentialRepresentation.PASSWORD);
    credentials.setValue(value);
    credentials.setTemporary(false);
    return credentials;
  }

  private ClientRepresentation setupClient(String name, List<String> uris) {
    LOGGER.debug("Setting up client " + name + " with redirect uris " + uris.get(0));
    ClientRepresentation client = new ClientRepresentation();
    client.setClientId(name);
    client.setDirectAccessGrantsEnabled(true);
    client.setPublicClient(true);
    client.setRedirectUris(uris);
    client.setFrontchannelLogout(false);
    return client;
  }

  private RoleRepresentation setupRole(String name) {
    LOGGER.debug("Setting up role " + name);
    RoleRepresentation role = new RoleRepresentation();
    role.setName(name);
    role.setDescription("Default role for " + name);
    role.setContainerId(kcRealm);
    return role;
  }

  private boolean realmExists(String realmName) {
    LOGGER.debug("Checking if realm " + realmName + " exists");
    List<RealmRepresentation> realms = this.keycloak.realms().findAll();
    return realms.stream().anyMatch(realm -> realm.getRealm().equals(realmName));
  }
}
