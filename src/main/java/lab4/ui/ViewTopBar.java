package lab4.ui;

import lab4.controllers.Controller;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Date;

public class ViewTopBar extends JPanel {
    private int width,height;
    private JButton exerciseMenu,goalsMenu,trainingSessionsMenu,routinesMenu,progressMenu;
    public ViewTopBar(int width, int height) {
        setBounds(0,0,width,50);
        setLayout(null);
        this.width = width;
        this.height = height;
        createButtons();
    }
    private void createButtons(){
        exerciseMenu = new JButton("Exercise");
        goalsMenu = new JButton("Goals");
        trainingSessionsMenu = new JButton("Training Sessions");
        routinesMenu = new JButton("Routines");
        progressMenu = new JButton("Progress");
    }

    public void view(){
        removeAll();

        int placement=(width-140*5)/6;
        exerciseMenu.setBounds(placement, 10, 140, 30);
        goalsMenu.setBounds(placement*2+140, 10, 140, 30);
        trainingSessionsMenu.setBounds(placement*3+140*2, 10, 140, 30);
        routinesMenu.setBounds(placement*4+140*3, 10, 140, 30);
        progressMenu.setBounds(placement*5+140*4, 10, 140, 30);

        add(exerciseMenu);
        add(goalsMenu);
        add(trainingSessionsMenu);
        add(routinesMenu);
        add(progressMenu);
    }

    public void setController(Controller controller){
        exerciseMenu.addActionListener(e->controller.viewExercisesMenu());
        goalsMenu.addActionListener(e->controller.viewGoals(""));
        trainingSessionsMenu.addActionListener(e->controller.viewTrainingSessions());
        routinesMenu.addActionListener(e->controller.viewWorkoutRoutines());
        progressMenu.addActionListener(e->controller.viewChart("", new Date(), new Date(), ""));
    }
}
