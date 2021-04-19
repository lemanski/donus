package com.teste.donus.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String nomeCompleto;

}
