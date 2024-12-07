package lab4.workout;

import java.io.Serializable;
import java.util.Date;

public class Goal implements Serializable {
    private static final long serialVersionUID = 1L;
    private GoalType goalType;
    private Exercise exercise;
    private int goalValue;
    private int currentValue;
    private Date deadline;
    public enum GoalType{
        EXERCISE_WEIGHT, EXERCISE_COUNT
    }
    public Goal(GoalType goalType, Exercise exercise) {
        this.goalType = goalType;
        this.exercise = exercise;
    }


    public GoalType getGoalType() {
        return goalType;
    }
    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }
    public Exercise getExercise() {
        return exercise;
    }
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    public int getCurrentValue() {
        return currentValue;
    }
    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }
    public int getGoalValue() {
        return goalValue;
    }
    public void setGoalValue(int goalValue) {
        this.goalValue = goalValue;
    }
    public Date getDeadline() {
        return deadline;
    }
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
