package lab4.controllers;

import lab4.workout.ExerciseDetail;
import lab4.workout.TrainingSession;
import lab4.workout.WorkoutRoutine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TrainingSessionService {
    private ArrayList<TrainingSession> trainingSessions;
    public TrainingSessionService() {
        trainingSessions = new ArrayList<>();
    }
    public ArrayList<ArrayList<String>> prepareSessionsToView(){
        ArrayList<ArrayList<String>> workoutSessionsToDisplay=new ArrayList<>();
        // date(d,m,y,h,m) -> length -> id -> routine name -> exercises
        ArrayList<String> workoutSession;
        for(TrainingSession w: trainingSessions){
                workoutSession=new ArrayList<>();
                workoutSession.addAll(getSessionDate(String.valueOf(w.getId())));
                workoutSession.add(Integer.toString(w.getId()));
                workoutSession.add(w.getRoutineName());

            for(ExerciseDetail e: w.getExercises()){
                workoutSession.add(e.getExercise().getName());
                workoutSession.add(Integer.toString(e.getSets()));
                workoutSession.add(Integer.toString(e.getRepetitions()));
                workoutSession.add(Integer.toString(e.getWeight()));
            }
            workoutSessionsToDisplay.add(workoutSession);
        }
        return workoutSessionsToDisplay;
    }

    public ArrayList<String> getSessionDate(String id){
        ArrayList<String> sessionDate=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        for(TrainingSession t: trainingSessions){
            if(t.getId()==Integer.parseInt(id)){
                calendar.setTime(t.getSessionStartTime());
                sessionDate.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                sessionDate.add(String.valueOf(calendar.get(Calendar.MONTH) + 1)); // od 0
                sessionDate.add(String.valueOf(calendar.get(Calendar.YEAR)));
                sessionDate.add(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
                sessionDate.add(String.valueOf(calendar.get(Calendar.MINUTE)));
                sessionDate.add(Integer.toString(t.getSessionDuration()));
            }
        }
        return sessionDate;
    }

    public void addTrainingSession(WorkoutRoutine usedRoutine, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weight, ArrayList<String> date, String length){
        ArrayList<Integer> setsValues = stringToIntegerArrayList(sets);
        ArrayList<Integer> repsValues = stringToIntegerArrayList(reps);
        ArrayList<Integer> weightValues = stringToIntegerArrayList(weight);

        TrainingSession newTrainingSession=new TrainingSession(usedRoutine,setsValues,repsValues,weightValues);
        newTrainingSession.setSessionStartTime(createDate(date));
        newTrainingSession.setSessionDuration(Integer.parseInt(length));
        trainingSessions.add(newTrainingSession);
    }

    public void editTrainingSession(String id, ArrayList<String> date, String length, ArrayList<String> sets, ArrayList<String> reps, ArrayList<String> weight){
        ArrayList<Integer> setsValues = stringToIntegerArrayList(sets);
        ArrayList<Integer> repsValues = stringToIntegerArrayList(reps);
        ArrayList<Integer> weightValues = stringToIntegerArrayList(weight);

        for(TrainingSession t: trainingSessions){
            if(t.getId()==Integer.parseInt(id)){
                t.setSessionStartTime(createDate(date));
                t.setSessionDuration(Integer.parseInt(length));
                ArrayList<ExerciseDetail> exercises=t.getExercises();
                for(int i=0; i<exercises.size(); i++){
                    exercises.get(i).setSets(setsValues.get(i));
                    exercises.get(i).setRepetitions(repsValues.get(i));
                    exercises.get(i).setWeight(weightValues.get(i));
                }
                return;
            }
        }
    }

    public ArrayList<String> getSessionRoutine(String id){
        ArrayList<String> routine=new ArrayList<>();
        for(TrainingSession t: trainingSessions){
            if(t.getId()==Integer.parseInt(id)){
                routine.add(t.getRoutineName());
                for(ExerciseDetail e: t.getExercises()){
                    routine.add(e.getExercise().getName());
                    routine.add(Integer.toString(e.getSets()));
                    routine.add(Integer.toString(e.getRepetitions()));
                    routine.add(Integer.toString(e.getWeight()));
                }
                break;
            }
        }
        return routine;
    }


    public Date createDate(ArrayList<String> date){
        int day = Integer.parseInt(date.get(0));
        int month = Integer.parseInt(date.get(1)) - 1 ; // Miesiace w Calendar od 0
        int year = Integer.parseInt(date.get(2));
        int hour = Integer.parseInt(date.get(3));
        int minute = Integer.parseInt(date.get(4));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 0);
        return calendar.getTime();
    }
    public ArrayList<Integer> stringToIntegerArrayList(ArrayList<String> stringList){
        ArrayList<Integer> integerList=new ArrayList<>();
        for(String s: stringList)
            integerList.add(Integer.parseInt(s));
        return integerList;
    }

    public void deleteTrainingSession(String id){
        for(TrainingSession t: trainingSessions){
            if(String.valueOf(t.getId()).equals(id)){
                trainingSessions.remove(t);
                break;
            }
        }
    }


    public int getBestWeightValue(String exerciseName){
        int bestWeight=0;
        for(TrainingSession ts: trainingSessions){
            ArrayList<ExerciseDetail> exercises=ts.getExercises();
            for(int i=0; i<exercises.size(); i++){
                ExerciseDetail e=exercises.get(i);
                if(e.getExercise().getName().equals(exerciseName) && e.getWeight()>bestWeight)
                    bestWeight=e.getWeight();
            }
        }
        return bestWeight;
    }

    public int getExerciseTimesDoneValue(String exerciseName){
        int timesDone=0;
        for(TrainingSession ts: trainingSessions){
            ArrayList<ExerciseDetail> exercises=ts.getExercises();
            for (ExerciseDetail e : exercises)
                if (e.getExercise().getName().equals(exerciseName))
                    timesDone += 1;
        }
        return timesDone;
    }

    public ArrayList<TrainingSession> getTrainingSessions(){
        return trainingSessions;
    }
    public void setTrainingSessions(ArrayList<TrainingSession> trainingSessions){
        this.trainingSessions = trainingSessions;
    }
}
