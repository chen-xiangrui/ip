package ruibot.tasks;

public class ToDo extends Task {

    public ToDo(String name, boolean isCompleted) {
        super(name, isCompleted);
    }

    public String taskString(){
        String mark = isCompleted ? "[X]" : "[ ]";

        return "[T] " + mark + " " + this.name;
    }

    public boolean containsDate(String date) {
        return false;
    }
}
