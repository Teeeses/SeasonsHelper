package com.explead.seasonhelper.winter.logic;

import com.explead.seasonhelper.winter.genrate.GenerateContainer;

import java.util.Comparator;

public class WinterComparators {

    public static Comparator<WinterCube> UP = new Comparator<WinterCube>() {
        @Override
        public int compare(WinterCube a, WinterCube b) {
            if (a.getX() < b.getX()) return -1;
            else if (a.getX() == b.getX()) return 0;
            else return 1;
        }
    };

    public static Comparator<WinterCube> DOWN = new Comparator<WinterCube>() {
        @Override
        public int compare(WinterCube a, WinterCube b) {
            if (a.getX() < b.getX()) return 1;
            else if (a.getX() == b.getX()) return 0;
            else return -1;
        }
    };

    public static Comparator<WinterCube> RIGHT = new Comparator<WinterCube>() {
        @Override
        public int compare(WinterCube a, WinterCube b) {
            if (a.getY() < b.getY()) return 1;
            else if (a.getY() == b.getY()) return 0;
            else return -1;
        }
    };

    public static Comparator<WinterCube> LEFT = new Comparator<WinterCube>() {
        @Override
        public int compare(WinterCube a, WinterCube b) {
            if (a.getY() < b.getY()) return -1;
            else if (a.getY() == b.getY()) return 0;
            else return 1;
        }
    };

    public static Comparator<WinterCube> ID = new Comparator<WinterCube>() {
        @Override
        public int compare(WinterCube a, WinterCube b) {
            if (a.getColor().getValue() < b.getColor().getValue()) return -1;
            else if (a.getColor().getValue() == b.getColor().getValue()) return 0;
            else return 1;
        }
    };

    public static Comparator<GenerateContainer> SORT_CONTAINERS = new Comparator<GenerateContainer>() {
        @Override
        public int compare(GenerateContainer a, GenerateContainer b) {
            if (a.getDirections().size() < b.getDirections().size()) return -1;
            else if (a.getDirections().size() == b.getDirections().size()) return 0;
            else return 1;
        }
    };
}