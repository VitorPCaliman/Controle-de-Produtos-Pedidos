package com.controle.mspedidos.exception;

public class ProdutoNotFoundException extends BusinessException {
    public ProdutoNotFoundException(String message) {
        super(message);
    }
}
