package com.revature.models;

import java.util.Arrays;

public enum Role {

    BASIC_USER("Basic User"),
    ADMIN("Admin"),
    LOCKED("Locked");



    // This is so we can get the Role string from SQL
    // since we can't extract the Role object.
    private String roleName;

    @Override
    public String toString() {
        return roleName;
    }

    Role(String name) {
        this.roleName = name;
    }

    /**
     *  This method takes in the String from SQL
     *  and converts it into a Role object.
     * @param name
     * @return
     */
    public static Role getRoleName(String name) {
        return Arrays.stream(Role.values())
                     .filter(role -> role.roleName.equals(name))
                     .findFirst()
                     .orElse(LOCKED);
    }

}
