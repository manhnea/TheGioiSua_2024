package com.example.TheGioiSua_2024.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "userinvoice")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Userinvoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int status;

  @ManyToOne
  @JoinColumn(name = "userid")
  private User user;

  @ManyToOne
  @JoinColumn(name = "invoiceid")
  @JsonBackReference
  private Invoice invoice;


}
