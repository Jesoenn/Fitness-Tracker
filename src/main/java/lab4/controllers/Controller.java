package lab4.controllers;

import lab4.ui.*;
import lab4.workout.Exercise;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Controller {
    //UI
    private Frame mainFrame;
    private ViewTopBar topBarPanel;
    private ViewExercises exercisesPanel;
    private ViewWorkoutRoutines workoutRoutinesPanel;
    private ViewTrainingSessions trainingSessionsPanel;
    private ViewGoals goalsPanel;
    private ViewProgressCharts progressChartsPanel;
    //Controllers
    private ExerciseService exerciseService;
    private WorkoutRoutineService workoutRoutineService;
    private TrainingSessionService trainingSessionService;
    private GoalsService goalsService;
    private ChartsService chartsService;

    private FileOperations fileOperations;

    private enum Menus{
        ADD_ROUTINE,EDIT_ROUTINE, OTHER
    }
    private Menus currentDisplay=Menus.OTHER;
    private String routineBeingEdited;
    public Controller(Frame mainFrame, ViewTopBar topBarPanel, ViewExercises exercisesPanel, ViewWorkoutRoutines
            workoutRoutinesPanel, ViewTrainingSessions trainingSessionsPanel, ViewGoals goalsPanel, ViewProgressCharts progressChartsPanel) {
        exerciseService = new ExerciseService();
        workoutRoutineService = new WorkoutRoutineService();
        trainingSessionService = new TrainingSessionService();
        goalsService = new GoalsService();
        chartsService = new ChartsService();

        this.mainFrame = mainFrame;
        this.topBarPanel = topBarPanel;
        this.exercisesPanel = exercisesPanel;
        this.workoutRoutinesPanel = workoutRoutinesPanel;
        this.trainingSessionsPanel = trainingSessionsPanel;
        this.goalsPanel = goalsPanel;
        this.progressChartsPanel = progressChartsPanel;

        fileOperations = new FileOperations();
        loadDatabase();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveCurrentState();
        }));


        setControllers();
        viewExercisesMenu();
    }

    private void loadDatabase(){
        exerciseService.setExercises(fileOperations.loadList(FileOperations.ListType.EXERCISES));
        workoutRoutineService.setWorkoutRoutines(fileOperations.loadList(FileOperations.ListType.WORKOUT_ROUTINES));
        trainingSessionService.setTrainingSessions(fileOperations.loadList(FileOperations.ListType.TRAINING_SESSIONS));
        goalsService.setGoals(fileOperations.loadList(FileOperations.ListType.GOALS));

        if(!trainingSessionService.getTrainingSessions().isEmpty()){
            trainingSessionService.getTrainingSessions().getFirst().loadTotalSessions();
        }
    }
    private void saveCurrentState(){
        fileOperations.saveList(exerciseService.getExercisesObjects(), FileOperations.ListType.EXERCISES);
        fileOperations.saveList(workoutRoutineService.getWorkoutRoutines(), FileOperations.ListType.WORKOUT_ROUTINES);
        fileOperations.saveList(trainingSessionService.getTrainingSessions(), FileOperations.ListType.TRAINING_SESSIONS);
        fileOperations.saveList(goalsService.getGoalsList(), FileOperations.ListType.GOALS);
    }

    private void setControllers(){
        topBarPanel.setController(this);
        exercisesPanel.setController(this);
        workoutRoutinesPanel.setController(this);
        trainingSessionsPanel.setController(this);
        goalsPanel.setController(this);
        progressChartsPanel.setController(this);
    }

    public void viewTopBar(){
        mainFrame.clear();
        topBarPanel.view();
        mainFrame.addPanel(topBarPanel);
    }

    //Exercises
    public void viewExercisesMenu(){
        viewTopBar();
        ArrayList<String> exercises = exerciseService.getExercises();
        if(exercises.isEmpty()){
            exercises.add("Exercise list is empty.");
            mainFrame.addScrollPanel(exercisesPanel.view(exercises,true));
        }
        else{
            mainFrame.addScrollPanel(exercisesPanel.view(exercises,false));
            exercisesPanel.setExerciseNamesController(this);
        }
    }
    public void viewAddExercise(String status){
        viewTopBar();
        mainFrame.addPanel(exercisesPanel.addExercise(status));
    }
    public void addExercise(String name){
        if(!exerciseService.exerciseNameExists(name) && !name.isEmpty()){
            exerciseService.addExercise(name);
            viewExercisesMenu();
        }
        else{
            viewAddExercise("Exercise already exists.");
        }
    }
    public void deleteExercise(String name){
        exerciseService.deleteExercise(name);
        workoutRoutineService.deleteExercise(name);
        goalsService.deleteGoal(name);
        viewExercisesMenu();
    }

    //Workout Routines
    public void viewWorkoutRoutines(){
        viewTopBar();
        ArrayList<ArrayList<String>> routines=workoutRoutineService.getAllRoutinesAsStrings();
        mainFrame.addScrollPanel(workoutRoutinesPanel.viewWorkoutRoutines(routines));
        workoutRoutinesPanel.setRoutineController(this);
    }
    public void viewAddWorkoutRoutine(){
        currentDisplay=Menus.ADD_ROUTINE;
        viewTopBar();
        mainFrame.addScrollPanel(workoutRoutinesPanel.addWorkoutRoutine(exerciseService.getExercises().toArray(new String[0]),new ArrayList<>(),getNumberFormatter(0,999)));
        workoutRoutinesPanel.setDeleteExerciseFromRoutineBeingAddedController(this);
    }

    public void deleteExerciseFromRoutineBeingAdded(int index,String routineName,ArrayList<String> exercises, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weights){
        exercises.remove(index);
        sets.remove(index);
        reps.remove(index);
        weights.remove(index);
        ArrayList<String> editedRoutine=workoutRoutineService.routineSeparateArraysToOne(routineName,exercises,sets,reps,weights);

        viewTopBar();
        mainFrame.addScrollPanel(workoutRoutinesPanel.addWorkoutRoutine(exerciseService.getExercises().toArray(new String[0]),editedRoutine,getNumberFormatter(0,999)));
        workoutRoutinesPanel.setDeleteExerciseFromRoutineBeingAddedController(this);
    }
    public void addExerciseToRoutine(String routineName,ArrayList<String> exercises, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weights, String exercise, String set, String rep, String weight){
        ArrayList<String> editedRoutine=workoutRoutineService.routineSeparateArraysToOne(routineName,exercises,sets,reps,weights);
        editedRoutine.add(exercise);
        editedRoutine.add(set);
        editedRoutine.add(rep);
        editedRoutine.add(weight);

        viewTopBar();
        mainFrame.addScrollPanel(workoutRoutinesPanel.addWorkoutRoutine(exerciseService.getExercises().toArray(new String[0]),editedRoutine,getNumberFormatter(0,999)));
        workoutRoutinesPanel.setDeleteExerciseFromRoutineBeingAddedController(this);
    }
    public void viewEditWorkoutRoutine(String routine){
        currentDisplay=Menus.EDIT_ROUTINE;
        routineBeingEdited=routine;
        ArrayList<String> routineArray=workoutRoutineService.getRoutineAsArray(routine);
        viewTopBar();
        mainFrame.addScrollPanel(workoutRoutinesPanel.addWorkoutRoutine(exerciseService.getExercises().toArray(new String[0]),routineArray,getNumberFormatter(0,999)));
        workoutRoutinesPanel.setDeleteExerciseFromRoutineBeingAddedController(this);
    }
    public void addWorkoutRoutine(String routineName,ArrayList<String> exercises, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weights){
        if(workoutRoutineService.routineNameExists(routineName) && currentDisplay==Menus.ADD_ROUTINE
                || workoutRoutineService.routineNameExists(routineName) && currentDisplay==Menus.EDIT_ROUTINE && !routineBeingEdited.equals(routineName)
                || routineName.isEmpty()){

            routineName="Name exists.";
            ArrayList<String> editedRoutine=workoutRoutineService.routineSeparateArraysToOne(routineName,exercises,sets,reps,weights);
            viewTopBar();
            mainFrame.addScrollPanel(workoutRoutinesPanel.addWorkoutRoutine(exerciseService.getExercises().toArray(new String[0]),editedRoutine,getNumberFormatter(0,999)));
            workoutRoutinesPanel.setDeleteExerciseFromRoutineBeingAddedController(this);
        }
        else if(currentDisplay==Menus.ADD_ROUTINE){
            workoutRoutineService.addWorkoutRoutine(exerciseService.getExercisesFromArrayList(exercises),routineName,sets,reps,weights);
            viewWorkoutRoutines();
        }
        else if(currentDisplay==Menus.EDIT_ROUTINE){
            workoutRoutineService.editWorkoutRoutine(routineBeingEdited,exerciseService.getExercisesFromArrayList(exercises),routineName,sets,reps,weights);
            viewWorkoutRoutines();
        }
    }
    public void deleteWorkoutRoutine(String routineName){
        workoutRoutineService.deleteWorkoutRoutine(routineName);
        viewWorkoutRoutines();
    }

    //Training sessions
    public void viewTrainingSessions(){
        ArrayList<ArrayList<String>> workoutSessionsToDisplay=trainingSessionService.prepareSessionsToView();
        ArrayList<ArrayList<String>> datesAndLengths=new ArrayList<>();
        ArrayList<ArrayList<String>> idsAndRoutineNames=new ArrayList<>();
        ArrayList<ArrayList<String>> exercises=new ArrayList<>();
        //date(d,m,y,h,m) -> length -> id -> routine name -> exercises
        ArrayList<String> sessionDateAndLength;
        ArrayList<String> sessionExercises;
        ArrayList<String> sessionIdAndRoutineName;
        for(ArrayList<String> session: workoutSessionsToDisplay){
            sessionDateAndLength=new ArrayList<>();
            sessionExercises=new ArrayList<>();
            sessionIdAndRoutineName=new ArrayList<>();
            for(int i=0; i<6; i++)
                sessionDateAndLength.add(session.get(i));
            for(int i=6; i<8; i++)
                sessionIdAndRoutineName.add(session.get(i));
            for(int i=8; i<session.size(); i++)
                sessionExercises.add(session.get(i));
            datesAndLengths.add(sessionDateAndLength);
            idsAndRoutineNames.add(sessionIdAndRoutineName);
            exercises.add(sessionExercises);
        }
        viewTopBar();
        mainFrame.addScrollPanel(trainingSessionsPanel.viewTrainingSessionsMenu(datesAndLengths,idsAndRoutineNames,exercises));
        trainingSessionsPanel.setDeleteAndEditTrainingSessionController(this);
    }
    public void viewEditTrainingSession(String trainingSessionId){
        ArrayList<String> routine=trainingSessionService.getSessionRoutine(trainingSessionId);
        ArrayList<String> date=trainingSessionService.getSessionDate(trainingSessionId);
        viewTopBar();
        mainFrame.add(trainingSessionsPanel.editTrainingSession(trainingSessionId,date,routine,getNumberFormatter(0,999), dateFormatter()));
    }
    public void startTrainingSessions(String routineName){
        viewTopBar();
        Calendar currentDate = Calendar.getInstance();
        //date(d,m,y,h,m)
        ArrayList<String> dateArray=new ArrayList<>();
        dateArray.add(String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));
        dateArray.add(String.valueOf(currentDate.get(Calendar.MONTH) + 1));
        dateArray.add(String.valueOf(currentDate.get(Calendar.YEAR)));
        dateArray.add(String.valueOf(currentDate.get(Calendar.HOUR_OF_DAY)));
        dateArray.add(String.valueOf(currentDate.get(Calendar.MINUTE)));
        dateArray.add("1");
        mainFrame.addScrollPanel(trainingSessionsPanel.viewActiveSession(dateArray,workoutRoutineService.getRoutineAsArray(routineName),getNumberFormatter(0,999),dateFormatter()));
    }
    public void addTrainingSession(String routineName, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weight, ArrayList<String> date, String length){
        trainingSessionService.addTrainingSession(workoutRoutineService.getRoutineByName(routineName),sets,reps,weight,date,length);
        viewTrainingSessions();
    }
    public void editTrainingSession(String id, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weight, ArrayList<String> date, String length){
        trainingSessionService.editTrainingSession(id, date, length,sets,reps,weight);
        viewTrainingSessions();
    }
    public void deleteTrainingSession(String id){
        trainingSessionService.deleteTrainingSession(id);
        viewTrainingSessions();
    }

    //Goals
    public void viewGoals(String exerciseName){
        viewTopBar();
        if(exerciseService.getExercises().isEmpty()){
            mainFrame.addPanel(goalsPanel.viewGoalsMenu(new String[0], "", new String[0], "","","",getNumberFormatter(0,9999), new ArrayList<>()));
        }
        else{
            goalsService.updateGoals(trainingSessionService);

            String[] goalTypes = goalsService.getGoalTypes().toArray(new String[0]);
            String[] exerciseList = exerciseService.getExercises().toArray(new String[0]);
            if(exerciseName.isEmpty())
                exerciseName=exerciseList[0];

            String goalName=goalsService.getGoalName(exerciseName);
            String goalValue=goalsService.getGoalValue(exerciseName);
            String currentValue=goalsService.getCurrentGoalValue(exerciseName);
            ArrayList<String> goalDeadline=goalsService.getDeadline(exerciseName);

            viewTopBar();
            mainFrame.add(goalsPanel.viewGoalsMenu(goalTypes, exerciseName, exerciseList, goalName, goalValue, currentValue, getNumberFormatter(0,9999), goalDeadline));
        }
        goalsPanel.setViewController(this);
    }

    public void addGoal(String exercise, Date deadline, String goalValue, String goalType){
        ArrayList<Exercise> exercises=exerciseService.getExercisesObjects();
        for (Exercise ex:exercises){
            if(ex.getName().equals(exercise)){
                goalsService.addGoal(ex, goalType, goalValue, trainingSessionService.getBestWeightValue(exercise), trainingSessionService.getExerciseTimesDoneValue(exercise), deadline);
                viewGoals(exercise);
                break;
            }
        }
    }

    public void deleteGoal(String exerciseName){
        goalsService.deleteGoal(exerciseName);
        viewGoals("");
    }

    //Progression charts
    public void viewChart(String exerciseName, Date startDate, Date endDate, String chartType){
        String[] chartTypes= chartsService.getChartTypes();
        String[] exerciseList = exerciseService.getExercises().toArray(new String[0]);
        viewTopBar();
        if(exerciseName.isEmpty() || exerciseList.length==0){
            mainFrame.addPanel(progressChartsPanel.viewProgressMenu(exerciseName,"",new DefaultCategoryDataset(),chartTypes,exerciseList));
        }
        else{
            DefaultCategoryDataset dataset = chartsService.getDataset(trainingSessionService.getTrainingSessions(),exerciseName,startDate,endDate, chartType);
            mainFrame.addPanel(progressChartsPanel.viewProgressMenu(exerciseName,chartType,dataset,chartTypes,exerciseList));
        }
    }



    private NumberFormatter getNumberFormatter(int min, int max){
        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        integerFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(integerFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(true);
        numberFormatter.setCommitsOnValidEdit(true);
        numberFormatter.setMinimum(min);
        numberFormatter.setMaximum(max);
        return numberFormatter;
    }
    public ArrayList<NumberFormatter> dateFormatter(){
        ArrayList<NumberFormatter> dateFormatters=new ArrayList<>();
        dateFormatters.add(getNumberFormatter(1,30)); //day
        dateFormatters.add(getNumberFormatter(1,12)); //month
        dateFormatters.add(getNumberFormatter(1,2024)); //year
        dateFormatters.add(getNumberFormatter(0,24)); //hour
        dateFormatters.add(getNumberFormatter(0,60)); //minute
        return dateFormatters;
    }
}
