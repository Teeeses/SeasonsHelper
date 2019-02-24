package com.explead.seasonhelper.common.app;

import android.app.Application;

import com.explead.seasonhelper.common.beans.LevelContainer;
import com.explead.seasonhelper.common.logic.ContainerCells;

import java.util.ArrayList;

/**
 * Created by Александр on 09.07.2017.
 */

public class App extends Application{

    private static float widthScreen;
    private static float heightScreen;

    private static int[][] winterMass;
    private static  ArrayList<ContainerCells> winterContainer;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static ArrayList<ContainerCells> getWinterContainer() {
        return winterContainer;
    }

    public static void setWinterContainer(ArrayList<ContainerCells> winterContainer) {
        App.winterContainer = winterContainer;
    }

    public static void setWinterMass(int[][] winterMass) {
        App.winterMass = winterMass;
    }

    public static int[][] getWinterMass() {
        return winterMass;
    }

    public static float getWidthScreen() {
        return widthScreen;
    }

    public static void setWidthScreen(float widthScreen) {
        App.widthScreen = widthScreen;
    }

    public static float getHeightScreen() {
        return heightScreen;
    }

    public static void setHeightScreen(float heightScreen) {
        App.heightScreen = heightScreen;
    }
}
