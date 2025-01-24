public class Deadline extends Task {
    String endDate;

    public Deadline(String name, String endDate) {
        super(name);
        this.endDate = endDate;
    }

    public String taskString(){
        String mark = isCompleted ? "[X]" : "[ ]";

        return "[D] " + mark + " " + this.name + " (by: " + this.endDate + ")";
    }
}
