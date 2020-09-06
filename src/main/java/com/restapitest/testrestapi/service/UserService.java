package com.restapitest.testrestapi.service;

import com.restapitest.testrestapi.entity.User;

public interface UserService {

    public User getUserById(Integer id);

    public User saveUser(User user);

}
