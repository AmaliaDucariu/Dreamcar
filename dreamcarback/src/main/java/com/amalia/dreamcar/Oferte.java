package com.amalia.dreamcar;


import javax.persistence.*;

@Entity
@Table(name="OFERTE")
public class Oferte {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOferta;

    @Column(name="SOLICITARE")
    private Long solicitare;

    @Column(name="NR_BUCATI")
    private Float nrBucati;

    @Column(name="PRET")
    private Float pret;

    @Column(name="USERNAME")
    private String username;

    public Long getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Long idOferta) {
        this.idOferta = idOferta;
    }

    public Long getSolicitare() {
        return solicitare;
    }

    public void setSolicitare(Long solicitare) {
        this.solicitare = solicitare;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getNrBucati() {
        return nrBucati;
    }

    public void setNrBucati(Float nrBucati) {
        this.nrBucati = nrBucati;
    }

    public Float getPret() {
        return pret;
    }

    public void setPret(Float pret) {
        this.pret = pret;
    }

}
