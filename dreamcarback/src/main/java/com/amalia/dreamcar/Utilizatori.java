package com.amalia.dreamcar;

import javax.persistence.*;

@Entity
@Table(name="UTILIZATORI")
public class Utilizatori {
    @Id

    @Column(name="USERNAME")
    private String username;

    @Column(name="PAROLA")
    private String parola;

    @Column(name="ROL")
    private Long rol;

    @Column(name="EMAIL")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;;
    }

    public Long getRol() {
        return rol;
    }

    public void setRol(Long rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
