package space.learning.myui.exam1.Q2;

import android.graphics.Point;

public class MyPoint extends Point {

    public int id;

    public MyPoint(int x, int y, int id) {
        super(x, y);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
