package ca.cmpt213.a4.view;

import ca.cmpt213.a4.control.ManageTasks;
import ca.cmpt213.a4.model.TaskInformation;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Creates the GUI of the Task Tracker by using Java Swing. Incorporates methods from the ManageTasks class to
 * make use of the methods within the GUI.
 *
 * @author Daven Chohan
 */

public class MainUI implements ActionListener, ItemListener {
    JToggleButton allButton;
    JToggleButton overdueButton;
    JToggleButton upcomingButton;
    JFrame applicationFrame;
    JPanel mainPanel;
    JPanel buttonPanel;
    JPanel taskList;
    JTextField nameInput;
    JTextField notesInput;
    JDialog newWindow;
    DatePicker datePicker;
    TimePicker timePicker;
    TimePickerSettings timePickerSettings;
    private final ManageTasks manageTasks;

    public MainUI() {
        manageTasks = new ManageTasks();
        manageTasks.readJSON();
        applicationFrame = new JFrame("My To-Do List");
        applicationFrame.setSize(600, 500);
        applicationFrame.setResizable(false);
        applicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        applicationFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                applicationFrame.dispose();
                manageTasks.createJSON();
                System.exit(0);
            }
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        allButton = new JToggleButton("All");
        allButton.setSelected(true);
        allButton.setEnabled(false);
        allButton.addActionListener(this);
        overdueButton = new JToggleButton("Overdue");
        overdueButton.addActionListener(this);
        upcomingButton = new JToggleButton("Upcoming");
        upcomingButton.addActionListener(this);
        buttonPanel.add(allButton);
        buttonPanel.add(overdueButton);
        buttonPanel.add(upcomingButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(buttonPanel);

        taskList = new JPanel();
        taskList.setLayout(new BoxLayout(taskList, BoxLayout.Y_AXIS));
        displayTasks();

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setMaximumSize(new Dimension(500, 300));
        mainPanel.add(scrollPane);

        JPanel bottomPanel = new JPanel();
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setPreferredSize(new Dimension(150, 35));
        addTaskButton.addActionListener(this);
        JButton creditsButton = new JButton("Credits");
        creditsButton.setPreferredSize(new Dimension(150, 35));
        creditsButton.addActionListener(this);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        bottomPanel.add(addTaskButton);
        bottomPanel.add(creditsButton);
        mainPanel.add(bottomPanel);

        applicationFrame.add(mainPanel);
        applicationFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Credits")){
            JOptionPane.showMessageDialog(null, "This program is created by Daven", "Credits",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getActionCommand().equals("Add Task")) {
            newWindow = new JDialog(applicationFrame, "Add task");
            JPanel addTaskWindow = new JPanel();
            newWindow.setSize(400, 350);
            addTaskWindow.setLayout(new BoxLayout(addTaskWindow, BoxLayout.Y_AXIS));

            JPanel namePanel = new JPanel();
            namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
            JPanel notesPanel = new JPanel();
            notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.X_AXIS));
            JPanel dueDatePanel = new JPanel();
            dueDatePanel.setLayout(new BoxLayout(dueDatePanel, BoxLayout.X_AXIS));
            JPanel timePanel = new JPanel();
            timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

            JLabel nameLabel = new JLabel("Name: ");
            nameInput = new JTextField(20);
            Font otherFont = nameInput.getFont().deriveFont(Font.PLAIN, 15f);
            nameInput.setFont(otherFont);
            nameInput.setMaximumSize(new Dimension(300, nameInput.getPreferredSize().height));
            namePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            namePanel.add(nameLabel);
            namePanel.add(nameInput);

            JLabel notesLabel = new JLabel("Notes: ");
            notesInput = new JTextField(20);
            notesInput.setFont(otherFont);
            notesInput.setMaximumSize(new Dimension(300, notesInput.getPreferredSize().height));
            notesPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            notesPanel.add(notesLabel);
            notesPanel.add(notesInput);


            JLabel dueDateLabel = new JLabel("Due date: ");
            datePicker = new DatePicker();
            datePicker.setMaximumSize(new Dimension(155, datePicker.getPreferredSize().height));
            dueDatePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            dueDatePanel.add(dueDateLabel);
            dueDatePanel.add(datePicker);

            JLabel timeLabel = new JLabel("Time due: ");
            timePickerSettings = new TimePickerSettings();
            timePickerSettings.use24HourClockFormat();
            timePicker = new TimePicker(timePickerSettings);
            timePicker.setMaximumSize(new Dimension(90, timePicker.getPreferredSize().height));
            timePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            timePanel.add(timeLabel);
            timePanel.add(timePicker);
            dueDatePanel.add(timePanel);

            JButton createButton = new JButton("Create");
            createButton.addActionListener(this);
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(this);
            JButton generateButton = new JButton("Generate");
            generateButton.addActionListener(this);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
            buttonPanel.add(generateButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(100, 0)));
            buttonPanel.add(createButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
            buttonPanel.add(cancelButton);

            addTaskWindow.add(namePanel);
            addTaskWindow.add(notesPanel);
            addTaskWindow.add(dueDatePanel);
            addTaskWindow.add(buttonPanel);
            newWindow.add(addTaskWindow);
            newWindow.setModal(true);
            newWindow.setVisible(true);
        }
        if (e.getActionCommand().equals("Create")) {
            String date = datePicker.getDateStringOrEmptyString();
            String time = timePicker.getTimeStringOrEmptyString();
            if (nameInput.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "The task must have a name. Please enter one.", "Error", JOptionPane.WARNING_MESSAGE);
            } else if (date.isBlank()) {
                JOptionPane.showMessageDialog(null, "Please provide a date.", "Error", JOptionPane.WARNING_MESSAGE);
            } else if (time.isBlank()) {
                JOptionPane.showMessageDialog(null, "Please provide a time.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                String name = nameInput.getText();
                String notes = notesInput.getText();
                manageTasks.addTask(date, name, notes, time);
                newWindow.setVisible(false);
            }
        }
        if (e.getActionCommand().equals("Cancel")) {
            newWindow.setVisible(false);
        }
        if (e.getActionCommand().equals("Generate")) {
            List<String> generatedTask = manageTasks.generateTask();
            nameInput.setText(generatedTask.get(0));
            notesInput.setText(generatedTask.get(1));
        }
        if (e.getActionCommand().equals("Remove Task")) {
            TaskInformation removingTask = (TaskInformation) ((JButton) e.getSource()).getClientProperty("task");
            manageTasks.removeTask(removingTask);
        }
        if (e.getActionCommand().equals("All")) {
            allButton.setEnabled(false);
            overdueButton.setEnabled(true);
            upcomingButton.setEnabled(true);
            overdueButton.setSelected(false);
            upcomingButton.setSelected(false);
        }
        if (e.getActionCommand().equals("Overdue")) {
            overdueButton.setEnabled(false);
            allButton.setEnabled(true);
            upcomingButton.setEnabled(true);
            allButton.setSelected(false);
            upcomingButton.setSelected(false);
        }
        if (e.getActionCommand().equals("Upcoming")) {
            upcomingButton.setEnabled(false);
            overdueButton.setEnabled(true);
            allButton.setEnabled(true);
            overdueButton.setSelected(false);
            allButton.setSelected(false);
        }
        displayTasks();
    }

    private void generateList(List<TaskInformation> tasks, String taskType) {
        taskList.removeAll();
        int taskNumber = 1;
        if (tasks.isEmpty()) {
            JLabel noTasks;
            if (taskType.equals("upcoming")) {
                noTasks = new JLabel("No upcoming incomplete tasks to show.");
            } else if (taskType.equals("overdue")) {
                noTasks = new JLabel("No overdue incomplete tasks to show.");
            } else {
                noTasks = new JLabel("No tasks to show.");
            }
            taskList.add(noTasks);
        } else {
            for (TaskInformation data : tasks) {
                JPanel task = new JPanel();
                task.setLayout(new BoxLayout(task, BoxLayout.X_AXIS));
                task.add(Box.createRigidArea(new Dimension(10, 0)));
                JPanel newTask = new JPanel();
                newTask.setLayout(new BoxLayout(newTask, BoxLayout.Y_AXIS));
                String labelData = String.valueOf(data);
                String newLabelData = labelData.replaceAll("\n", "<br/>");
                JLabel nameLabel = new JLabel("<html>" + newLabelData + "</html>");
                nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                nameLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 0));
                newTask.add(nameLabel);
                Border borderTasks = BorderFactory.createTitledBorder("Task #" + taskNumber);
                newTask.setBorder(borderTasks);
                JCheckBox completed = new JCheckBox("Completed");
                completed.putClientProperty("task", taskNumber);
                completed.setSelected(tasks.get(taskNumber - 1).isCompleted());
                completed.addItemListener(this);
                JPanel extraPanel = new JPanel();
                extraPanel.setLayout(new BoxLayout(extraPanel, BoxLayout.X_AXIS));
                JButton removeTask = new JButton("Remove Task");
                removeTask.putClientProperty("task", data);
                removeTask.addActionListener(this);
                if (taskType.equals("all")) {
                    newTask.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
                    extraPanel.add(completed);
                    extraPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
                    extraPanel.add(Box.createRigidArea(new Dimension(245, 0)));
                    extraPanel.add(removeTask);
                    extraPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    newTask.add(extraPanel);
                } else {
                    nameLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 10, 0));
                }
                taskList.add(Box.createVerticalStrut(10));
                task.add(newTask);
                task.add(Box.createRigidArea(new Dimension(10, 0)));
                taskList.add(task);
                taskList.add(Box.createVerticalStrut(10));
                taskNumber++;
            }
        }
        taskList.repaint();
        taskList.revalidate();
    }

    private void displayTasks() {
        if (overdueButton.isSelected()) {
            generateList(manageTasks.overdueTasks(), "overdue");
        }
        if (allButton.isSelected()) {
            generateList(manageTasks.getTasks(), "all");
        }
        if (upcomingButton.isSelected()) {
            generateList(manageTasks.upcomingTasks(), "upcoming");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        int taskNumber = (int) ((JCheckBox) e.getSource()).getClientProperty("task");
        JCheckBox completed = (JCheckBox) e.getItem();
        boolean isCompleted = completed.isSelected();
        if (isCompleted) {
            manageTasks.completeTask(taskNumber);
        } else {
            completed.setSelected(false);
            manageTasks.incompleteTask(taskNumber);
        }
        displayTasks();
    }
}
