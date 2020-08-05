package com.davv1d.customerevents.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Email {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "email", length = 30, nullable = false)
    private String email;

    public Email(String email) {
        this.email = email;
    }
}
