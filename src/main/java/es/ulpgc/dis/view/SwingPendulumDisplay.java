package es.ulpgc.dis.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SwingPendulumDisplay extends JPanel implements PendulumDisplay {
    private final List<Circle> circles;
    private Grabbed grabbed = null;
    private Released released = null;
    Optional<Circle> draggedCircle = Optional.empty();

    public SwingPendulumDisplay() {
        this.circles = new ArrayList<>();
        this.addMouseListener(createMouseListener());
        this.addMouseMotionListener(createMouseMotionListener());
    }

    private MouseListener createMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                draggedCircle = findCircle(Coordinates.at(e.getX(), e.getY()));
                draggedCircle.ifPresent(c -> grabbed.at(c));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (released == null) return;
                Coordinates coordinates = Coordinates.at(e.getX(), e.getY());
                draggedCircle.ifPresent(c -> released.at(new Circle(c.id(), coordinates.x, coordinates.y, c.r())));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
    }

    private MouseMotionListener createMouseMotionListener() {
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedCircle.isEmpty())return;
                Coordinates coordinates = Coordinates.at(e.getX(), e.getY());
                draggedCircle.ifPresent(c -> grabbed.at(new Circle(c.id(), coordinates.x, coordinates.y, c.r())));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
    }

    private Optional<Circle> findCircle(Coordinates coordinates) {
        synchronized (circles) {
            return circles.stream()
                    .filter(c -> c.isAt(coordinates.x, coordinates.y))
                    .findFirst();
        }
    }

    @Override
    public void of(Grabbed grabbed) {
        this.grabbed = grabbed;
    }

    @Override
    public void of(Released released) {
        this.released = released;
    }

    @Override
    public int centerPointX() {
        return getWidth() / 2;
    }

    @Override
    public int centerPointY() {
        return getHeight() / 4;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        synchronized (this.circles) {
            super.paintComponent(graphics);
            drawMiddleCircle(graphics, centerPointX(), centerPointY());
            Coordinates.width = getWidth();
            Coordinates.height = getHeight();
            circles.forEach(c -> draw(graphics, c, centerPointX(), centerPointY()));
        }
    }

    @Override
    public void draw(List<Circle> circles) {
        synchronized (this.circles) {
            this.circles.clear();
            this.circles.addAll(circles);
            repaint();
        }
    }

    private void draw(Graphics graphics, Circle c, int x0, int y0) {
        graphics.setColor(Color.BLACK);
        graphics.drawLine(x0, y0, c.x(), c.y());

        graphics.setColor(Color.red);
        graphics.fillOval(c.x() - c.r(), c.y() - c.r(), c.r()*2,c.r()*2);
    }

    private static void drawMiddleCircle(Graphics g, int x0, int y0) {
        g.setColor(Color.BLACK);
        g.fillOval(x0 - 5, y0 - 5, 10, 10);
    }

    record Coordinates(int x, int y) {
        public static int width;
        public static int height;

        public static Coordinates at(int x, int y) {
            return new Coordinates(x, y);
        }
    }
}
