package com.example.demo.serviceImpl.service;

import com.example.demo.model.Dept;
import com.example.demo.model.User;

public interface  IUserService {

    public Long addUser(User user);

    public User getByName(String userName);

    public Long addDept(Dept dept);
}
