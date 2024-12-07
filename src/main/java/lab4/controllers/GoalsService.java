package lab4.controllers;

import lab4.workout.Exercise;
import lab4.workout.Goal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GoalsService {
    private ArrayList<Goal> goals;
    public GoalsService() {
        goals = new ArrayList<>();
    }

    public void addGoal(Exercise exercise, String goalTypeText, String goalValue, int bestWeightValue, int timesDoneValue, Date deadline){
        Goal.GoalType goalType=switch(goalTypeText){
            case "Max weight" -> Goal.GoalType.EXERCISE_WEIGHT;
            case "Times completed" -> Goal.GoalType.EXERCISE_COUNT;
            default -> throw new IllegalStateException("Unexpected value: " + goalTypeText);
        };

        Goal newGoal=getGoal(exercise.getName());
        if(newGoal==null)
            newGoal=new Goal(goalType, exercise);
        else
            newGoal.setGoalType(goalType);

        newGoal.setGoalValue(Integer.parseInt(goalValue));
        int currentValue=0;
        if(newGoal.getGoalType()==Goal.GoalType.EXERCISE_WEIGHT)
            currentValue=bestWeightValue;
        else if(newGoal.getGoalType()==Goal.GoalType.EXERCISE_COUNT)
            currentValue=timesDoneValue;
        newGoal.setCurrentValue(currentValue);
        newGoal.setDeadline(deadline);
        goals.add(newGoal);
    }

    public Goal getGoal(String exerciseName){
        for(Goal goal: goals){
            if(goal.getExercise().getName().equals(exerciseName))
                return goal;
        }
        return null;
    }

    public ArrayList<String> getGoalTypes(){
        ArrayList<String> goalTypes = new ArrayList<>();
        goalTypes.add("Max weight");
        goalTypes.add("Times completed");
        return goalTypes;
    }

    public String getGoalName(String exercise){
        for(Goal goal: goals){
            if(goal.getExercise().getName().equals(exercise)){
                return switch(goal.getGoalType()){
                    case EXERCISE_WEIGHT -> "Max weight";
                    case EXERCISE_COUNT -> "Times completed";
                };
            }
        }
        return "";
    }
    public String getGoalValue(String exercise){
        for(Goal goal: goals){
            if(goal.getExercise().getName().equals(exercise)){
                return Integer.toString(goal.getGoalValue());
            }
        }
        return "";
    }
    public String getCurrentGoalValue(String exercise){
        for(Goal goal: goals){
            if(goal.getExercise().getName().equals(exercise)){
                return Integer.toString(goal.getCurrentValue());
            }
        }
        return "";
    }
    public ArrayList<String> getDeadline(String exercise){
        ArrayList<String> deadline = new ArrayList<>();
        for(Goal goal: goals){
            if(goal.getExercise().getName().equals(exercise)){
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(goal.getDeadline());

                deadline.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                deadline.add(String.valueOf(calendar.get(Calendar.MONTH) + 1));
                deadline.add(String.valueOf(calendar.get(Calendar.YEAR)));

            }
        }
        return deadline;
    }

    public void deleteGoal(String exercise){
        for(Goal goal: goals){
            if(goal.getExercise().getName().equals(exercise)){
                goals.remove(goal);
                return;
            }
        }
    }

    public void updateGoals(TrainingSessionService trainingSessionService){
        ArrayList<Goal> goalsToRemove=new ArrayList<>();

        for(Goal goal: goals){
            if(goal.getDeadline().before(new Date()))
                goalsToRemove.add(goal);
            Goal.GoalType goalType=goal.getGoalType();
            if(goalType==Goal.GoalType.EXERCISE_WEIGHT)
                goal.setCurrentValue(trainingSessionService.getBestWeightValue(goal.getExercise().getName()));
            else if(goalType==Goal.GoalType.EXERCISE_COUNT)
                goal.setCurrentValue(trainingSessionService.getExerciseTimesDoneValue(goal.getExercise().getName()));
        }
        goals.removeAll(goalsToRemove);
    }
    public ArrayList<Goal> getGoalsList(){
        return goals;
    }
    public void setGoals(ArrayList<Goal> goals){
        this.goals=goals;
    }
}
