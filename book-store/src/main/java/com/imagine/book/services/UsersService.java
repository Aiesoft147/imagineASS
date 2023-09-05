package com.imagine.book.services;

public interface UsersService
{
    String register(String firstName, String lastName, String username, String password);
    String login(String username, String password);
    String logout();
}
