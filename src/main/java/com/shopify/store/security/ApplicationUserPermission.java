package com.shopify.store.security;

public enum ApplicationUserPermission {
    SELLER_READ("seller:read"),
    SELLER_WRITE("seller:write"),
    BUYER_READ("buyer:read"),
    BUYER_WRITE("buyer:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
