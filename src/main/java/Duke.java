import duke.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


import java.io.File;

public class Duke {

    private Storage storage;
    private TaskList tasks;
    private File file;
    private Parser p;
    private Ui ui;

    public Duke() {

    }

    public Duke(String filePath) {
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(filePath);
        } catch (DukeException e) {
            ui.showLoadingError();
            this.tasks = new TaskList(filePath);
        }
        ui = new Ui(this.tasks, this.storage);
        p = new Parser();
        this.file = new File(filePath);
        this.tasks.readFromFile();
    }

    /**
     * Method that runs the Duke by asking for user input
     *
     * @return void
     */
    public void run() {
        System.out.println("Hello! This is Duke, your very own chat bot.");
        System.out.println("What can I help you with ?");
        while (true) {
            String fullCommand = ui.input();
            if (!fullCommand.equals("bye")) {
                try {
                    p.parse(fullCommand, this.tasks, this.storage);
                } catch (DukeException e) {
                    System.out.println(e.getMsg());
                }
            } else {
                System.out.println("It's sad to see you go :(");
                System.out.println("Goodbye, hope to you another day!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Duke("./data/duke.txt").run();
    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    public String getResponse(String input) {
        while (!input.equals("bye")) {
            try {
                this.tasks = new TaskList("./data/duke.txt");
            } catch (DukeException e) {
                ui.showLoadingError();
                this.tasks = new TaskList("./data/duke.txt");
            }
            this.tasks.readFromFile();
            return p.parse(input, this.tasks, this.storage);
        }
        return "It's sad to see you go :(";
    }
}
