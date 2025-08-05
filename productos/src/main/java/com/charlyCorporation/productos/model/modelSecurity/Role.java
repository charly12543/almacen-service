package com.charlyCorporation.productos.model.modelSecurity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;
    private String role;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "roles_permission", joinColumns = @JoinColumn(name = "roles_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissionList = new HashSet<>();



}
