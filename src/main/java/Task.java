abstract class Task {
    String name;
    boolean isCompleted;

    public Task(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public void changeStatus() {
        this.isCompleted = !this.isCompleted;
    }

    public abstract String taskString();
}
