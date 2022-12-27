/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mballem.curso.jasper.spring.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Ballem
 */
@Entity
@Table(name = "niveis")
public class Nivel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_nivel")
    private Long id;

    private String nivel;

    private BigDecimal bonus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nivel")
    private List<Funcionario> funcionarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nivel nivel = (Nivel) o;

        return id != null ? id.equals(nivel.id) : nivel.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "com.mballem.curso.jasper.spring.entity.Nivel[ id=" + id + " ]";
    }
    
}
