package com.pmp.flag_forge.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmp.flag_forge.Model.User.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
