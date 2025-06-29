package com.pmp.flag_forge.Model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false)
    private UUID id;

    @NotEmpty
    private String customerName;
}
