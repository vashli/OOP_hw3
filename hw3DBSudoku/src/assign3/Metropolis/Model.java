package assign3.Metropolis;

//    Store the canonical copy of the data
//    Respond to getters methods to provide data
//    Respond to setters to change data
//    In Java, it is typical to use a "listener" strategy to tell the views about data changes.
//        - Java uses the Model/Listener structure, and it's a good design,
//        although there are other ways to do it.
//        - Also known as the "observer/observable" pattern
//    Model manages a list of listeners
//    When receiving a setXXX() to change the data, the model makes the change
//    and then notifies the listeners of the change (fireXXXChanged)
//        - Iterate through the listeners and notify each about the change.
//        - Change notification messages can include more specific information
//        about the change (cell edited, row deleted, ...)
public class Model {

}

