package com.revature.tempRepo;

import com.revature.models.AppUser;

import java.util.HashMap;

public class UserDataBase extends HashMap<Integer, AppUser> {

    public static UserDataBase userData = new UserDataBase();
    public static int key = 1;

}
