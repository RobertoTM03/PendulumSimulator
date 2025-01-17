package es.ulpgc.dis.view;

import es.ulpgc.dis.model.Pendulum;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingPendulumDisplay extends JPanel implements PendulumDisplay {
    private List<Pendulum> pendulums;

    @Override
    public void draw(List<Pendulum> pendulums) {
        this.pendulums = pendulums;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pendulums == null) return;

        int width = getWidth();
        int height = getHeight();

        int x0 = width / 2;
        int y0 = height / 4;

        drawMiddleCircle(g, x0, y0);

        for (Pendulum pendulum : pendulums) {
            int x1 = (int) (x0 + pendulum.length() * Math.sin(pendulum.theta()));
            int y1 = (int) (y0 + pendulum.length() * Math.cos(pendulum.theta()));

            g.setColor(Color.GREEN);
            g.drawLine(x0, y0, x1, y1);

            g.setColor(Color.RED);
            int radius = (int) pendulum.radius();
            g.fillOval(x1 - radius / 2, y1 - radius / 2, radius, radius);
        }
    }

    private static void drawMiddleCircle(Graphics g, int x0, int y0) {
        g.setColor(Color.BLACK);
        g.fillOval(x0 - 5, y0 - 5, 10, 10);
    }
}
