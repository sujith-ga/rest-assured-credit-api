package com.product.credit.data.changeless;

public enum SimulationErrorsData {

    ERRORS_AMOUNT_GREATER("errors.amount", "Amount must be equal or greater than $ 1.000"),
    ERRORS_AMOUNT_LESS("errors.amount", "Amount must be equal or less than than $ 40.000"),
    ERRORS_INSTALLMENTS_GREATER("errors.installments", "Installments must be equal or greater than 2"),
    ERRORS_INSTALLMENTS_LESS("errors.installments", "Installments must be equal or less than 48"),
    ERRORS_EMAIL("errors.email", "E-mail must be valid"),
    ERRORS_CPF("errors.cpf", "CPF cannot be empty"),
    ERRORS_NAME("errors.name", "Name cannot be empty");

    public final String key;
    public final String message;

    SimulationErrorsData(String key, String message) {
        this.key = key;
        this.message = message;
    }
}
