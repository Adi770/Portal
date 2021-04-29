package com.lepa.portal.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER"),MODERATOR("MODERATOR"),REDAKTOR("REDAKTOR"),ADMIN("ADMIN"),BAN("BAN");

    public final String nameRole;

    Role(String name) {
        this.nameRole = name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_"+this.nameRole;
    }
}
