package lab4;

import lab4.controllers.Controller;
import lab4.ui.*;

public class Main{
    public static void main(String[] args){
        int width=1000;
        int height=600;
        Frame frame = new Frame(width,height);
        ViewTopBar topBarPanel = new ViewTopBar(width,height);
        ViewExercises exercisesPanel = new ViewExercises(width,height);
        ViewWorkoutRoutines workoutRoutinePanel = new ViewWorkoutRoutines(width,height);
        ViewTrainingSessions trainingSessionsPanel = new ViewTrainingSessions(width,height);
        ViewGoals goalsPanel = new ViewGoals(width,height);
        ViewProgressCharts progressChartsPanel = new ViewProgressCharts(width,height);
        Controller controller=new Controller(frame,topBarPanel,exercisesPanel,workoutRoutinePanel,trainingSessionsPanel, goalsPanel,progressChartsPanel);
    }
}