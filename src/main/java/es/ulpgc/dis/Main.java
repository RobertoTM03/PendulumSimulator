package es.ulpgc.dis;

import es.ulpgc.dis.model.Pendulum;
import es.ulpgc.dis.model.PendulumSimulator;
import es.ulpgc.dis.presenter.PendulumPresenter;
import es.ulpgc.dis.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();

        PendulumSimulator simulator = new PendulumSimulator(0.016);
        PendulumPresenter presenter = new PendulumPresenter(mainFrame.getPendulumDisplay(), simulator)
                .add(new Pendulum("P1", 20, Math.toRadians(90), Math.toRadians(30), 0, 9.8, 1.2))
                .add(new Pendulum("P2", 10, Math.toRadians(45), Math.toRadians(45), 4, 9.8, 1))
                .add(new Pendulum("P3", 5, Math.toRadians(60), Math.toRadians(60), 0, 9.8, 0.8));

        presenter.execute();

        mainFrame.setVisible(true);
    }
}
