import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

/*
 *  Class which demonstrates the extensibility 
    of the ConvertCreateDDL class. Only meant for 
    demonstration/testing, NOT MEANT for 
    distribution!
 */
public class CreateDDLExample extends ConvertCreateDDL {

   //this array is for determining how MySQL refers to datatypes
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
    * Method which simply outputs the parsed input
      to a .txt file by default.
      
      IMPORTANT NOTE: Only meant for testing, NOT final
      product distribution!
    */
   public void createDDL() {
      EdgeConvertGUI.setReadSuccess(true);
      databaseName = generateDatabaseName();
      
      sb.append("This is sample output!\r\n");
      sb.append("Number of tables: "+tables.length+"\r\n");
      sb.append("Number of fields: "+fields.length+"\r\n\r\n");
      
      sb.append("Outputting Tables:\r\n");
      for (int i=0; i<tables.length; i++){
         sb.append(tables[i].toString() +"\r\n");
      }
      sb.append("Outputting Fields:\r\n");
      for (int i=0; i<fields.length; i++){
         sb.append(fields[i].toString() +"\r\n");
      }
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
      return "Example Output";
   }
   
   public String getOutputFileType() {
      return ".txt";
   }
   
   public String getStatementsString() {
     createDDL();
     return sb.toString();
   }
   
}//ConvertCreateDDL
