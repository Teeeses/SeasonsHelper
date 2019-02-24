package com.explead.seasonhelper.common.beans;

import com.explead.seasonhelper.R;
import com.explead.seasonhelper.common.logic.Cell;

public class GetIds {

    public static int getIdCubeFromColor(Cell.ColorCube color) {
        Integer id = null;
        if(color == Cell.ColorCube.RED) {
            id = R.drawable.red;
        }
        if(color == Cell.ColorCube.BLUE) {
            id = R.drawable.blue;
        }
        if(color == Cell.ColorCube.GREEN) {
            id = R.drawable.green;
        }
        if(color == Cell.ColorCube.YELLOW) {
            id = R.drawable.yellow;
        }
        if(color == Cell.ColorCube.VIOLET) {
            id = R.drawable.violet;
        }
        return id.intValue();
    }

    public static int getIdRouteFromColor(Cell.ColorCube color) {
        Integer id = null;
        if(color == Cell.ColorCube.RED) {
            id = R.drawable.red_route;
        }
        if(color == Cell.ColorCube.BLUE) {
            id = R.drawable.blue_route;
        }
        if(color == Cell.ColorCube.GREEN) {
            id = R.drawable.green_route;
        }
        if(color == Cell.ColorCube.YELLOW) {
            id = R.drawable.yellow_route;
        }
        if(color == Cell.ColorCube.VIOLET) {
            id = R.drawable.violet_route;
        }
        return id.intValue();
    }

    public static int getIdWinterInsideCubeFromColor(Cell.ColorCube color) {
        Integer id = null;
        if(color == Cell.ColorCube.RED) {
            id = R.drawable.icon_inside_red_cube;
        }
        if(color == Cell.ColorCube.BLUE) {
            id = R.drawable.icon_inside_blue_cube;
        }
        if(color == Cell.ColorCube.YELLOW) {
            id = R.drawable.icon_inside_yellow_cube;
        }
        return id.intValue();
    }

    public static int getIdWinterCubeFromColor(Cell.ColorCube color) {
        Integer id = null;
        if(color == Cell.ColorCube.RED) {
            id = R.drawable.icon_red_cube;
        }
        if(color == Cell.ColorCube.BLUE) {
            id = R.drawable.icon_blue_cube;
        }
        if(color == Cell.ColorCube.YELLOW) {
            id = R.drawable.icon_yellow_cube;
        }
        return id.intValue();
    }

}
