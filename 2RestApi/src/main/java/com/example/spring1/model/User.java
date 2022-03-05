package com.example.spring1.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Size(min = 2, max = 30, message = "firstName should be between 2 and 30 characters")
    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    @Size(min = 2, max = 30, message = "lastName should be between 2 and 30 characters")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "photo")
    private String photo;

}
