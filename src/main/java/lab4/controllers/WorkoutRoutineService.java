package lab4.controllers;

import lab4.workout.Exercise;
import lab4.workout.ExerciseDetail;
import lab4.workout.WorkoutRoutine;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WorkoutRoutineService {
    private ArrayList<WorkoutRoutine> workoutRoutines;
    public WorkoutRoutineService(){
        workoutRoutines = new ArrayList<>();
    }
    public void addWorkoutRoutine(ArrayList<Exercise> exerciseList, String workoutName, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weights){
        WorkoutRoutine routine=new WorkoutRoutine(workoutName);
        for(int i=0; i<exerciseList.size(); i++){
            routine.addExercise(exerciseList.get(i),Integer.parseInt(sets.get(i)),Integer.parseInt(reps.get(i)),Integer.parseInt(weights.get(i)));
        }
        workoutRoutines.add(routine);
    }
    public boolean routineNameExists(String routineName){
        for (WorkoutRoutine workoutRoutine : workoutRoutines) {
            if (workoutRoutine.getRoutineName().equals(routineName)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<ArrayList<String>> getAllRoutinesAsStrings(){
        ArrayList<ArrayList<String>> allRoutineArray=new ArrayList<>();
        ArrayList<String> routineArray;
        for(WorkoutRoutine workoutRoutine : workoutRoutines){
            routineArray=new ArrayList<>();
            routineArray.add(workoutRoutine.getRoutineName());
            routineArray.add("Exercise");
            routineArray.add("Sets");
            routineArray.add("Reps");
            routineArray.add("Weight");
            for(ExerciseDetail e: workoutRoutine.getExercises()){
                routineArray.add(e.getExercise().getName());
                routineArray.add(String.valueOf(e.getSets()));
                routineArray.add(String.valueOf(e.getRepetitions()));
                routineArray.add(String.valueOf(e.getWeight()));
            }
            allRoutineArray.add(routineArray);
        }
        return allRoutineArray;
    }

    public void deleteWorkoutRoutine(String routineName){
        for(WorkoutRoutine workoutRoutine : workoutRoutines){
            if(workoutRoutine.getRoutineName().equals(routineName)){
                workoutRoutines.remove(workoutRoutine);
                break;
            }
        }
    }


    public ArrayList<String> routineSeparateArraysToOne(String routineName,ArrayList<String> exercises, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weights){
        ArrayList<String> editedRoutine=new ArrayList<>();
        editedRoutine.add(routineName);
        for(int i=0; i<exercises.size(); i++){
            editedRoutine.add(exercises.get(i));
            editedRoutine.add(sets.get(i));
            editedRoutine.add(reps.get(i));
            editedRoutine.add(weights.get(i));
        }
        return editedRoutine;
    }

    public void deleteExercise(String exerciseName){
        ArrayList<ExerciseDetail> exercisesToDelete;
        for(WorkoutRoutine workoutRoutine : workoutRoutines){
            exercisesToDelete=new ArrayList<>();
            for(ExerciseDetail e: workoutRoutine.getExercises()){
                if(e.getExercise().getName().equals(exerciseName))
                    exercisesToDelete.add(e);
            }
            workoutRoutine.getExercises().removeAll(exercisesToDelete);
        }
    }
    public ArrayList<String> getRoutineAsArray(String routineName){
        WorkoutRoutine routine;
        ArrayList<String> routineArray=new ArrayList<>();
        for(WorkoutRoutine workoutRoutine : workoutRoutines){
            if(workoutRoutine.getRoutineName().equals(routineName)){
                routine=workoutRoutine;
                routineArray.add(routine.getRoutineName());
                for(ExerciseDetail e: routine.getExercises()){
                    routineArray.add(e.getExercise().getName());
                    routineArray.add(String.valueOf(e.getSets()));
                    routineArray.add(String.valueOf(e.getRepetitions()));
                    routineArray.add(String.valueOf(e.getWeight()));
                }
                break;
            }
        }
        return routineArray;
    }

    public void editWorkoutRoutine(String originalRoutineName,ArrayList<Exercise> exerciseList, String workoutName, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weights){
        for(WorkoutRoutine workoutRoutine : workoutRoutines){
            if(workoutRoutine.getRoutineName().equals(originalRoutineName)){
                workoutRoutine.setRoutineName(workoutName);
                workoutRoutine.getExercises().clear();
                for(int i=0; i<exerciseList.size(); i++)
                    workoutRoutine.addExercise(exerciseList.get(i),Integer.parseInt(sets.get(i)),Integer.parseInt(reps.get(i)),Integer.parseInt(weights.get(i)));
                break;
            }
        }
    }

    public WorkoutRoutine getRoutineByName(String name){
        for(WorkoutRoutine workoutRoutine : workoutRoutines){
            if(workoutRoutine.getRoutineName().equals(name))
                return workoutRoutine;
        }
        return null;
    }

    public ArrayList<WorkoutRoutine> getWorkoutRoutines(){
        return workoutRoutines;
    }
    public void setWorkoutRoutines(ArrayList<WorkoutRoutine> workoutRoutines){
        this.workoutRoutines = workoutRoutines;
    }
}
