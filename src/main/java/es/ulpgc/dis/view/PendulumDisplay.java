package es.ulpgc.dis.view;

import java.util.List;

public interface PendulumDisplay {
    void draw(List<Circle> circles);
    void of(Grabbed grabbed);
    void of(Released released);

    int centerPointX();
    int centerPointY();

    record Circle(String id, int x, int y, int r) {
        public boolean isAt(int x, int y) {
            return Math.abs((x - this.x)) < this.r &&
                    Math.abs((y - this.y)) < this.r;
        }
    }

    interface Grabbed {
        void at(Circle circle);
    }

    interface  Released {
        void at(Circle circle);
    }
}
