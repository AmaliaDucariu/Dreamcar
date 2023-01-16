package com.amalia.dreamcar;


import javax.persistence.*;

@Entity
@Table(name="ROLURI")
public class Roluri {

    @Id

    @Column(name="ROL")
    private Long rol;

    @Column(name="DESCRIERE")
    private String descriere;

    public Long getRol() {
        return rol;
    }

    public void setRol(Long rol) {
        this.rol = rol;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
