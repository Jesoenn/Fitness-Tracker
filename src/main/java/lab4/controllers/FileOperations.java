package lab4.controllers;

import lab4.workout.TrainingSession;

import java.io.*;
import java.util.ArrayList;

public class FileOperations {
    private final String exercisePath="exercises.ser";
    private final String workoutRoutinePath="workoutRoutines.ser";
    private final String trainingSessionPath="trainingSessions.ser";
    private final String goalPath="goals.ser";
    public enum ListType{
        EXERCISES, WORKOUT_ROUTINES, TRAINING_SESSIONS, GOALS
    }
    public FileOperations(){

    }

    public <T> ArrayList<T> loadList(ListType listType) {
        String filePath=switch(listType){
            case EXERCISES -> exercisePath;
            case WORKOUT_ROUTINES -> workoutRoutinePath;
            case TRAINING_SESSIONS -> trainingSessionPath;
            case GOALS -> goalPath;
        };

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            ArrayList<T> list = (ArrayList<T>) ois.readObject();
            System.out.println("List loaded successfully from: " + filePath);
            return list;
        } catch (Exception e) {
            System.out.println("Error loading list from: " + filePath);
            return new ArrayList<>();
        }
    }

    public <T> void saveList(ArrayList<T> arrayList, ListType listType){
        String filePath=switch(listType){
            case EXERCISES -> exercisePath;
            case WORKOUT_ROUTINES -> workoutRoutinePath;
            case TRAINING_SESSIONS -> trainingSessionPath;
            case GOALS -> goalPath;
        };

        try{
            FileOutputStream fileOutputStream=new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);

            if(!arrayList.isEmpty() && listType == ListType.TRAINING_SESSIONS){
                TrainingSession trainingSession=(TrainingSession)arrayList.getFirst();
                trainingSession.saveTotalSessions();
            }
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error saving "+filePath+" list");
            throw new RuntimeException(e);
        }
    }
}
