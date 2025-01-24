abstract class Task {
    String name;
    boolean isCompleted;

    public Task(String name) {
        this.name = name;
        this.isCompleted = false;
    }

    public void changeStatus() {
        this.isCompleted = !this.isCompleted;
    }

    public abstract String taskString();
}
