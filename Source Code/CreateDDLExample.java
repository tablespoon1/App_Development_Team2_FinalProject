import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

/*
 *  Class which converts the stored form of an imported database structure 
 *  into an output in the MySQL format.
 */
public class CreateDDLExample extends ConvertCreateDDL {

   //protected String databaseName;
   //this array is for determining how MySQL refers to datatypes
   protected String databaseName;
   protected String[] strDataType = {"VARCHAR", "BOOL", "INT", "DOUBLE"};
   protected StringBuffer sb;
   
   protected int[] nativeFields;
   protected int[] relatedFields;
   protected boolean[] primaryKey;
   protected int numPrimaryKey;
   protected int numForeignKey;

   /*
    * Public constructor accepts arrays of all Tables and Fields and
    * passes them to it's superclass constructor.
    *
    * @param inputTables Array of class EdgeTable containing all tables
    * @param inputFields Array of class EdgeField containing all fields
    */
   public CreateDDLExample(EdgeTable[] inputTables, EdgeField[] inputFields) {
      super(inputTables, inputFields);
      sb = new StringBuffer();
   } //CreateDDLExample(EdgeTable[], EdgeField[])
   
   
   /*
    *  Empty Constructor with empty args list allows output directory 
    *  to be set before there are table and field objects   
    */
   public CreateDDLExample() { //default constructor with empty arg list for to allow output dir to be set before there are table and field objects
      
   }
   
   /*
    * Method which creates the databases and tables for the MySQL
    * output, processing tables, their fields and relations to
    * each other and tranforming them into the appropporate
    * MySQL statements.
    */
   public void createDDL() {
      EdgeConvertGUI.setReadSuccess(true);
      databaseName = generateDatabaseName();
      sb.append("This is just a test file! And this is test output!");
   }
   

   

   /*
    *  Prompts user for database name for the output file.
    *  
    *  @return The user determined database name
    */
   public String generateDatabaseName() { //prompts user for database name
      String dbNameDefault = "MyExampleDB";
      
      return EdgeConvertGUI.promptDatabaseName(dbNameDefault);
      /*
      do {
         databaseName = (String)JOptionPane.showInputDialog(
                       null,
                       "Enter the database name:",
                       "Database Name",
                       JOptionPane.PLAIN_MESSAGE,
                       null,
                       null,
                       dbNameDefault);
         if (databaseName == null) {
            EdgeConvertGUI.setReadSuccess(false);
            return "";
         }
         if (databaseName.equals("")) {
            JOptionPane.showMessageDialog(null, "You must select a name for your database.");
         }
      } while (databaseName.equals(""));
      return databaseName;
      */
   }
   
   /**
      Getters and setters
   */
   
   public String getDatabaseName() {
      return databaseName;
   }

   
   public String getProductName() {
      return "Example Output Format";
   }

   public String getStatementsString() {
      createDDL();
      return sb.toString();
   }
   
   public String getOutputFileType() {
      return ".txt";
   }
   
}//ConvertCreateDDL
