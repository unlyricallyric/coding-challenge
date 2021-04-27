package com.shopify.store.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.shopify.store.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    SELLER(Sets.newHashSet(SELLER_READ, SELLER_WRITE)),
    BUYER(Sets.newHashSet());

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
