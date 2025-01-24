public class Event extends Task {
    String startDate;
    String endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String taskString(){
        String mark = isCompleted ? "[X]" : "[ ]";

        return "[E] " + mark + " " + this.name + " (from: " + this.startDate + " to: " + this.endDate + ")";
    }
}
