package com.revature.screens;

public abstract class Screens {

    private String name;
    private String route;

    // This is to be used in conjuction with the Router class. The Router
    // will take in user input and display the appropriate screen using
    // this constructor.
    protected Screens(String name, String route) {
        this.name = name;
        this.route = route;
    }

    // Since we are not changing the field values, we only
    // need public getters.
    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }

    /**
     * Displays a screen depending on implementation
     */
    public abstract void render();
}
