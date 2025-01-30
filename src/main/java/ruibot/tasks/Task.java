package ruibot.tasks;

public abstract class Task {
    String name;
    boolean isCompleted;

    public Task(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public boolean isTaskCompleted() {
        return this.isCompleted;
    }
    public void changeStatus() {
        this.isCompleted = !this.isCompleted;
    }

    public abstract String taskString();

    public boolean contains(String keyword) {
        return this.name.contains(keyword);
    }
}
