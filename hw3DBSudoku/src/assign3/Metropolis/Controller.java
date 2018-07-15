package assign3.Metropolis;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//  The controller is the logic that glues the model and the view together for data change.
//  Manage the relationship between the model and view --
//  typically this involves sending updates around as the data changes.
//    1. Most data changes are initiated by user events (keyboard, mouse gestures)
//    that tend to initiate on the view side.These are translated to setter/mutator
//    messages to the model which does the actual data maintenance
//    2. When a change happens in the model data, the view needs to hear about it so it can
//    draw differently if appropriate.In Swing, this is done with the Listener paradigm.
public class Controller {
    public static void main(String[] args) {
        DatabaseManager DBManager = new DatabaseManager();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        Model myTableModel = new Model();
        View view = new View("METROPOLIS", myTableModel);

    }

}
