public class ToDo extends Task {

    public ToDo(String name) {
        super(name);
    }

    public String taskString(){
        String mark = isCompleted ? "[X]" : "[ ]";

        return "[T] " + mark + " " + this.name;
    }
}
