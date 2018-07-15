package assign3.Metropolis;



import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//    Have a pointer to model
//    Don't store any data
//    Send getXXX() to model to get data as needed
//    User edit operations (clicking, typing) in the UI map to setXXX()
//    messages sent to model
//    Register as a listener to the model and respond to change notifications
//    On change notification, consider doing a model.getXXX() to get the new values
//    to make the pixels up-to-date with the real data. Or perhaps nothing needs to be
//    done, if for example that data is currently scrolled off screen or not shown.
public class View extends JFrame{

    private JButton addButton;
    private JButton searchButton;
    private JComboBox<String> compareBox;
    private JComboBox<String> matchBox;
    private JTextField metropolisField;
    private JTextField continentField;
    private JTextField populationField;
    private Model myTableModel;

    private static final String COMPARE_0 = "Population larger than";
    private static final String COMPARE_1 = "Less than or equal to";
    private static final String MATCH_0 = "Exact match";
    private static final String MATCH_1 = "Partial match";



    public View(String name, Model tableModel){
        super(name);
        myTableModel = tableModel;
        addNorthPanel();
        addCenterPanel();
        addEastPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * Initializes the add button and adds action listener to it
     */
    private void initAddButton(){

        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 20));


        addButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myTableModel.add(metropolisField.getText(), continentField.getText(),
                        populationField.getText());
            }
        });
    }

    /**
     * Initializes the search button and adds action listener to it
     */
    private void initSearchButton(){
        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 20));
        searchButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean larger = compareBox.getSelectedItem().equals(COMPARE_0);
                boolean exact = matchBox.getSelectedItem().equals(MATCH_0);

                myTableModel.search(metropolisField.getText(), continentField.getText(),
                        populationField.getText(), larger, exact);
            }
        });
    }

    /**
     * Initializes the compare combo box and adds two items to it
     */
    private void initCompareBox(){
        compareBox = new JComboBox<String>();
        compareBox.setMaximumSize(new Dimension(150, 20));
        compareBox.addItem(COMPARE_0);
        compareBox.addItem(COMPARE_1);

    }

    /**
     * Initializes the match combo box and adds two items to it
     */
    private void initMatchBox(){
        matchBox = new JComboBox<String>();
        matchBox.setMaximumSize(new Dimension(150, 20));
        matchBox.addItem(MATCH_0);
        matchBox.addItem(MATCH_1);
    }

    /**
     * Returns a panel containing two buttons for the top of the east section of the frame.
     */
    private JPanel eastTopPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMaximumSize(new Dimension(150, 50));

        initAddButton();
        initSearchButton();

        panel.add(addButton);
        panel.add(searchButton);

        return panel;
    }

    /**
     * Returns a panel containing two combo boxes for the bottom of the east section of the frame.
     */
    private JPanel eastBottomPanel(){
        JPanel optionsPanel =  new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setMaximumSize(new Dimension(150, 60));

        initCompareBox();
        initMatchBox();

        optionsPanel.add(compareBox);

        optionsPanel.add(matchBox);
        return optionsPanel;
    }

    /**
     * Adds a panel on the east side of the frame
     */
    private void addEastPanel() {
        JPanel EastPanel = new JPanel();
        EastPanel.setLayout(new BoxLayout(EastPanel, BoxLayout.Y_AXIS));

        EastPanel.add(eastTopPanel());

        JPanel botPanel = eastBottomPanel();
        botPanel.setBorder(new TitledBorder("Search Options"));
        EastPanel.add(botPanel);

        add(EastPanel,BorderLayout.EAST);
    }

    /**
     * Adds a panel containing JTable on the center of the frame
     */
    private void addCenterPanel() {
        JTable jTable = new JTable(myTableModel);
        JScrollPane pane = new JScrollPane(jTable);
        add(pane, BorderLayout.CENTER);
    }

    /**
     * Adds a panel containing three text fields on the north part of the frame
     */
    private void addNorthPanel() {
        JPanel northPanel = new JPanel();

        metropolisField = new JTextField(20);
        continentField = new JTextField(20);
        populationField = new JTextField(20);

        JLabel metropolisLabel = new JLabel("Metropolis:");
        JLabel continentLabel = new JLabel("Continent:");
        JLabel populationLabel = new JLabel("Population:");

        northPanel.add(metropolisLabel);
        northPanel.add(metropolisField);
        northPanel.add(continentLabel);
        northPanel.add(continentField);
        northPanel.add(populationLabel);
        northPanel.add(populationField);

        this.add(northPanel, BorderLayout.NORTH);

    }

}
