package ca.cmpt213.a4.control;

import com.google.gson.*;
import ca.cmpt213.a4.model.TaskInformation;

import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 * A class that creates a ManageTasks object with several options that can be used to edit certain things from an ArrayList of tasks.
 *
 * @author Daven Chohan
 */
public class ManageTasks {

    private final List<TaskInformation> tasks;

    public ManageTasks() {
        tasks = new ArrayList<>();
    }

    public List<TaskInformation> getTasks() {
        sortTask();
        return tasks;
    }

    public void addTask(String date, String name, String notes, String time) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        month--;
        tasks.add(new TaskInformation(false, name, notes, new GregorianCalendar(year, month, day, hour, minute)));
    }

    public void removeTask(TaskInformation task) {
        tasks.remove(task);
    }

    public void completeTask(int choice) {
        tasks.get(choice - 1).setCompleted(true);
    }

    public void incompleteTask(int choice) {
        tasks.get(choice - 1).setCompleted(false);
    }

    private void sortTask() {
        int taskSize = tasks.size();
        TaskInformation tempData;
        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < taskSize - 1; i++) {
                if (tasks.get(i).getDueDate().compareTo(tasks.get(i + 1).getDueDate()) > 0) {
                    tempData = tasks.get(i);
                    tasks.set(i, tasks.get(i + 1));
                    tasks.set(i + 1, tempData);
                    isSorted = false;
                }
            }
        }
    }

    public List<TaskInformation> overdueTasks() {
        sortTask();
        List<TaskInformation> overDue = new ArrayList<>();
        GregorianCalendar currentTime = new GregorianCalendar();
        for (TaskInformation task : tasks) {
            if (task.getDueDate().compareTo(currentTime) < 0 && !task.isCompleted()) {
                overDue.add(task);
            }
        }
        return overDue;
    }

    public List<TaskInformation> upcomingTasks() {
        sortTask();
        List<TaskInformation> upcoming = new ArrayList<>();
        GregorianCalendar currentTime = new GregorianCalendar();
        for (TaskInformation task : tasks) {
            if (task.getDueDate().compareTo(currentTime) > 0 && !task.isCompleted()) {
                upcoming.add(task);
            }
        }
        return upcoming;
    }

    public void createJSON() {
        // Learned partly from GSON tutorial from Future Studio
        String directory = System.getProperty("user.dir") + "/data.json";
        Gson gson = new Gson();
        String json = gson.toJson(tasks);
        try {
            FileWriter fileWriter = new FileWriter(directory);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred in writing to the json file.");
            e.printStackTrace();
        }
    }

    public List<String> generateTask() {
        // How to run the curl command learned from https://www.baeldung.com/java-curl
        String command = "curl -X GET https://www.boredapi.com/api/activity";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert process != null;
        InputStream inputStream = process.getInputStream();
        Scanner console = new Scanner(inputStream).useDelimiter("\\A");
        String input = console.next();
        JsonElement consoleElement = JsonParser.parseString(input);
        JsonObject taskJsonObject = consoleElement.getAsJsonObject();
        List<String> newTask = new ArrayList<>();
        String name = taskJsonObject.get("activity").getAsString();
        newTask.add(name);
        String type = taskJsonObject.get("type").getAsString();
        String participants = taskJsonObject.get("participants").getAsString();
        String price = taskJsonObject.get("price").getAsString();
        String notes = "type: " + type + ", " + "participants: " + participants + ", " + "price: " + price;
        newTask.add(notes);
        return newTask;
    }

    public void readJSON() {
        // Learned from Professor Brian Fraser's tutorial on JSON
        String directory = System.getProperty("user.dir") + "/data.json";
        File myFile = new File(directory);
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred in creating the json file.");
            e.printStackTrace();
        }
        try {
            if (myFile.length() == 0) {
                return;
            }
            JsonElement fileElement = JsonParser.parseReader(new FileReader(myFile));

            // Processing all tasks
            JsonArray jsonArrayOfTasks = fileElement.getAsJsonArray();
            for (JsonElement taskElement : jsonArrayOfTasks) {
                // Get the JsonObject:
                JsonObject taskJsonObject = taskElement.getAsJsonObject();

                // Extract data
                String name = taskJsonObject.get("name").getAsString();
                String notes = taskJsonObject.get("notes").getAsString();
                boolean isCompleted = taskJsonObject.get("isCompleted").getAsBoolean();
                JsonObject tempJsonObject = taskJsonObject.get("dueDate").getAsJsonObject();

                int year = tempJsonObject.get("year").getAsInt();
                int month = tempJsonObject.get("month").getAsInt();
                int day = tempJsonObject.get("dayOfMonth").getAsInt();
                int hour = tempJsonObject.get("hourOfDay").getAsInt();
                int minute = tempJsonObject.get("minute").getAsInt();

                tasks.add(new TaskInformation(isCompleted, name, notes, new GregorianCalendar(year, month, day, hour, minute)));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found.");
            e.printStackTrace();
        }
    }

}
