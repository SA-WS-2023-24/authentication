package com.htwberlin.userservice.core.domain.model;

import lombok.*;

import java.util.Map;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Address {
  private String street;
  private String houseNumber;
  private String zipCode;
  private String city;
  private String country;

  public Map<String, String> toMap() {
    Map<String, String> address = new HashMap<>();
    address.put("street", this.street);
    address.put("houseNumber", this.houseNumber);
    address.put("zipCode", this.zipCode);
    address.put("city", this.city);
    address.put("country", this.country);
    return address;
  }
}
