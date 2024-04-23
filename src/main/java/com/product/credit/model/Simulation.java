package com.product.credit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Objects;

public class Simulation {

    @JsonIgnore
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private BigDecimal amount;
    private Integer installments;
    private Boolean insurance;

    public Simulation(String name, String cpf, String email, BigDecimal amount, Integer installments, Boolean insurance) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.amount = amount;
        this.installments = installments;
        this.insurance = insurance;
    }

    public Simulation() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getEmail() {
        return this.email;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Integer getInstallments() {
        return this.installments;
    }

    public Boolean getInsurance() {
        return this.insurance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public void setInsurance(Boolean insurance) {
        this.insurance = insurance;
    }

    public String toString() {
        return "Simulation(id=" + this.getId() + ", name=" + this.getName() + ", cpf=" + this.getCpf() + ", email="
            + this.getEmail() + ", amount=" + this.getAmount() + ", installments=" + this.getInstallments()
            + ", insurance=" + this.getInsurance() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Simulation that = (Simulation) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(email, that.email) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(installments, that.installments) &&
            Objects.equals(insurance, that.insurance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cpf, email, amount, installments, insurance);
    }
}
