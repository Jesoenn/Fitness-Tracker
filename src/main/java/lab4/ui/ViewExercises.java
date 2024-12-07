package lab4.ui;

import lab4.controllers.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewExercises{
    private int width, height;
    private JButton addExerciseButton,confirmExerciseButton;
    private JTextField exerciseName;
    private HashMap<JLabel,JButton> exerciseNames;
    public ViewExercises(int width, int height) {
        this.width = width;
        this.height = height;
        createButtons();
    }
    private void createButtons(){
        addExerciseButton=new JButton("Add Exercise");
        confirmExerciseButton=new JButton("Confirm");
        exerciseNames=new HashMap<>();
        exerciseName=new JTextField();
    }
    public JScrollPane view(ArrayList<String> exerciseNames,boolean emptyList){

        addExerciseButton.setBounds(10, 10, 140, 30);

        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);
        panel.setPreferredSize(new Dimension(width,exerciseNames.size()*35));
        panel.add(addExerciseButton);

        if(emptyList){
            JLabel label=new JLabel(exerciseNames.getFirst());
            label.setBounds(10, 45, 150, 25);
            panel.add(label);
        }
        else
            viewExerciseList(panel,exerciseNames);

        JScrollPane scrollPane=new JScrollPane(panel);
        scrollPane.setBounds(10,60,width-30,height-150);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }
    private void viewExerciseList(JPanel panel, ArrayList<String> names){
        exerciseNames=new HashMap<>();
        for(String name: names) {
            exerciseNames.put(new JLabel(name), new JButton("Delete"));
        }
        int i=0;
        for (Map.Entry<JLabel, JButton> exerciseName : exerciseNames.entrySet()) {
            exerciseName.getKey().setBounds(10, 30*i+45, 150, 25);
            exerciseName.getValue().setBounds(170, 30*i+45, 150, 25);
            panel.add(exerciseName.getKey());
            panel.add(exerciseName.getValue());
            i++;
        }
    }
    public JPanel addExercise(String status_message){
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBounds(10,60,width-30,height-300);

        JLabel exerciseLabel = new JLabel("Exercise:");
        exerciseLabel.setBounds(width/2-200, height/2-200, 100, 25);
        exerciseName.setText("");
        exerciseName.setBounds(width/2-100, height/2-200, 150, 25);
        panel.add(exerciseLabel);
        panel.add(exerciseName);
        JLabel statusLabel = new JLabel(status_message);
        statusLabel.setBounds(width/2-200, height/2-100, 400, 25);
        panel.add(statusLabel);
        confirmExerciseButton.setBounds(width/2-100, height/2-50, 100, 30);
        panel.add(confirmExerciseButton);

        return panel;
    }

    public void setController(Controller controller){
        addExerciseButton.addActionListener(e-> controller.viewAddExercise(""));
        confirmExerciseButton.addActionListener(e-> controller.addExercise(exerciseName.getText()));
    }
    public void setExerciseNamesController(Controller controller){
        for (Map.Entry<JLabel, JButton> exerciseName : exerciseNames.entrySet()) {
            exerciseName.getValue().addActionListener(e -> controller.deleteExercise(exerciseName.getKey().getText()));
        }
    }
}
