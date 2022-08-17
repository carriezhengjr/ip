import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * CaCa is a personal assistant chatbot that helps users manage and track your things.
 * Functions with respective commands are listed below as
 * Function (description): command. e.g...
 * - Greet user (triggered as soon as the chatbot is run)
 * - Exit program (end chatbot): bye
 * - Add tasks:
 *     - ToDos (tasks without any date/time): todo taskDescription.
 *     e.g.todo borrow book
 *     - Deadlines (tasks to be done before date/time): deadline taskDescription /by dateTime.
 *     e.g. deadline return book /by Sunday
 *     - Events (tasks that start and end at a specific time): event taskDescription /at dateTime
 *     e.g. event project meeting /at Mon 2-4pm
 * - List task (displays a list of all tasks stored): list
 * - Mark task (marks task as done with a "X"): mark taskNumber. e.g. mark 2
 * - Unmark task (marks task as not done and removes "X"): unmark taskNumber. e.g. unmark 2
 */
public class CaCa {

    /**
     * A class-level array to store all user inputs.
     */
    private static List<Task> tasks = new ArrayList<>();

    /**
     * The main chatbot program greets user, reads and stores user input,
     * allows user to update task status as done or undone, displays all tasks
     * with status when user inputs list and exits when user inputs bye.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        String line = "____________________________________________________________\n";

        // ASCII text banner below created and adapted from
        // https://manytools.org/hacker-tools/ascii-banner/
        // with the following settings:
        // Banner text: CaCa, Font: Big, Horizontal spacing: Normal, Vertical spacing: Normal.
        String logo = "   _____       _____      \n"
                + "  / ____|     / ____|     \n"
                + " | |     __ _| |     __ _ \n"
                + " | |    / _` | |    / _` |\n"
                + " | |___| (_| | |___| (_| |\n"
                + "  \\_____\\__,_|\\_____\\__,_|\n\n";
        String greeting = "Hello! I'm CaCa.\n"
                + "What can I do for you?\n";
        System.out.println(line + logo + greeting + line);

        // Solution below on getting user input is
        // adapted from https://www.w3schools.com/java/java_user_input.asp
        Scanner sc = new Scanner(System.in); // Creates a Scanner Object.

        while (true) {
            // Reads user input.
            String input = sc.nextLine();

            // Detect user command, where 1st element is the type of task to be done,
            // 2nd element is the task description (with or without date/time).
            String[] command = input.split(" ", 2);

            System.out.print(line);

            if (command[0].equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!\n" + line);
                break;

            } else if (command[0].equals("list")) {
                System.out.println("Here are the tasks in your list:");

                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    System.out.printf("%d.%s%n", i + 1, task);

                    if (i == tasks.size() - 1) {
                        System.out.print(line);
                    }
                }

            } else if (command[0].equals("mark") || command[0].equals("unmark")) {
                // taskIndex entered by user is 1 larger than its array index.
                int taskIndex = Integer.parseInt(command[1]);
                Task taskToMark = tasks.get(taskIndex - 1);

                if (command[0].equals("mark")) {
                    taskToMark.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                } else {
                    taskToMark.markAsUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                }

                System.out.println(taskToMark + "\n" + line);

            } else if (command[0].equals("todo") || command[0].equals("deadline") || command[0].equals("event")) {
                Task taskToAdd = null;

                if (command[0].equals("todo")) {
                    String description = command[1];
                    taskToAdd = new Todo(description);
                } else if (command[0].equals("deadline")) {
                    String[] detailedCommand = command[1].split(" /by ", 2);
                    String description = detailedCommand[0];
                    String by = detailedCommand[1];
                    taskToAdd = new Deadline(description, by);
                } else {
                    String[] detailedCommand = command[1].split(" /at ", 2);
                    String description = detailedCommand[0];
                    String at = detailedCommand[1];
                    taskToAdd = new Event(description, at);
                }

                tasks.add(taskToAdd);
                System.out.println("Got it. I've added this task:");
                System.out.println(taskToAdd);
                System.out.printf("Now you have %d tasks in the list.\n%s", tasks.size(), line);

            } else {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(\n" + line);
            }
        }
    }
}
