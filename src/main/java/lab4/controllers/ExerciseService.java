package lab4.controllers;

import lab4.workout.Exercise;

import java.util.ArrayList;

public class ExerciseService {
    private ArrayList<Exercise> exercises;
    public ExerciseService() {
        exercises = new ArrayList<>();
    }

    public void addExercise(String name) {
        exercises.add(new Exercise(name));
    }
    public ArrayList<String> getExercises() {
        ArrayList<String> list = new ArrayList<>();
        for (Exercise exercise : exercises) {
            list.add(exercise.getName());
        }
        return list;
    }
    public boolean exerciseNameExists(String name) {
        for (Exercise exercise : exercises) {
            if (exercise.getName().equals(name))
                return true;
        }
        return false;
    }
    public void deleteExercise(String name) {
        for (Exercise exercise : exercises) {
            if(exercise.getName().equals(name)){
                exercises.remove(exercise);
                break;
            }
        }
    }
    public ArrayList<Exercise> getExercisesFromArrayList(ArrayList<String> exerciseNames) {
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        for(String name: exerciseNames){
            for(Exercise exercise: exercises){
                if(exercise.getName().equals(name))
                    exerciseList.add(exercise);
            }
        }
        return exerciseList;
    }
    public ArrayList<Exercise> getExercisesObjects() {
        return exercises;
    }
    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }
}
