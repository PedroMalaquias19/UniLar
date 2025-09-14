package com.pedro.UniLar.security.auth;

public record RegisterRequest(String firstName, String lastName, String email, String password, String NIF, String address, String contact) {
}