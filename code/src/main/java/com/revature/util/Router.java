package com.revature.util;

import com.revature.screens.Screens;

import java.util.HashSet;
import java.util.Set;

public class Router {

    // Instantiated when AppState is instantiated.
    // This creates a new Set of Screens to work with
    private Set<Screens> screens = new HashSet<>();


    /**
     * Adds selected screen classes to Set<Screens> to be instantiated
     * @param screen
     * @return
     */
    public Router addToScreens(Screens screen) {
        screens.add(screen);
        return this;
    }

    /**
     * Takes in a user-defined String that will tell the app to .render()
     * the specified screen.
     * @param route
     */
    public void navigate(String route) {

        screens.stream()
               .filter(screens -> screens.getRoute().equals(route))
               .findFirst()
               .orElseThrow(() -> new RuntimeException("Invalid choice."))
               .render();
    }





}
