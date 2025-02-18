package ruibot;

import java.io.IOException;

import ruibot.tasks.Task;

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
    private String fileDirectory;
    private String fileName;

    /**
     * Constructor to initialise Storage with the filepath of file storing the tasks.
     *
     * @param fileDirectory The file directory of file storing the tasks.
     * @param fileName The file name storing the tasks.
     */
    public Storage(String fileDirectory, String fileName) {
        this.fileDirectory = fileDirectory;
        this.fileName = fileName;
        try {
            File directory = new File(this.fileDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            this.file = new File(this.fileDirectory + this.fileName);
            if (!this.file.exists()){
                this.file.createNewFile();
            }
            this.scanner = new Scanner(this.file);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Load the tasks stored in ruibot.txt.
     *
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
     *
     * @param tasks List of tasks to be stored.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(this.fileDirectory + this.fileName, false);
            for (Task task : tasks) {
                fw.write(task.taskString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
