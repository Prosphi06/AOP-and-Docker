package com.aop.interceptors.demo.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "customer")
public class CustomerEntity {

   @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name="CustomerID")
    private Long customerId;
    @Column(name="FirstName")
    private String firstName;
    @Column(name="LastName")
    private String lastName;
    @Column(name="EmailID")
    private String emailId;
    @Column(name="Phone")
    private String phone;
    @Column(name="WhatsappNo")
    private String whatsAppNo;
    @Column(name="DateofBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthdate;

}
