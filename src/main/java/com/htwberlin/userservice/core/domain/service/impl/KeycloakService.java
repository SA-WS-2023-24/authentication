package com.htwberlin.userservice.core.domain.service.impl;

import com.htwberlin.userservice.core.domain.service.interfaces.IKeycloakService;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class KeycloakService implements IKeycloakService {

  private static final String REALM_NAME = "pcpartshop";
  private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakService.class);

}
