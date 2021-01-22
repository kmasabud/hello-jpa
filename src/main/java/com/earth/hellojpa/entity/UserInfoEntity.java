package com.earth.hellojpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "user_info")
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String tell;
    private String sex;
}
