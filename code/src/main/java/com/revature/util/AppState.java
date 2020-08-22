package com.revature.util;

//importing other classes
import com.revature.models.AppUser;

//importing built-in utils
import java.io.BufferedReader;

public class AppState {

    private BufferedReader input;
    private ScreenRoute router;
    private boolean isRunning;
    private AppUser currentUser;
}
