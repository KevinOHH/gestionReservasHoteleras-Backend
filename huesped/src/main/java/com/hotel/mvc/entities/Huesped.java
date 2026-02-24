package com.hotel.mvc.entities;

import com.hotel.mvc.enums.EstadoRegistro;
import com.hotel.mvc.enums.Nacionalidad;
import com.hotel.mvc.enums.TipoDocumento;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoDocumento documento; 
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Nacionalidad nacionalidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private EstadoRegistro estado;  
}