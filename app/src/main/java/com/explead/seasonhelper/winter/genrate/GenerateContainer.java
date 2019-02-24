package com.explead.seasonhelper.winter.genrate;

import com.explead.seasonhelper.common.logic.Cell;
import com.explead.seasonhelper.common.logic.Direction;
import com.explead.seasonhelper.winter.logic.WinterCube;

import java.util.ArrayList;

public class GenerateContainer {

    public int amount = 1;
    private ArrayList<Cell> cells = new ArrayList<>();
    private ArrayList<Direction> directions = new ArrayList<>();

    public GenerateContainer(ArrayList<WinterCube> cubes, ArrayList<Direction> directions) {
        add(cubes);
        createDirections(directions);
    }

    private void createDirections(ArrayList<Direction> array) {
        directions.clear();
        for(Direction direction: array) {
            directions.add(Direction.valueOf(direction.name()));
        }
    }

    private void add(ArrayList<WinterCube> cubes) {
        for(WinterCube cube: cubes) {
            cells.add(new WinterCube(cube));
        }
    }

    public String getStringDirections() {
        StringBuilder builder = new StringBuilder();
        for(Direction direction: directions) {
            builder.append(direction.name());
        }
        return builder.toString();
    }

    public void plusAmount() {
        amount++;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public int getAmount() {
        return amount;
    }

    public boolean setDirections(ArrayList<Direction> array) {
        if(directions.size() > array.size()) {
            createDirections(array);
            return true;
        }
        return false;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }
}
