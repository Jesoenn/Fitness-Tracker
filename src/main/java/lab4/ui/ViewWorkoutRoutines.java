package lab4.ui;

import lab4.controllers.Controller;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewWorkoutRoutines {
    private int width,height;
    private JButton createRoutineButton,addExerciseToRoutine,addWorkoutRoutineButton,confirmEditWorkoutRoutineButton;
    private HashMap<String,JButton> deleteRoutine,editRoutine,startRoutine;
    private ArrayList<JComboBox<String>> exerciseValues;
    //Adding and editing
    private ArrayList<JFormattedTextField> setsValues,repsValues,weightValues;
    private HashMap<Integer, JButton> deleteExerciseFromRoutineBeingAddedButtons;
    private JTextField routineNameField;

    private JFormattedTextField setsField,repsField,weightField;
    private JComboBox<String> exerciseValue;

    public ViewWorkoutRoutines(int width, int height) {
        this.width = width;
        this.height = height;
        createButtons();
    }

    private void createButtons(){
        createRoutineButton=new JButton("Create Workout Routine");
        addExerciseToRoutine=new JButton("Add");
        deleteRoutine=new HashMap<>();
        editRoutine=new HashMap<>();
        addWorkoutRoutineButton=new JButton("Create");
        //Adding and editing
        exerciseValues=new ArrayList<>();
        setsValues=new ArrayList<>();
        repsValues=new ArrayList<>();
        weightValues=new ArrayList<>();
        deleteExerciseFromRoutineBeingAddedButtons=new HashMap<>();
        routineNameField=new JTextField();
        confirmEditWorkoutRoutineButton=new JButton("Edit");
    }

    public JScrollPane viewWorkoutRoutines(ArrayList<ArrayList<String>> routines){
        JPanel panel=new JPanel();
        panel.setLayout(null);

        createRoutineButton.setBounds(10, 10, 200, 30);
        panel.add(createRoutineButton);

        deleteRoutine=new HashMap<>();
        editRoutine=new HashMap<>();
        startRoutine=new HashMap<>();

        int prevHeight=0;
        for(ArrayList<String> routine: routines) {
            JPanel routinePanel=displayRoutine(routine,prevHeight);
            prevHeight+=routinePanel.getHeight();
            panel.add(routinePanel);
        }
        panel.setPreferredSize(new Dimension(width,prevHeight));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(10,60,width-30,height-150);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }
    public JPanel displayRoutine(ArrayList<String> routine, int prevHeight){
        JPanel routinePanel=new JPanel();
        routinePanel.setLayout(null);
        JLabel routineLabel = new JLabel(routine.getFirst());
        routineLabel.setBounds(0,40,100,30);
        routinePanel.add(routineLabel);

        deleteRoutine.put(routine.getFirst(),new JButton("Delete"));
        editRoutine.put(routine.getFirst(),new JButton("Edit"));
        startRoutine.put(routine.getFirst(),new JButton("Start"));
        deleteRoutine.get(routine.getFirst()).setBounds(110,40,80,30);
        editRoutine.get(routine.getFirst()).setBounds(200,40,80,30);
        startRoutine.get(routine.getFirst()).setBounds(290,40,80,30);
        routinePanel.add(editRoutine.get(routine.getFirst()));
        routinePanel.add(deleteRoutine.get(routine.getFirst()));
        routinePanel.add(startRoutine.get(routine.getFirst()));

        JPanel exercisePanel=new JPanel();
        exercisePanel.setLayout(null);
        ArrayList<JLabel> exerciseLabels=new ArrayList<>();
        routinePanel.add(exercisePanel);
        int row=0;
        for (int i = 1; i < routine.size(); i++) {
            exerciseLabels.add(new JLabel(routine.get(i)));
            exercisePanel.add(exerciseLabels.get(i-1));
            if(i%4==1)
                exerciseLabels.get(i-1).setBounds(15,row*25+25+40,100,25);
            if(i%4==2)
                exerciseLabels.get(i-1).setBounds(115,row*25+25+40,100,25);
            if(i%4==3)
                exerciseLabels.get(i-1).setBounds(215,row*25+25+40,100,25);
            if(i%4==0){
                exerciseLabels.get(i-1).setBounds(315,row*25+25+40,100,25);
                row++;
            }
        }
        exercisePanel.setSize(new Dimension(400,30+row*40));
        routinePanel.setBounds(10,10+prevHeight,400,30+exercisePanel.getHeight());
        return routinePanel;
    }

    public JScrollPane addWorkoutRoutine(String[] exerciseNames, ArrayList<String> routine,NumberFormatter numberFormatter){
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);
        if(exerciseNames.length!=0){
            addWorkoutRoutineButton.setBounds(275,10,100,30);
            panel.add(addWorkoutRoutineButton);
        }

        workoutRoutineEditor(panel,exerciseNames,routine,numberFormatter);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(10,90,width-30,width-150);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }


    public void workoutRoutineEditor(JPanel panel,String[] exerciseNames, ArrayList<String> routine,NumberFormatter numberFormatter){
        if(exerciseNames.length!=0){
            int listHeight=displayExercisesBeingAdded(panel, routine,exerciseNames,numberFormatter);

            JLabel exerciseLabel=new JLabel("Exercise:");
            exerciseLabel.setBounds(10,50+listHeight,100,30);
            panel.add(exerciseLabel);
            exerciseValue=new JComboBox<>(exerciseNames);
            exerciseValue.setBounds(80,50+listHeight,120,30);
            panel.add(exerciseValue);
            JLabel setsLabel=new JLabel("Sets:");
            setsLabel.setBounds(220,50+listHeight,100,30);
            panel.add(setsLabel);
            JLabel repsLabel=new JLabel("Reps:");
            repsLabel.setBounds(300,50+listHeight,100,30);
            panel.add(repsLabel);
            JLabel weightLabel=new JLabel("Weight:");
            weightLabel.setBounds(380,50+listHeight,100,30);
            panel.add(weightLabel);

            setsField=new JFormattedTextField(numberFormatter);
            setsField.setBounds(255,50+listHeight,30,30);
            setsField.setValue(0);
            panel.add(setsField);
            repsField=new JFormattedTextField(numberFormatter);
            repsField.setBounds(335,50+listHeight,30,30);
            repsField.setValue(0);
            panel.add(repsField);
            weightField=new JFormattedTextField(numberFormatter);
            weightField.setBounds(430,50+listHeight,30,30);
            weightField.setValue(0);
            panel.add(weightField);

            addExerciseToRoutine.setBounds(10,90+listHeight,70,30);
            panel.add(addExerciseToRoutine);


            panel.setPreferredSize(new Dimension(width,listHeight*5));
        }
        else{
            JLabel status=new JLabel("No exercises found.");
            status.setBounds(10, 8, 150, 25);
            panel.setPreferredSize(new Dimension(width,35));
            panel.add(status);
        }
    }
    public int displayExercisesBeingAdded(JPanel panel, ArrayList<String> routine, String[] exerciseNames, NumberFormatter numberFormatter){
        JLabel routineNameLabel=new JLabel("Routine name: ");
        routineNameLabel.setBounds(10,10,150,30);
        panel.add(routineNameLabel);
        if(!routine.isEmpty())
            routineNameField=new JTextField(routine.getFirst());
        else
            routineNameField=new JTextField("");
        routineNameField.setBounds(120,10,100,30);
        panel.add(routineNameField);
        //Labels
        ArrayList<JLabel> exerciseLabels=new ArrayList<>();
        ArrayList<JLabel> setsLabels=new ArrayList<>();
        ArrayList<JLabel> repsLabels=new ArrayList<>();
        ArrayList<JLabel> weightLabels=new ArrayList<>();
        //Options to choose
        exerciseValues=new ArrayList<>();
        setsValues=new ArrayList<>();
        repsValues=new ArrayList<>();
        weightValues=new ArrayList<>();
        deleteExerciseFromRoutineBeingAddedButtons=new HashMap<>();
        int index=0;
        for(int i=1;i<routine.size();i+=4){
            exerciseLabels.add(new JLabel("Exercise: "));
            exerciseLabels.get(index).setBounds(10,50+index*40,100,30);
            panel.add(exerciseLabels.get(index));
            setsLabels.add(new JLabel("Sets: "));
            setsLabels.get(index).setBounds(220,50+index*40,100,30);
            panel.add(setsLabels.get(index));
            repsLabels.add(new JLabel("Reps: "));
            repsLabels.get(index).setBounds(300,50+index*40,100,30);
            panel.add(repsLabels.get(index));
            weightLabels.add(new JLabel("Weight: "));
            weightLabels.get(index).setBounds(380,50+index*40,100,30);
            panel.add(weightLabels.get(index));

            exerciseValues.add(new JComboBox<>(exerciseNames));
            exerciseValues.get(index).setSelectedItem(routine.get(i));
            exerciseValues.get(index).setBounds(80,50+index*40,120,30);
            panel.add(exerciseValues.get(index));

            setsValues.add(new JFormattedTextField(numberFormatter));
            setsValues.get(index).setValue(Integer.valueOf(routine.get(i+1)));
            setsValues.get(index).setBounds(255,50+index*40,30,30);
            panel.add(setsValues.get(index));

            repsValues.add(new JFormattedTextField(numberFormatter));
            repsValues.get(index).setValue(Integer.valueOf(routine.get(i+2)));
            repsValues.get(index).setBounds(335,50+index*40,30,30);
            panel.add(repsValues.get(index));

            weightValues.add(new JFormattedTextField(numberFormatter));
            weightValues.get(index).setValue(Integer.valueOf(routine.get(i+3)));
            weightValues.get(index).setBounds(430,50+index*40,30,30);
            panel.add(weightValues.get(index));

            JButton deleteButton=new JButton("Delete");
            deleteButton.setBounds(480,50+index*40,100,30);
            panel.add(deleteButton);
            deleteExerciseFromRoutineBeingAddedButtons.put(index,deleteButton);

            index++;
        }
        return index*40;
    }


    public void setController(Controller controller){
        createRoutineButton.addActionListener(e-> controller.viewAddWorkoutRoutine());
        addExerciseToRoutine.addActionListener( e ->{
            ArrayList<String> exercises=new ArrayList<>();
            for (JComboBox<String> value : exerciseValues)
                exercises.add(Objects.requireNonNull(value.getSelectedItem()).toString());
            controller.addExerciseToRoutine(routineNameField.getText(),exercises,toArray(setsValues),toArray(repsValues),toArray(weightValues),exerciseValue.getSelectedItem().toString(),setsField.getText(),repsField.getText(),weightField.getText());
        });
        addWorkoutRoutineButton.addActionListener(e->{
            ArrayList<String> exercises=new ArrayList<>();
            for (JComboBox<String> value : exerciseValues)
                exercises.add(Objects.requireNonNull(value.getSelectedItem()).toString());
            controller.addWorkoutRoutine(routineNameField.getText(),exercises,toArray(setsValues),toArray(repsValues),toArray(weightValues));
        });
    }
    public void setDeleteExerciseFromRoutineBeingAddedController(Controller controller){
        for(Map.Entry<Integer,JButton> deleteButton: deleteExerciseFromRoutineBeingAddedButtons.entrySet()){
            deleteButton.getValue().addActionListener(e -> {
                ArrayList<String> exercises=new ArrayList<>();
                for (JComboBox<String> value : exerciseValues)
                    exercises.add(Objects.requireNonNull(value.getSelectedItem()).toString());
                controller.deleteExerciseFromRoutineBeingAdded(deleteButton.getKey(),routineNameField.getText(),exercises,toArray(setsValues),toArray(repsValues),toArray(weightValues));
            });
        }
    }
    public void setRoutineController(Controller controller){
        for(Map.Entry<String,JButton> deleteButton: deleteRoutine.entrySet()){
            deleteButton.getValue().addActionListener(e -> controller.deleteWorkoutRoutine(deleteButton.getKey()));
        }
        for(Map.Entry<String,JButton> editButton: editRoutine.entrySet()){
            editButton.getValue().addActionListener(e -> controller.viewEditWorkoutRoutine(editButton.getKey()));
        }
        for(Map.Entry<String,JButton> startButton: startRoutine.entrySet()){
            startButton.getValue().addActionListener(e -> controller.startTrainingSessions(startButton.getKey()));
        }
    }

    public ArrayList<String> toArray(ArrayList<JFormattedTextField> fieldValues){
        ArrayList<String> arrayValues=new ArrayList<>();
        for(JFormattedTextField field:fieldValues)
            arrayValues.add(field.getText());
        return arrayValues;
    }
}
