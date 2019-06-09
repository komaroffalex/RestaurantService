package com.rservice.businesslogic;

public enum Role {
    ADMINISTRATOR(0, "Administrator"),
    CLIENT(1, "Client"),
    WORKER(2, "Worker");

    private final int role;
    private final String roleName;

    Role(int role, String roleName) {
        this.role = role;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return role;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }

    /**
     * Get the role which corresponds to the given number as returned by
     * {@link Role#getRoleId()}. If the number is an invalid value,
     * then {@link Role#CLIENT} will be returned.
     *
     * @param role The numeric role value.
     * @return The role.
     */
    public static Role valueOf(int role) {
        try {
            return Role.values()[role];
        } catch (ArrayIndexOutOfBoundsException e) {
            return CLIENT;
        }
    }

    public static Role valueOfString(String role) {
        switch (role) {
            case "Administrator":
                return ADMINISTRATOR;
            case "Client":
                return CLIENT;
            case "Worker":
                return WORKER;
        }
        return CLIENT;
    }
}
