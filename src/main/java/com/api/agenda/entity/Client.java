package com.api.agenda.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "client")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {
    private String phone;
}