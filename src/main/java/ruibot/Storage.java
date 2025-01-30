package ruibot;

import java.io.IOException;

import ruibot.tasks.Task;

import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;

import java.util.Scanner;

public class Storage {
    private File file;
    private Scanner scanner;
    private String filePath;

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

    public ArrayList<String> load() {
        ArrayList<String> lines = new ArrayList<>();
        while (this.scanner.hasNext()) {
            String line = this.scanner.nextLine();
            lines.add(line);
        }
        return lines;
    }

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
