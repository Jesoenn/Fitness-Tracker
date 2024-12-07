package lab4.workout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class TrainingSession extends WorkoutRoutine implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int totalSessions=0;
    private int saveTotalSessions;
    private int id;
    private Date sessionStartTime;
    private int sessionDuration;

    public TrainingSession(WorkoutRoutine routine, ArrayList<Integer> sets, ArrayList<Integer> reps, ArrayList<Integer> weight) {
        super(routine.getRoutineName());
        id=totalSessions;
        totalSessions++;
        this.sessionDuration = 0;
        addExercises(sets,reps,weight,routine.getExercises());
    }

    public void addExercises(ArrayList<Integer> sets, ArrayList<Integer> reps, ArrayList<Integer> weight, ArrayList<ExerciseDetail> exercises){
        for(int i=0; i<exercises.size(); i++){
            this.addExercise(exercises.get(i).getExercise(),sets.get(i),reps.get(i),weight.get(i));
        }
    }

    public int getId() {
        return id;
    }
    public Date getSessionStartTime() {
        return sessionStartTime;
    }
    public void setSessionStartTime(Date sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }
    public int getSessionDuration() {
        return sessionDuration;
    }
    public void setSessionDuration(int length) {
        this.sessionDuration =length;
    }
    public static void setTotalSessions(int totalSessions) {
        TrainingSession.totalSessions = totalSessions;
    }
    public void saveTotalSessions() {
        saveTotalSessions=totalSessions;
    }
    public void loadTotalSessions() {
        totalSessions=saveTotalSessions;
    }
}
