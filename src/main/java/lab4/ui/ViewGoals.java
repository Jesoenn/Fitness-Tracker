package lab4.ui;

import com.toedter.calendar.JDateChooser;
import lab4.controllers.Controller;
import com.toedter.calendar.JCalendar;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewGoals {
    private int width,height;
    private JComboBox<String> exercises, goalTypes;
    private JButton addGoalButton, deleteGoalButton;
    private JFormattedTextField goalValueField;
    private JDateChooser dateChooser;
    public ViewGoals(int width, int height) {
        this.width = width;
        this.height = height;
        addButtons();
    }
    private void addButtons(){
        exercises = new JComboBox<>();
        addGoalButton= new JButton("Add Goal");
        deleteGoalButton = new JButton("Delete Goal");
    }


    public JPanel viewGoalsMenu(String[] goalTypes, String viewedExercise, String[] exerciseList, String goalName, String goalValue, String currentValue, NumberFormatter numberFormatter, ArrayList<String> deadline){
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        if(exerciseList.length==0){
            JLabel status=new JLabel("Exercise list is empty.");
            status.setBounds(10,10,300,30);
            panel.add(status);
        }
        else{
            viewAddGoal(panel, goalTypes, viewedExercise, exerciseList, goalName, numberFormatter);
            viewGoals(panel, viewedExercise, goalName, goalValue, currentValue, deadline);
        }
        panel.setPreferredSize(new Dimension(width-30,height-150));
        panel.setBounds(10,60,width-30,height-150);
        return panel;
    }

    public void viewAddGoal(JPanel panel, String[] goalTypesList, String viewedExercise, String[] exerciseList, String goalName, NumberFormatter numberFormatter){
        exercises = new JComboBox<>(exerciseList);
        goalTypes = new JComboBox<>(goalTypesList);
        goalValueField = new JFormattedTextField(numberFormatter);
        goalValueField.setValue(0);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date minDate=calendar.getTime();

        dateChooser = new JDateChooser(minDate);
        dateChooser.setMinSelectableDate(minDate);
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setBounds(85, 130, 140, 30);

        JLabel exerciseLabel = new JLabel("Exercise:");
        JLabel goalTypeLabel = new JLabel("Goal:");
        JLabel goalValueLabel = new JLabel("Value:");
        JLabel deadlineLabel = new JLabel("Deadline:");

        exerciseLabel.setBounds(10,10,150,30);
        goalTypeLabel.setBounds(10,50,150,30);
        goalValueLabel.setBounds(10,90,150,30);
        deadlineLabel.setBounds(10,130,150,30);

        goalTypes.setSelectedItem(goalName);
        goalTypes.setBounds(85,50,140,30);
        goalValueField.setBounds(85,90,140,30);

        exercises.setSelectedItem(viewedExercise);
        exercises.setBounds(85,10,140,30);
        addGoalButton.setBounds(10,170,100,30);

        panel.add(exercises);
        panel.add(goalTypes);
        panel.add(goalValueField);
        panel.add(exerciseLabel);
        panel.add(goalTypeLabel);
        panel.add(goalValueLabel);
        panel.add(addGoalButton);
        panel.add(dateChooser);
        panel.add(deadlineLabel);
    }

    public void viewGoals(JPanel panel, String viewedExercise, String goalName, String goalValue, String currentValue, ArrayList<String> deadline){
        Font progressFont= new Font("Dialog", Font.BOLD, 30);
        if(goalName.isEmpty()){
            JLabel status=new JLabel("No goal set.");
            status.setFont(progressFont);
            status.setBounds(550,200,300,50);
            panel.add(status);
        }
        else{
            String deadlineText = String.format("%02d/%02d/%04d",Integer.parseInt(deadline.get(0)), Integer.parseInt(deadline.get(1)), Integer.parseInt(deadline.get(2)));
            JLabel deadlineLabel = new JLabel("Deadline: "+deadlineText);
            deadlineLabel.setFont(progressFont);
            deadlineLabel.setBounds(350,20,500,50);
            panel.add(deadlineLabel);

            deleteGoalButton.setBounds(width-30-160,height-150-40,150,30);
            panel.add(deleteGoalButton);

            JLabel viewedExerciseLabel = new JLabel("Exercise: "+viewedExercise);
            viewedExerciseLabel.setFont(progressFont);
            viewedExerciseLabel.setBounds(350,70,500,50);
            panel.add(viewedExerciseLabel);

            JLabel goalNameLabel = new JLabel();
            if(goalName.equals("Max weight"))
                goalNameLabel.setText(goalName+": "+currentValue+"kg");
            else
                goalNameLabel.setText(goalName+": "+currentValue);
            goalNameLabel.setFont(progressFont);
            goalNameLabel.setBounds(350,120,500,50);
            panel.add(goalNameLabel);

            JProgressBar goalProgress=new JProgressBar(0,Integer.parseInt(goalValue));
            goalProgress.setBounds(350,210,500,80);
            goalProgress.setValue(Integer.parseInt(currentValue));
            goalProgress.setFont(progressFont);
            goalProgress.setString(currentValue + " / " + goalValue);
            goalProgress.setStringPainted(true);
            panel.add(goalProgress);

            if(Integer.parseInt(goalValue)<=Integer.parseInt(currentValue)){
                JLabel succes=new JLabel("GOAL COMPLETED!");
                succes.setFont(progressFont);
                succes.setBounds(445,280,500,80);
                panel.add(succes);
            }
        }


    }

    public void setController(Controller controller){
        addGoalButton.addActionListener(e -> controller.addGoal((String)exercises.getSelectedItem(), dateChooser.getDate(), goalValueField.getText(), (String) goalTypes.getSelectedItem()));
        deleteGoalButton.addActionListener( e-> controller.deleteGoal((String)exercises.getSelectedItem()));
    }
    public void setViewController(Controller controller){
        exercises.addActionListener(e -> controller.viewGoals((String) exercises.getSelectedItem()));
    }
}
