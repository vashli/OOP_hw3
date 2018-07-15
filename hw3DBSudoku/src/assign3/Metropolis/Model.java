package assign3.Metropolis;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;

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
public class Model extends AbstractTableModel{
    private static final int N_COLUMN = 3;
    private static final String COLUMN_0 = "Metropolis";
    private static final String COLUMN_1 = "Continent";
    private static final String COLUMN_2 = "Population";
    private int nCol;
    private int nRow;
    private Connection con;
    private ArrayList<String>[] grid;


    /**
     * Connects to a database and sets up a table
     */
    public Model(){
        nRow = 0;
        nCol = N_COLUMN;
        grid  = new ArrayList[N_COLUMN];
        for(int i = 0; i < N_COLUMN; i++){
            grid[i] = new ArrayList<String>();
        }

    }

    /**
     * Receives the information from the database and updates grid accordingly
     * @param s String containing the sql command
     */
    private void initGrid(String s){
        try {
            Statement st = con.createStatement();
            ResultSet rset = st.executeQuery(s);
            grid = new ArrayList[N_COLUMN];
            for(int i = 0; i < N_COLUMN; i++){
                grid[i] = new ArrayList<String>();
            }
            nRow = 0;
            while (rset.next()){
                grid[0].add(rset.getString(1));
                grid[1].add(rset.getString(2));
                grid[2].add(rset.getString(3));
                nRow++;
            }

            fireTableDataChanged();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the sql command, adds the information to the database and updates grid
     * @param metropolis the name of the metropolis to add to database
     * @param continent	the name of the continent to add to database
     * @param population quantity of the population to add to database
     */
    public void add(String metropolis, String continent, String population){
        if(metropolis.equals("")||continent.equals("") || population.equals("")) return;

        String s = String.format("insert into metropolises values(\"%s\", \"%s\", %s);",
                metropolis, continent, population);
        String INIT_STRING = String.format("select * from metropolises where metropolis " +
                "= \"%s\"and continent = \"%s\" and population = %s limit 1;",
                metropolis, continent, population);
        try {
            con = DatabaseManager.getConnection();
            if(con == null) return;
            Statement st = con.createStatement();
            st.executeUpdate(s);
            initGrid(INIT_STRING);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * /**
     * Generates the sql command, selects information according to given
     * parameters and updates grid
     * @param metropolis the name of the metropolis to select
     * @param continent	the name of the continent to select
     * @param population quantity of the population to select
     * @param larger boolean witch specifies how to compare given quantity
     * to the needed one
     * @param exact boolean witch specifies if given strings need to be the
     * exact matches to the ones in the database
     */
    public void search(String metropolis, String continent,
                       String population, boolean larger, boolean exact ){

        con = DatabaseManager.getConnection();
        if(con == null) return;
        String s = "select * from metropolises" ;
        boolean needsAnd = false;
        if(!metropolis.equals("")){
            s+= " where metropolis ";
            if(exact) s+= "= \"" + metropolis + "\" ";
            else s+= "like \"%" + metropolis + "%\"";
            needsAnd = true;

        }
        if(!continent.equals("")){
            if(needsAnd) s+= " and ";
            else s+=" where ";
            s+= "continent ";
            if(exact) s+= "= \"" + continent + "\" ";
            else s+= "like \"%" + continent + "%\"";
            needsAnd = true;

        }

        if(!population.equals("")){
            if(needsAnd) s+= " and ";
            else s+=" where ";
            s+= "population ";
            if(larger) s+= "> " + population;
            else s+= "<= " + population;
        }
        s+= ";";

        initGrid(s);
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a default name for the column
     * @param index the column being queried
     * @return a string containing the default name of column
     */
    @Override
    public String getColumnName(int index){
        if(index == 0) return COLUMN_0;
        if(index == 1) return COLUMN_1;
        return COLUMN_2;

    }

    /**
     * Returns the number of columns in the model. A JTable uses this method
     * to determine how many columns it should create and display by default
     * @return the number of columns in the model
     */
    @Override
    public int getColumnCount() {

        return N_COLUMN;
    }

    /**
     * Returns the number of rows in the model. A JTable uses this method
     * to determine how many rows it should create and display by default
     * @return the number of rows in the mode
     */
    @Override
    public int getRowCount() {
        return nRow;
    }

    /**
     * Returns the value for the cell at columnIndex and rowIndex.
     * @param rowIndex the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public String getValueAt(int rowIndex, int columnIndex) {

        return grid[columnIndex].get(rowIndex);
    }
}

