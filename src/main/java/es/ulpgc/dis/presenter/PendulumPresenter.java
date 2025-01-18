package es.ulpgc.dis.presenter;

import es.ulpgc.dis.model.PendulumSimulator;
import es.ulpgc.dis.model.Pendulum;
import es.ulpgc.dis.view.PendulumDisplay;

import java.util.*;

public class PendulumPresenter {
    public static final Timer timer = new Timer();
    private static final int period = 16;

    private final PendulumDisplay pendulumDisplay;
    private final PendulumSimulator simulator;
    private List<Pendulum> pendulums;
    private Pendulum grabbedPendulum;

    public PendulumPresenter(PendulumDisplay pendulumDisplay, PendulumSimulator simulator) {
        this.pendulumDisplay = pendulumDisplay;
        this.simulator = simulator;
        this.pendulumDisplay.of(pendulumGrabbed());
        this.pendulumDisplay.of(pendulumReleased());
        this.pendulums = new ArrayList<>();
    }

    private PendulumDisplay.Released pendulumReleased() {
        return _ -> grabbedPendulum = null;
    }

    private PendulumDisplay.Grabbed pendulumGrabbed() {
        return c -> grabbedPendulum = toPendulum(c);
    }

    private Pendulum toPendulum(PendulumDisplay.Circle c) {
        Pendulum pendulum = findPendulum(c.id());

        int x0 = pendulumDisplay.centerPointX();
        int y0 = pendulumDisplay.centerPointY();

        int circleCenterX = c.x();
        int circleCenterY = c.y();

        double dx = toMeters(circleCenterX - x0);
        double dy = toMeters(circleCenterY - y0);

        double newLength = Math.sqrt(dx * dx + dy * dy);

        double newTheta = Math.atan2(dx, dy);

        return new Pendulum(
                pendulum.id(),
                newLength,
                newTheta,
                newTheta,
                0,
                pendulum.g(),
                pendulum.radius()
        );
    }

    private Pendulum findPendulum(String id) {
        return pendulums.stream()
                .filter(b -> b.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public PendulumPresenter add(Pendulum pendulum) {
        this.pendulums.add(pendulum);
        return this;
    }

    public void execute() {
        timer.schedule(task(),
                0,
                period);
    }

    private TimerTask task() {
        return  new TimerTask() {
            @Override
            public void run() {
                simulate();
                draw();
            }

            private void draw() {
                pendulumDisplay.draw(toCircles(pendulums));
            }
        };
    }

    private final static double PixelsPerMeter = 5 / 0.2;

    private PendulumDisplay.Circle mapToCircle(Pendulum pendulum) {
        int x0 = pendulumDisplay.centerPointX();
        int y0 = pendulumDisplay.centerPointY();

        int x1 = x0 + (int) (toPixels(pendulum.length()) * Math.sin(pendulum.theta()));
        int y1 = y0 + (int) (toPixels(pendulum.length()) * Math.cos(pendulum.theta()));

        int radius = toPixels(pendulum.radius());

        return new PendulumDisplay.Circle(pendulum.id(), x1, y1, radius);
    }


    private List<PendulumDisplay.Circle> toCircles(List<Pendulum> pendulums) {
        return pendulums.stream()
                .map(this::mapToCircle)
                .toList();
    }

    private static int toPixels(double b) {
        return (int) (b * PixelsPerMeter);
    }

    private static double toMeters(double b) {
        return  (b / PixelsPerMeter);
    }

    private void simulate() {
        pendulums = pendulums.stream()
                .map(this::simulatePendulum)
                .toList();
    }

    private Pendulum simulatePendulum(Pendulum pendulum) {
        if (grabbedPendulum != null && pendulum.id().equals(grabbedPendulum.id())) {
            System.out.println(grabbedPendulum);
            return grabbedPendulum;
        }
        return simulator.simulate(pendulum);
    }
}
