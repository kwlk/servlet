package com.beans;

import com.enums.Role;

public class User {
    protected String login;
    protected String pass;
    final protected Role role;

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
        this.role = Role.USER;
    }

    public User(String login, String pass, Role role) {
        this.login = login;
        this.pass = pass;
        this.role = role;
    }

    public Role getRole () {
        return this.role;
    }

    public String getName() {
        return login;
    }
}
