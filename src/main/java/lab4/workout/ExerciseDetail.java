package lab4.workout;

import java.io.Serializable;

public class ExerciseDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private Exercise exercise;
    private int sets, repetitions, weight;

    public ExerciseDetail(Exercise exercise, int sets, int repetitions, int weight) {
        this.exercise = exercise;
        this.sets = sets;
        this.repetitions = repetitions;
        this.weight = weight;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public int getSets() {
        return sets;
    }

    public int getRepetitions() {
        return repetitions;
    }
    public int getWeight() {
        return weight;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

}
