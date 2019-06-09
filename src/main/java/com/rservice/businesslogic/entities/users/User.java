package com.rservice.businesslogic.entities.users;

import com.rservice.businesslogic.Role;

public interface User {

    Boolean logIn(String realPassword);
    Role getRole();

    int getId();
    void setId(int id);

    String getLogin();
    void setLogin(String login);

    String getPassword();
    void setPassword(String password);
    void encryptAndSetPassword(String realPassword);

    String getName();
    void setName(String name);
}
