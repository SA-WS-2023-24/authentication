//package com.htwberlin.userservice;
//
//import com.htwberlin.userservice.core.domain.config.UserConfigurationProperties;
//import com.htwberlin.userservice.core.domain.model.User;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.representations.idm.ClientRepresentation;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.RealmRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class KeycloakLoader implements CommandLineRunner {
//  private static final String REALM_NAME = "pcpartshop";
//  private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakLoader.class);
//  private final Keycloak keycloak;
//  private UserConfigurationProperties userConfigurationProperties;
//
//  @Autowired
//  public KeycloakLoader(Keycloak keycloak) {
//    this.keycloak = keycloak;
//  }
//
//  @Override
//  public void run(String... args) throws Exception {
//        LOGGER.info("Initializing Keycloak...");
//
//        try {
//          this.keycloak.realms().findAll();
//          LOGGER.info("Successful connected to keycloak");
//        } catch (Exception e) {
//          LOGGER.error("Could not connect to keycloak. Please check your configuration", e);
//          return;
//        }
//
//        LOGGER.debug("Check if Realm exits...");
//
//        Optional<RealmRepresentation> representationOptional = keycloak.realms().findAll().stream()
//            .filter(r -> r.getRealm().equals(REALM_NAME))
//            .findAny();
//
//        if (representationOptional.isPresent()) {
//          LOGGER.info("Realm exists. Stop setup");
//          return;
//        }
//
//        LOGGER.info("Realm does not exist. Start setup");
//        RealmRepresentation realmRepresentation = new RealmRepresentation();
//        realmRepresentation.setRealm(REALM_NAME);
//        realmRepresentation.setEnabled(true);
//        realmRepresentation.setRegistrationAllowed(true);
//
//        // Client
//        ClientRepresentation clientRepresentation = new ClientRepresentation();
//        clientRepresentation.setClientId("spring-backend");
//        clientRepresentation.setDirectAccessGrantsEnabled(true);
//        clientRepresentation.setPublicClient(true);
//        List<ClientRepresentation> clients = new ArrayList<>();
//        clients.add(clientRepresentation);
//        realmRepresentation.setClients(clients);
//
//        // setup Users
//        List<UserRepresentation> userList = new ArrayList<>();
//        for (User userProps : userConfigurationProperties.getCredentials()) {
//
//          UserRepresentation userRepresentation = getUserRepresentation(userProps);
//
//          userList.add(userRepresentation);
//        }
//        realmRepresentation.setUsers(userList);
//
//        keycloak.realms().create(realmRepresentation);
//
//        LOGGER.info("Keycloak setup finished");
//      }
//
//  private static UserRepresentation getUserRepresentation(User userProps) {
//    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
//    credentialRepresentation.setValue(userProps.getPassword());
//    UserRepresentation userRepresentation = new UserRepresentation();
//    userRepresentation.setUsername(userProps.getUsername());
//    List<CredentialRepresentation> credentials = new ArrayList<>();
//    credentials.add(credentialRepresentation);
//    userRepresentation.setCredentials(credentials);
//    userRepresentation.setEnabled(true);
//    List<String> realmRoles = new ArrayList<>();
//    realmRoles.add(userProps.getRole().name());
//    userRepresentation.setRealmRoles(realmRoles);
//    return userRepresentation;
//  }
//}