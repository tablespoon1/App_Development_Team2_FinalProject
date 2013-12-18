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
public class CreateDDLMySQL extends ConvertCreateDDL {

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
   public CreateDDLMySQL(EdgeTable[] inputTables, EdgeField[] inputFields) {
      super(inputTables, inputFields);
      sb = new StringBuffer();
   } //CreateDDLMySQL(EdgeTable[], EdgeField[])
   
   
   /*
    *  Empty Constructor with empty args list allows output directory 
    *  to be set before there are table and field objects   
    */
   public CreateDDLMySQL() { //default constructor with empty arg list for to allow output dir to be set before there are table and field objects
      
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
      sb.append("CREATE DATABASE " + databaseName + ";\r\n");
      sb.append("USE " + databaseName + ";\r\n");
      
      processTables();
   }
   
   /*
      Method which processes tables one by one for
      converstion into SQL statements. 
   */
   protected void processTables() {
       for (int boundCount = 0; boundCount <= maxBound; boundCount++) { //process tables in order from least dependent (least number of bound tables) to most dependent
         for (int tableCount = 0; tableCount < numBoundTables.length; tableCount++) { //step through list of tables
            if (numBoundTables[tableCount] == boundCount) { //
               sb.append("CREATE TABLE " + tables[tableCount].getName() + " (\r\n");
               
               nativeFields = tables[tableCount].getNativeFieldsArray();
               relatedFields = tables[tableCount].getRelatedFieldsArray();
               primaryKey = new boolean[nativeFields.length];
               numPrimaryKey = 0;
               numForeignKey = 0;
               
               processFields();
               /*
               for (int nativeFieldCount = 0; nativeFieldCount < nativeFields.length; nativeFieldCount++) { //print out the fields
                  EdgeField currentField = getField(nativeFields[nativeFieldCount]);
                  sb.append("\t" + currentField.getName() + " " + strDataType[currentField.getDataType()]);
                  if (currentField.getDataType() == 0) { //varchar
                     sb.append("(" + currentField.getVarcharValue() + ")"); //append varchar length in () if data type is varchar
                  }
                  if (currentField.getDisallowNull()) {
                     sb.append(" NOT NULL");
                  }
                  if (!currentField.getDefaultValue().equals("")) {
                     if (currentField.getDataType() == 1) { //boolean data type
                        sb.append(" DEFAULT " + convertStrBooleanToInt(currentField.getDefaultValue()));
                     } else { //any other data type
                        sb.append(" DEFAULT " + currentField.getDefaultValue());
                     }
                  }
                  if (currentField.getIsPrimaryKey()) {
                     primaryKey[nativeFieldCount] = true;
                     numPrimaryKey++;
                  } else {
                     primaryKey[nativeFieldCount] = false;
                  }
                  if (currentField.getFieldBound() != 0) {
                     numForeignKey++;
                  }
                  sb.append(",\r\n"); //end of field
                  
               }
               */
               if (numPrimaryKey > 0) { //table has primary key(s)
                  processPrimaryKeys(tableCount);
                  /*
                  sb.append("CONSTRAINT " + tables[tableCount].getName() + "_PK PRIMARY KEY (");
                  for (int i = 0; i < primaryKey.length; i++) {
                     if (primaryKey[i]) {
                        sb.append(getField(nativeFields[i]).getName());
                        numPrimaryKey--;
                        if (numPrimaryKey > 0) {
                           sb.append(", ");
                        }
                     }
                  }
                  sb.append(")");
                  if (numForeignKey > 0) {
                     sb.append(",");
                  }
                  sb.append("\r\n");
                  */
               }
               if (numForeignKey > 0) { //table has foreign keys
                  processForeignKeys(tableCount);
                  /*
                  int currentFK = 1;
                  for (int i = 0; i < relatedFields.length; i++) {
                     if (relatedFields[i] != 0) {
                        sb.append("CONSTRAINT " + tables[tableCount].getName() + "_FK" + currentFK +
                                  " FOREIGN KEY(" + getField(nativeFields[i]).getName() + ") REFERENCES " +
                                  getTable(getField(nativeFields[i]).getTableBound()).getName() + "(" + getField(relatedFields[i]).getName() + ")");
                        if (currentFK < numForeignKey) {
                           sb.append(",\r\n");
                        }
                        currentFK++;
                     }
                  }
                  sb.append("\r\n");
                  */
               }
               sb.append(");\r\n\r\n"); //end of table
            }
         }
      }

   } // processTables()
   
   /*
      Proccesses all Fields into appropriate SQL statements
   */
   protected void processFields() {
      for (int nativeFieldCount = 0; nativeFieldCount < nativeFields.length; nativeFieldCount++) { //print out the fields
         EdgeField currentField = getField(nativeFields[nativeFieldCount]);
         sb.append("\t" + currentField.getName() + " " + strDataType[currentField.getDataType()]);
         if (currentField.getDataType() == 0) { //varchar
            sb.append("(" + currentField.getVarcharValue() + ")"); //append varchar length in () if data type is varchar
         }
         if (currentField.getDisallowNull()) {
            sb.append(" NOT NULL");
         }
         if (!currentField.getDefaultValue().equals("")) {
            if (currentField.getDataType() == 1) { //boolean data type
               sb.append(" DEFAULT " + convertStrBooleanToInt(currentField.getDefaultValue()));
            } else { //any other data type
               sb.append(" DEFAULT " + currentField.getDefaultValue());
            }
         }
         if (currentField.getIsPrimaryKey()) {
            primaryKey[nativeFieldCount] = true;
            numPrimaryKey++;
         } else {
            primaryKey[nativeFieldCount] = false;
         }
         if (currentField.getFieldBound() != 0) {
            numForeignKey++;
         }
         sb.append(",\r\n"); //end of field
      }
   } // processFields()
   
   /*
      Proccesses Primary Keys into approproate SQL statements
      
      @param thisTable the current table index
   */
   protected void processPrimaryKeys(int thisTable) {
      sb.append("CONSTRAINT " + tables[thisTable].getName() + "_PK PRIMARY KEY (");
      for (int i = 0; i < primaryKey.length; i++) {
         if (primaryKey[i]) {
            sb.append(getField(nativeFields[i]).getName());
            numPrimaryKey--;
            if (numPrimaryKey > 0) {
               sb.append(", ");
            }
         }
      }
      sb.append(")");
      if (numForeignKey > 0) {
         sb.append(",");
      }
      sb.append("\r\n");
   } //processPrimaryKeys()
   

   /*
      Processes Foreign Keys into appropriate SQL statements
      
      @param thisTable the current table index
   */
   protected void processForeignKeys(int thisTable) {
      int currentFK = 1;
      for (int i = 0; i < relatedFields.length; i++) {
         if (relatedFields[i] != 0) {
            sb.append("CONSTRAINT " + tables[thisTable].getName() + "_FK" + currentFK +
                     " FOREIGN KEY(" + getField(nativeFields[i]).getName() + ") REFERENCES " +
                     getTable(getField(nativeFields[i]).getTableBound()).getName() + "(" + getField(relatedFields[i]).getName() + ")");
            if (currentFK < numForeignKey) {
               sb.append(",\r\n");
            }
            currentFK++;
         }
      } // end for loop
      sb.append("\r\n");
   } //processForeignKeys()
   
   /*
    *  Method which converts boolean 'true' and 'false' into '1' and '0', respectively.
    *  MySQL uses '1' and '0' for boolean types.
    *  
    *  @param input Boolean in String form (True or False)
    *  @return 1 for true, 0 for false
    */
   protected int convertStrBooleanToInt(String input) { 
      if (input.equals("true")) {
         return 1;
      } else {
         return 0;
      }
   }
   
   /*
    *  Prompts user for database name for the output file.
    *  
    *  @return The user determined database name
    */
   public String generateDatabaseName() { //prompts user for database name
      String dbNameDefault = "MySQLDB";
      
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
      return "MySQL";
   }
   
    public String getOutputFileType() {
      return ".sql";
   }
   
   public String getStatementsString() {
     createDDL();
     return sb.toString();
   }
  
}//ConvertCreateDDL
