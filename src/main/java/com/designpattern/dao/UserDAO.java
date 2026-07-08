package com.designpattern.dao;

import java.util.List;
import com.designpattern.model.User;

public interface UserDAO {

    boolean addUser(User u);

    boolean updateUser(User u);   // full update (all fields)

    boolean updateProfileDetails(User u); // new method for profile edits

    
    boolean deleteUser(int id);

    User getUser(int id);

    List<User> getAllUser();
}
