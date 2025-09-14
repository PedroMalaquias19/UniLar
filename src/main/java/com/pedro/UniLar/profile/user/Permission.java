package com.pedro.UniLar.profile.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    DONATION_READ("donation:read"),
    DONATION_UPDATE("donation:update"),
    DONATION_CREATE("donation:create"),
    DONATION_DELETE("donation:delete"),

    COMPANY_READ("company:read"),
    COMPANY_UPDATE("company:update"),
    COMPANY_CREATE("company:create"),
    COMPANY_DELETE("company:delete"),

    CONTENT_READ("content:read"),
    CONTENT_UPDATE("content:update"),
    CONTENT_CREATE("content:create"),
    CONTENT_DELETE("content:delete");

    private final String permission;
}
