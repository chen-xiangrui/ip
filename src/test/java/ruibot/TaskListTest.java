package ruibot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ruibot.tasks.Task;
import ruibot.tasks.ToDo;

import java.util.ArrayList;

public class TaskListTest {
    @Test
    public void getTasks_oneTask_success() throws Exception {
        Task task = new ToDo("run", false);
        String line = "[T] [ ] run";
        ArrayList<String> lines = new ArrayList<>();
        lines.add(line);
        assertEquals(task.taskString(), new TaskList(lines).getTasks().get(0).taskString());
    }
}
