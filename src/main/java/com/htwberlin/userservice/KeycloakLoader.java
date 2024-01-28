package com.htwberlin.userservice;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
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

  private final Keycloak keycloak;
  private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakLoader.class);

  public KeycloakLoader(Keycloak keycloak) {
    this.keycloak = keycloak;
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
    UserRepresentation user = this.setupUser();
    ClientRepresentation client = this.setupClient(kcClientId);

    this.keycloak.realms().create(realm);
    this.keycloak.realm(kcRealm).clients().create(client);
    this.keycloak.realm(kcRealm).users().create(user);
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
    RealmRepresentation realm = new RealmRepresentation();
    realm.setRealm(name);
    realm.setEnabled(true);
    realm.setRegistrationAllowed(true);
    realm.setRegistrationEmailAsUsername(true);
    realm.setVerifyEmail(true);
    realm.setResetPasswordAllowed(true);
    return realm;
  }

  private UserRepresentation setupUser() {
    UserRepresentation user = new UserRepresentation();
    CredentialRepresentation credentials = setupUserCredentials();
    user.setEmail("init@test.com");
    user.setEnabled(true);
    user.setFirstName("initial");
    user.setLastName("user");
    user.setEmailVerified(true);
    user.setCredentials(List.of(credentials));
    return user;
  }

  private CredentialRepresentation setupUserCredentials() {
    CredentialRepresentation credentials = new CredentialRepresentation();
    credentials.setType(CredentialRepresentation.PASSWORD);
    credentials.setValue("password");
    credentials.setTemporary(false);
    return credentials;
  }

  private ClientRepresentation setupClient(String name) {
    ClientRepresentation client = new ClientRepresentation();
    client.setClientId(name);
    client.setDirectAccessGrantsEnabled(true);
    client.setPublicClient(true);
    client.setRedirectUris(List.of("localhost:8081/user/access/*"));
    return client;
  }

  private boolean realmExists(String realmName) {
    LOGGER.debug("Checking if realm " + realmName + " exists");
    List<RealmRepresentation> realms = this.keycloak.realms().findAll();
    return realms.stream().anyMatch(realm -> realm.getRealm().equals(realmName));
  }
}
