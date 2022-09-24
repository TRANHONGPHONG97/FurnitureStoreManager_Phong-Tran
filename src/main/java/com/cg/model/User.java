package com.cg.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;

public class User {
    private int idUser;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private int idrole;

    public User() {
    }

    public User(String userName, String password, String phone, String email, int idrole) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.idrole = idrole;
    }

    public User(int idUser, String userName, String password, String phone, String email, int idrole) {
        this.idUser = idUser;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.idrole = idrole;
    }

    public User(String userName, String password, String phone, String email, String idrole) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.idrole = Integer.parseInt(idrole);
    }

    public User(String userName, String password, String phone, String email) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @NotEmpty(message = "Name not empty")
    @Pattern(regexp = "^([A-Z]+[a-z]*[ ]?)+$", message = "Format name not right")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @NotEmpty
    @Pattern(regexp = "^([A-Z][a-z0-9]{8,24})", message = "Format password not right")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty
    @Pattern(regexp = "((84|0[1|2|3|4|5|6|7|8|9])+([0-9]{8})\\b)", message = "Format phone not right")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotEmpty
    @Email(message = "Email format not right")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$", message = "Format mail not right")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdrole() {
        return idrole;
    }

    public void setIdrole(int idrole) {
        this.idrole = idrole;
    }
}
