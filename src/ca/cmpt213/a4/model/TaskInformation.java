package ca.cmpt213.a4.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A class that creates a TaskInformation object which holds a task including specific information such as name, notes, due date and if it is completed.
 * The toString method has been overridden to return a string of information of the task.
 *
 * @author Daven Chohan
 */
public class TaskInformation {
    private boolean isCompleted;
    private final String name;
    private final String notes;
    private final GregorianCalendar dueDate;

    public TaskInformation(boolean isCompleted, String name, String notes, GregorianCalendar dueDate) {
        this.isCompleted = isCompleted;
        this.name = name;
        this.notes = notes;
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public GregorianCalendar getDueDate() {
        return dueDate;
    }

    public String fixHour() {
        int hour = dueDate.get(Calendar.HOUR_OF_DAY);
        return String.format("%02d", hour);
    }

    public String fixMinute() {
        int minute = dueDate.get(Calendar.MINUTE);
        return String.format("%02d", minute);
    }

    public String fixMonth() {
        int month = dueDate.get(Calendar.MONTH);
        return String.format("%02d", month + 1);
    }

    public String fixDay() {
        int day = dueDate.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d", day);
    }


    @Override
    public String toString() {
        return "Name: " + name +
                "\nNotes: " + notes +
                "\nDue date: " + dueDate.get(Calendar.YEAR) + "-" + fixMonth() + "-" + fixDay() + " " + fixHour() + ":"
                + fixMinute();
    }
}
