package me.playwithnathan.util;

public enum Perms {
    ADMIN("admin");

    private final String permission;

    Perms(String permission) {
        this.permission = permission;
    }

    public String getPerm() {
        return "mglb." + permission;
    }
}
