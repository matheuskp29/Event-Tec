package com.event.api.services;

import com.event.api.domain.entities.Address;
import com.event.api.domain.entities.Event;
import com.event.api.domain.exceptions.GenericException;
import com.event.api.domain.records.request.EventRequestDTO;
import com.event.api.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    public void createAddress(EventRequestDTO data, Event event) {
        try {
            Address address = Address.builder()
                    .city(data.city())
                    .uf(data.state())
                    .event(event)
                    .build();

            addressRepository.save(address);
        } catch (Exception e) {
            throw new GenericException("Error creating address: " + e.getMessage(), e);
        }
    }
}
