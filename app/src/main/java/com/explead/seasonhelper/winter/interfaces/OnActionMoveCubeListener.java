package com.explead.seasonhelper.winter.interfaces;

import com.explead.seasonhelper.common.logic.Direction;
import com.explead.seasonhelper.winter.logic.WinterCube;

public interface OnActionMoveCubeListener {
    void onNotMove();
    void onGoOnCell();
    void onArrow();
    void onCubeOnPlace(WinterCube cube);
}
