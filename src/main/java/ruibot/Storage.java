package ruibot;

import ruibot.tasks.Task;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Storage class handles the file operations, loading and saving tasks into the ruibot.txt.
 */
public class Storage {
    private File file;
    private Scanner scanner;
    private String filePath;

    /**
     * Constructor to initialise Storage with the filepath of ruibot.txt.
     * @param filePath The filepath of ruibot.txt.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        try {
            this.file = new File(this.filePath);
            file.createNewFile();
            this.scanner = new Scanner(this.file);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Load the tasks stored in ruibot.txt.
     * @return List of strings with each string containing the task.
     */
    public ArrayList<String> load() {
        ArrayList<String> lines = new ArrayList<>();
        while (this.scanner.hasNext()) {
            String line = this.scanner.nextLine();
            lines.add(line);
        }
        return lines;
    }

    /**
     * Save the tasks into ruibot.txt.
     * @param tasks List of tasks to be stored.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(this.filePath, false);
            for (Task task : tasks) {
                fw.write(task.taskString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
