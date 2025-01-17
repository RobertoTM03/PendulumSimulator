package es.ulpgc.dis.presenter;

import es.ulpgc.dis.model.PendulumSimulator;
import es.ulpgc.dis.model.Pendulum;
import es.ulpgc.dis.view.PendulumDisplay;

import java.util.*;
import java.util.concurrent.*;

public class PendulumPresenter {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final PendulumDisplay pendulumDisplay;
    private final PendulumSimulator simulator;
    private List<Pendulum> pendulums;
    private static final double dt = 0.016;
    private static final int period = 16;

    public PendulumPresenter(PendulumDisplay pendulumDisplay, PendulumSimulator simulator) {
        this.pendulumDisplay = pendulumDisplay;
        this.simulator = simulator;
        this.pendulums = new ArrayList<>();
    }

    public PendulumPresenter add(Pendulum pendulum) {
        this.pendulums.add(pendulum);
        return this;
    }

    public void execute() {
        scheduler.scheduleAtFixedRate(this::update, 0, period, TimeUnit.MILLISECONDS);
    }

    private void update() {
        int stepsPerFrame = 1;
        for (int i = 0; i < stepsPerFrame; i++) {
            simulate();
        }
        draw();
    }

    private void draw() {
        pendulumDisplay.draw(pendulums);
    }

    private void simulate() {
        pendulums = pendulums.stream()
                .map(simulator::simulate)
                .toList();
    }
}
