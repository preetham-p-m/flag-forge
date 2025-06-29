package com.pmp.flag_forge.Model.User;

import com.pmp.flag_forge.Model.Customer.Customer;

public class UserHelper {

    public static User toEntity(UserDto userDto, Customer customer) {
        return User.builder()
                .customer(customer)
                .fullName(userDto.getFullName())
                .userName(userDto.getUserName())
                .build();
    }
}
