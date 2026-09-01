package org.example;

public interface IAuthService{
    boolean register(String login, String password);
    User login(String login, String password);
}