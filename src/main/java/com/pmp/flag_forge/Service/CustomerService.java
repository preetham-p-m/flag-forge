package com.pmp.flag_forge.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pmp.flag_forge.Exception.Error.FlagForgeNotFoundException;
import com.pmp.flag_forge.Model.Customer.Customer;
import com.pmp.flag_forge.Model.Customer.CustomerDto;
import com.pmp.flag_forge.Model.Customer.CustomerHelper;
import com.pmp.flag_forge.Repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer create(CustomerDto customerDto) {
        var customer = CustomerHelper.toEntity(customerDto);
        return customerRepository.save(customer);
    }

    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }

    public Customer getById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new FlagForgeNotFoundException("User not found for userId " + id));
    }
}
