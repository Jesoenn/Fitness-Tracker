package lab4.workout;

import lab4.interfaces.IExercise;

import java.io.Serializable;

public class Exercise implements IExercise, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    public Exercise(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
