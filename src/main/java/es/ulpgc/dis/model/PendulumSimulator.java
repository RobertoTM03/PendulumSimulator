package es.ulpgc.dis.model;

public class PendulumSimulator {
    private final double dt;

    public PendulumSimulator(double dt) {
        if (dt <= 0) {
            throw new IllegalArgumentException("Delta time must be positive");
        }
        this.dt = dt;
    }

    public Pendulum simulate(Pendulum pendulum) {
        double length = pendulum.length();
        double g = pendulum.g();
        double gamma = 0.1;

        if (length <= 0) {
            throw new IllegalArgumentException("Length must be more than 0.");
        }
        if (g <= 0) {
            throw new IllegalArgumentException("Gravity must be more than 0");
        }

        double theta = pendulum.theta();

        double omega = pendulum.omega();
        double alpha = -(g / length) * Math.sin(theta) - gamma * omega;
        omega += alpha * dt;

        theta += omega * dt;
        theta = (theta + Math.PI) % (2 * Math.PI) - Math.PI;

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
