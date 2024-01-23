//package com.htwberlin.userservice.user.controller;
//
//import com.htwberlin.userservice.core.domain.model.Address;
//import com.htwberlin.userservice.core.domain.service.interfaces.IAddressService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/v1/")
//public class AddressController {
//    private final IAddressService addressService;
//
//    public AddressController(IAddressService addressService) {
//        this.addressService = addressService;
//    }
//
//    @PostMapping("/address")
//    public @ResponseBody Address create(@RequestBody Address address) {
//        return addressService.createAddress(address);
//    }
//
//    @GetMapping("/addresses/{userId}")
//    public @ResponseBody Iterable<Address> getAllAddresss(@PathVariable UUID productId) {
//        return addressService.getAllAddressesForUser(productId);
//    }
//
//    @DeleteMapping("/address/{id}")
//    public @ResponseBody void deleteAddress(@RequestBody Address address) {
//        addressService.deleteAddress(address);
//    }
//}
