package es.ulpgc.dis.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final PendulumDisplay pendulumDisplay;

    public MainFrame() {
        this.setTitle("Pendulum Simulator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
        add((Component) (pendulumDisplay = createPendulumDisplay()));
    }

    public PendulumDisplay getPendulumDisplay() {
        return pendulumDisplay;
    }

    private SwingPendulumDisplay createPendulumDisplay() {
        return new SwingPendulumDisplay();
    }
}
