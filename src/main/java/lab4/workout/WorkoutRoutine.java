package lab4.workout;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkoutRoutine implements Serializable {
    private static final long serialVersionUID = 1L;
    private String routineName;
    private ArrayList<ExerciseDetail> exerciseDetails;

    public WorkoutRoutine(String routineName) {
        this.routineName = routineName;
        exerciseDetails=new ArrayList<>();
    }

    public void addExercise(Exercise exercise, int sets, int repetitions, int weight) {
        exerciseDetails.add(new ExerciseDetail(exercise, sets, repetitions,weight));
    }

    public ArrayList<ExerciseDetail> getExercises() {
        return exerciseDetails;
    }


    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }
}
