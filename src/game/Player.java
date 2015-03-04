package game;

import robot.TestRobot;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *<h1>Player</h1><br>
 *     <p>Keep track of the player. Containing the players interface and it's robot.</p>
 */
public class Player {

    private String name;
    private final TestRobot robot;
    private JPanel panel;
    private JButton endTurnButton;
    private JButton moveButton;
    private boolean done;

    public void setDone(boolean b) {
        done = b;
    }

    public boolean getDone() {
        return done;
    }

    public JPanel getPanel() {
        return panel;
    }

    public Player(String name, TestRobot rb) {

        this.name = name;
        this.robot = rb;
        done = false;

        panel  = new JPanel();
        endTurnButton  = new JButton("End Turn");
        moveButton = new JButton("Move");

        moveButton.setAction(new AbstractAction(){
            @Override public void actionPerformed(final ActionEvent e) {
                robot.addMoveForwardOne();
            }
        });

        endTurnButton.setAction(new AbstractAction(){
            @Override public void actionPerformed(final ActionEvent e) {
                done = true;
            }
        });

        endTurnButton.setText("End Turn");
        moveButton.setText("Move");

        panel.add(endTurnButton);
        panel.add(moveButton);

    }





}
