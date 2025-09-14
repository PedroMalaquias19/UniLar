package com.pedro.UniLar.security.auth;

public record RegisterRequest(String nome, String sobrenome, String email, String password, String NIF, String telefone) {
}