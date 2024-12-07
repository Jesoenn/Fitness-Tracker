package lab4.ui;

import lab4.controllers.Controller;
import lab4.workout.TrainingSession;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewTrainingSessions {
    private int width, height;
    private ArrayList<JFormattedTextField> setsValues,repsValues,weightValues,date;
    private JFormattedTextField length;
    private String routineName;
    private HashMap<String, JButton> deleteTrainingSessionButtons,editTrainingSessionButtons;
    private JButton finishTrainingSessionButton,finishEditingTrainingSessionButton;
    private String editedId;
    public ViewTrainingSessions(int width, int height) {
        this.width = width;
        this.height = height;
        addButtons();
    }

    private void addButtons(){
        finishTrainingSessionButton=new JButton("Finish");
        finishEditingTrainingSessionButton=new JButton("Confirm");
        deleteTrainingSessionButtons=new HashMap<>();
        editTrainingSessionButtons=new HashMap<>();
    }

    public JScrollPane viewTrainingSessionsMenu(ArrayList<ArrayList<String>> datesAndLengths, ArrayList<ArrayList<String>> idsAndRoutineNames, ArrayList<ArrayList<String>> exercises){
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);
        int rows=0;
        if(datesAndLengths.isEmpty()){
            JLabel status=new JLabel("No training sessions added");
            status.setBounds(10,10,200,30);
            panel.add(status);
        }
        else {
            deleteTrainingSessionButtons=new HashMap<>();
            editTrainingSessionButtons=new HashMap<>();
            for(int i=0;i<datesAndLengths.size();i++){
                rows+=viewTrainingSession(panel,rows,datesAndLengths.get(i),idsAndRoutineNames.get(i),exercises.get(i));
            }
        }

        panel.setPreferredSize(new Dimension(width,rows));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(10,60,width-30,height-150);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }
    public int viewTrainingSession(JPanel panel, int rows, ArrayList<String> datesAndLengths,ArrayList<String> idsAndRoutineNames,ArrayList<String> exercises){
        String date="Date: "+datesAndLengths.get(0)+"/"+datesAndLengths.get(1)+"/"+datesAndLengths.get(2);
        String hour="Time: "+datesAndLengths.get(3)+":"+datesAndLengths.get(4);
        String length="Length (minutes): "+datesAndLengths.get(5);
        JButton deleteTrainingSessionButton=new JButton("Delete");
        JButton editTrainingSessionButton=new JButton("Edit");
        JLabel dateLabel=new JLabel(date);
        JLabel lengthLabel=new JLabel(length);
        JLabel hourLabel=new JLabel(hour);
        JLabel id=new JLabel("Session Id: "+idsAndRoutineNames.get(0));
        JLabel routineName=new JLabel("Routine: "+idsAndRoutineNames.get(1));
        deleteTrainingSessionButton.setBounds(350,10+rows,80,25);
        editTrainingSessionButton.setBounds(460,10+rows,80,25);
        dateLabel.setBounds(10,10+rows,200,30);
        hourLabel.setBounds(115,10+rows,200,30);
        lengthLabel.setBounds(200,10+rows,200,30);
        id.setBounds(10,10+23+rows,200,30);
        routineName.setBounds(150,10+23+rows,200,30);
        panel.add(deleteTrainingSessionButton);
        panel.add(editTrainingSessionButton);
        panel.add(hourLabel);
        panel.add(dateLabel);
        panel.add(lengthLabel);
        panel.add(id);
        panel.add(routineName);
        editTrainingSessionButtons.put(idsAndRoutineNames.getFirst(),editTrainingSessionButton);
        deleteTrainingSessionButtons.put(idsAndRoutineNames.getFirst(),deleteTrainingSessionButton);
        trainingSessionViewExercise(panel,rows,exercises);
        return (exercises.size()/4+2)*30;
    }
    public void trainingSessionViewExercise(JPanel panel, int rows, ArrayList<String> exercises){
        ArrayList<JLabel> exerciseLabels=new ArrayList<>();
        ArrayList<JLabel> setsLabels=new ArrayList<>();
        ArrayList<JLabel> repsLabels=new ArrayList<>();
        ArrayList<JLabel> weightLabels=new ArrayList<>();
        int index=0;
        for(int i=0;i<exercises.size();i+=4){
            exerciseLabels.add(new JLabel("Exercise: "+exercises.get(i)));
            setsLabels.add(new JLabel("Sets: "+exercises.get(i+1)));
            repsLabels.add(new JLabel("Reps: "+exercises.get(i+2)));
            weightLabels.add(new JLabel("Weight: "+exercises.get(i+3)));
            exerciseLabels.get(index).setBounds(10,56+index*30+rows,200,30);
            setsLabels.get(index).setBounds(10+150,56+index*30+rows,200,30);
            repsLabels.get(index).setBounds(10+220,56+index*30+rows,200,30);
            weightLabels.get(index).setBounds(10+290,56+index*30+rows,200,30);
            panel.add(exerciseLabels.get(index));
            panel.add(setsLabels.get(index));
            panel.add(repsLabels.get(index));
            panel.add(weightLabels.get(index));
            index++;
        }
    }
    public JScrollPane viewActiveSession(ArrayList<String> currentDate,ArrayList<String> routine, NumberFormatter numberFormatter, ArrayList<NumberFormatter> dateFormatters){
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        finishTrainingSessionButton.setBounds(10,10,80,30);
        panel.add(finishTrainingSessionButton);

        int rows=trainingSessionEditor(currentDate,routine,panel,numberFormatter,dateFormatters);
        panel.setPreferredSize(new Dimension(width,rows*90));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(10,60,width-30,height-30);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    public JScrollPane editTrainingSession(String id, ArrayList<String> date,ArrayList<String> routine,NumberFormatter numberFormatter, ArrayList<NumberFormatter> dateFormatters){
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        editedId=id;
        finishEditingTrainingSessionButton.setBounds(10,10,80,30);
        panel.add(finishEditingTrainingSessionButton);

        int rows=trainingSessionEditor(date,routine,panel,numberFormatter,dateFormatters);

        panel.setPreferredSize(new Dimension(width,rows*90));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(10,60,width-30,height-30);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }
    public int trainingSessionEditor(ArrayList<String> sessionDates,ArrayList<String> routine,JPanel panel, NumberFormatter numberFormatter, ArrayList<NumberFormatter> dateFormatters){
        routineName=routine.getFirst();
        JLabel routineLabel=new JLabel(routine.getFirst());
        routineLabel.setBounds(10, 8+50, 150, 25);
        panel.add(routineLabel);

        //Date
        JLabel dateLabel=new JLabel("Date (DD/MM/YYYY)");
        dateLabel.setBounds(110, 15, 150, 25);
        JLabel timeLabel=new JLabel("Time (HH/MM)");
        timeLabel.setBounds(365, 15, 150, 25);
        panel.add(dateLabel);
        panel.add(timeLabel);

        date=new ArrayList<>();
        for(int i=0;i<5;i++){
            date.add(new JFormattedTextField(dateFormatters.get(i)));
            if(sessionDates!=null)
                date.get(i).setValue(Integer.valueOf(sessionDates.get(i)));
            else
                date.get(i).setValue(1);
            if(i<3)
                date.get(i).setBounds(220+i*45,10,35,30);
            else
                date.get(i).setBounds(300+i*50,10,30,30);
            panel.add(date.get(i));
        }
        JLabel lengthLabel=new JLabel("Workout length (minutes)");
        length=new JFormattedTextField(numberFormatter);
        lengthLabel.setBounds(565,15,200,25);
        length.setBounds(720,15,60,25);
        if(sessionDates!=null)
            length.setValue(Integer.valueOf(sessionDates.get(5)));
        else
            length.setValue(1);
        panel.add(lengthLabel);
        panel.add(length);

        ArrayList<JLabel> exerciseValues=new ArrayList<>();
        setsValues=new ArrayList<>();
        repsValues=new ArrayList<>();
        weightValues=new ArrayList<>();
        ArrayList<JLabel> exerciseLabels=new ArrayList<>();
        ArrayList<JLabel> setsLabels=new ArrayList<>();
        ArrayList<JLabel> repsLabels=new ArrayList<>();
        ArrayList<JLabel> weightLabels=new ArrayList<>();

        int index=0;
        for (int i = 1; i < routine.size(); i+=4) {
            //Descriptions
            setsLabels.add(new JLabel("Sets:"));
            repsLabels.add(new JLabel("Reps:"));
            exerciseLabels.add(new JLabel("Exercise:"));
            weightLabels.add(new JLabel("Weight:"));
            exerciseLabels.get(index).setBounds(10, 8+80+30*index, 150, 25);
            setsLabels.get(index).setBounds(10+200, 8+80+30*index, 50, 25);
            repsLabels.get(index).setBounds(10+300, 8+80+30*index, 50, 25);
            weightLabels.get(index).setBounds(10+400, 8+80+30*index, 50, 25);
            panel.add(setsLabels.get(index));
            panel.add(repsLabels.get(index));
            panel.add(exerciseLabels.get(index));
            panel.add(weightLabels.get(index));
            //Exercise name
            exerciseValues.add(new JLabel(routine.get(i)));
            exerciseValues.get(index).setBounds(80, 8+80+30*index, 150, 25);
            panel.add(exerciseValues.get(index));
            //Sets
            setsValues.add(new JFormattedTextField(numberFormatter));
            setsValues.get(index).setValue(Integer.valueOf(routine.get(i+1)));
            setsValues.get(index).setBounds(260, 8+80+30*index, 30, 25);
            panel.add(setsValues.get(index));
            //Reps
            repsValues.add(new JFormattedTextField(numberFormatter));
            repsValues.get(index).setValue(Integer.valueOf(routine.get(i+2)));
            repsValues.get(index).setBounds(360, 8+80+30*index, 30, 25);
            panel.add(repsValues.get(index));
            //Weight
            weightValues.add(new JFormattedTextField(numberFormatter));
            weightValues.get(index).setValue(Integer.valueOf(routine.get(i+3)));
            weightValues.get(index).setBounds(460, 8+80+30*index, 30, 25);
            panel.add(weightValues.get(index));
            index++;
        }
        return index;
    }


    public void setController(Controller controller){
        finishTrainingSessionButton.addActionListener(e->controller.addTrainingSession(routineName,toArray(setsValues),toArray(repsValues),toArray(weightValues),toArray(date),length.getText()));
        finishEditingTrainingSessionButton.addActionListener(e->controller.editTrainingSession(editedId, toArray(setsValues),toArray(repsValues),toArray(weightValues),toArray(date),length.getText()));
    }

    public void setDeleteAndEditTrainingSessionController(Controller controller){
        for(Map.Entry<String,JButton> deleteButton: deleteTrainingSessionButtons.entrySet()){
            deleteButton.getValue().addActionListener(e -> controller.deleteTrainingSession(deleteButton.getKey()));
        }
        for(Map.Entry<String,JButton> editButton: editTrainingSessionButtons.entrySet()){
            editButton.getValue().addActionListener(e -> controller.viewEditTrainingSession(editButton.getKey()));
        }
    }


    public ArrayList<String> toArray(ArrayList<JFormattedTextField> fieldValues){
        ArrayList<String> arrayValues=new ArrayList<>();
        for(JFormattedTextField field:fieldValues)
            arrayValues.add(field.getText());
        return arrayValues;
    }
}
