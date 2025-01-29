package ruibot.tasks;

public class Deadline extends Task {
    String endDate;

    public Deadline(String name, boolean isCompleted, String endDate) {
        super(name, isCompleted);
        this.endDate = endDate;
    }

    public String taskString(){
        String mark = isCompleted ? "[X]" : "[ ]";

        return "[D] " + mark + " " + this.name + " (by: " + this.endDate + ")";
    }
}
