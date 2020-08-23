package com.revature;

import com.revature.util.AppState;

public class AppDriver {

    // This will instantiate the AppState class before anything else
    // which is responsible for instantiating all of our other classes.
    public static AppState app = new AppState();

    public static void main(String[] args) {

        while(app.running()) {
            app.getRoute().navigate("/homescreen");
        }


    }

}
