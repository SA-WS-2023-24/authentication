//package com.htwberlin.userservice.core.domain.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import java.util.UUID;
//
////@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
////@Entity
//@Getter
//@Setter
////@Table(name = "address")
//public class Address {
//
////    @Id
////    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//    private UUID userId;
//    private String street;
//    private String houseNumber;
//    private String zipCode;
//    private String city;
//    private String country;
//
//
////    @ManyToOne
////    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
////    @OnDelete(action = OnDeleteAction.CASCADE)
////    @JsonIgnore
//    private Address address;
//}
