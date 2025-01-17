package es.ulpgc.dis.model;

public class PendulumSimulator {
    private final double dt;

    public PendulumSimulator(double dt) {
        this.dt = dt;
    }

    public Pendulum simulate(Pendulum pendulum) {
        double length = pendulum.length();
        double g = pendulum.g();
        double gamma = 0.1;

        double theta = pendulum.theta();
        double omega = pendulum.omega();

        double alpha = -(g / length) * Math.sin(theta) - gamma * omega;

        omega += alpha * dt;
        theta += omega * dt;

        return new Pendulum(
                pendulum.id(),
                length,
                pendulum.theta0(),
                theta,
                omega,
                g,
                pendulum.radius()
        );
    }
}
