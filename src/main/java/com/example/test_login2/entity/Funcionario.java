package com.example.test_login2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Funcionarios")
public class Funcionario
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            joinColumns={@JoinColumn(name="Funcionarios_id", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="Roles_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();

}