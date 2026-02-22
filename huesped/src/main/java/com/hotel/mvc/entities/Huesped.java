package com.hotel.mvc.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "huespedes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 10)
    private String telefono;

    @Column(nullable = false, length = 50)
    private String documento;

    @Column(nullable = false, length = 50)
    private String nacionalidad;

    @Column(nullable = false, length = 10)
    private String estado;
}