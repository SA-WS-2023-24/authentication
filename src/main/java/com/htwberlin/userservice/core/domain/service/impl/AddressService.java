package com.htwberlin.userservice.core.domain.service.impl;

import com.htwberlin.userservice.core.domain.model.Address;
import com.htwberlin.userservice.core.domain.service.interfaces.IAddressRepository;
import com.htwberlin.userservice.core.domain.service.interfaces.IAddressService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService implements IAddressService {

    private final IAddressRepository addressRepository;

    public AddressService(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Address address) {
        addressRepository.delete(address);
    }

    @Override
    public Iterable<Address> getAllAddressesForUser(UUID userId) {
        return addressRepository.findAllByUserId(userId);
    }
}
