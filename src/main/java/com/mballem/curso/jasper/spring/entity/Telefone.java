/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mballem.curso.jasper.spring.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Ballem
 */
@Entity
@Table(name = "telefones")
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_telefone")
    private Long id;

    private String numero;

    @Column(name = "tipo_fone")
    private String tipoFone;

    @JoinColumn(name = "id_funcionario", referencedColumnName = "id_funcionario")
    @ManyToOne
    private Funcionario funcionario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoFone() {
        return tipoFone;
    }

    public void setTipoFone(String tipoFone) {
        this.tipoFone = tipoFone;
    }

    public Funcionario getFuncionarios() {
        return funcionario;
    }

    public void setFuncionarios(Funcionario funcionario) {
        this.funcionario = funcionario;
    }


    @Override
    public String toString() {
        return "com.mballem.curso.jasper.spring.entity.Telefone[ id=" + id + " ]";
    }
    
}
