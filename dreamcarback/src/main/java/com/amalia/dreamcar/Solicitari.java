package com.amalia.dreamcar;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="SOLICITARI")
public class Solicitari {

    @Id
    @Column(name="SOLICITARE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long solicitare;

    @Column(name="DATA_LANSARE")
    private Date dataLansare;

    @Column(name="PRET_TINTA")
    private Float pretTinta;

    @Column(name="TIMEOUT")
    private Long timeout;

    @Column(name="TIMEOUT_UOM")
    private String timeoutUom;

    @Column(name="PRODUS")
    private String produs;

    @Column(name="NR_BUCATI")
    private Float nrBucati;

    @Column(name="DESCRIERE")
    private String descriere;

    public Float getPretTinta() {
        return pretTinta;
    }

    public void setPretTinta(Float pretTinta) {
        this.pretTinta = pretTinta;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getTimeoutUom() {
        return timeoutUom;
    }

    public void setTimeoutUom(String timeoutUom) {
        this.timeoutUom = timeoutUom;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Long getSolicitare() {
        return solicitare;
    }

    public void setSolicitare(Long solicitare) {
        this.solicitare = solicitare;
    }

    public Date getDataLansare() {
        return dataLansare;
    }

    public void setDataLansare(Date dataLansare) {
        this.dataLansare = dataLansare;
    }

    public String getProdus() {
        return produs;
    }

    public void setProdus(String produs) {
        this.produs = produs;
    }

    public Float getNrBucati() {
        return nrBucati;
    }

    public void setNrBucati(Float nrBucati) {
        this.nrBucati = nrBucati;
    }
}
