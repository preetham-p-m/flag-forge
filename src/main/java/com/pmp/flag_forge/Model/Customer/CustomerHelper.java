package com.pmp.flag_forge.Model.Customer;

public class CustomerHelper {

    public static Customer toEntity(CustomerDto customerDto) {
        return Customer.builder()
                .customerName(customerDto.getCustomerName())
                .build();
    }
}
