package com.product.credit.data.changeless;

public final class RestrictionsData {

    private RestrictionsData() {
    }

    public static final String SERVICE = "/restrictions";
    public static final String GET_RESTRICTIONS = String.format("%s/{cpf}", SERVICE);
    public static final String CPF = "cpf";
}
