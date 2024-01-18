package com.htwberlin.userservice.core.domain.service.interfaces;

import com.htwberlin.userservice.core.domain.model.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IAddressRepository extends CrudRepository<Address, UUID> {

    Iterable<Address> findAllByUserId(UUID userId);

}
