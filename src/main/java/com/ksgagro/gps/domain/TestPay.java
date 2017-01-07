package com.ksgagro.gps.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by kma on 29.11.2016.
 */
public class TestPay {
    private Long id;
    private String name;
    private String identificationNumber;
    private Date conclusionContractDate;
    private Integer contractTerm;
    private String kadastrNumber;
    private Double area;
    private String renter;
    private List<Coordinates> coordinateses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Date getConclusionContractDate() {
        return conclusionContractDate;
    }

    public void setConclusionContractDate(Date conclusionContractDate) {
        this.conclusionContractDate = conclusionContractDate;
    }

    public Integer getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(Integer contractTerm) {
        this.contractTerm = contractTerm;
    }

    public String getKadastrNumber() {
        return kadastrNumber;
    }

    public void setKadastrNumber(String kadastrNumber) {
        this.kadastrNumber = kadastrNumber;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    public List<Coordinates> getCoordinateses() {
        return coordinateses;
    }

    public void setCoordinateses(List<Coordinates> coordinateses) {
        this.coordinateses = coordinateses;
    }
}
