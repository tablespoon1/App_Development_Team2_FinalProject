import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

/*
   Abstract class which every DDL definition class extends
*/
public abstract class ConvertCreateDDL {
   protected String databaseName;
   protected EdgeTable[] tables; //master copy of EdgeTable objects
   protected EdgeField[] fields; //master copy of EdgeField objects
   protected int[] numBoundTables;
   protected int maxBound;
   protected StringBuffer sb;
   protected int selected;
   
   /*
      Parameterized constructor instantiates tables and fileds arrays
      and calls the initialize function
   */
   public ConvertCreateDDL(EdgeTable[] tables, EdgeField[] fields) {
      this.tables = tables;
      this.fields = fields;
      initialize();
   } //ConvertCreateDDL(EdgeTable[], EdgeField[])
   
   /*
      Default constructor with empty arg list for to allow output dir 
      to be set before there are table and field objects
   */
   public ConvertCreateDDL() { //
      
   } //ConvertCreateDDL()
   
   /*
      Initializes the StringBuffer and steps through
      the array of tables, identofying all bound tables
   */
   public void initialize() {
      numBoundTables = new int[tables.length];
      maxBound = 0;
      sb = new StringBuffer();

      for (int i = 0; i < tables.length; i++) { //step through list of tables
         int numBound = 0; //initialize counter for number of bound tables
         int[] relatedFields = tables[i].getRelatedFieldsArray();
         for (int j = 0; j < relatedFields.length; j++) { //step through related fields list
            if (relatedFields[j] != 0) {
               numBound++; //count the number of non-zero related fields
            }
         }
         numBoundTables[i] = numBound;
         if (numBound > maxBound) {
            maxBound = numBound;
         }
      }
   }
   
   /*
      Protected Method return the index of table array
      which correpsonds to the given table num
      
      @param numFigure Number of the specified table
      @return Index of given table in the tables array
   */
   protected EdgeTable getTable(int numFigure) {
      for (int tIndex = 0; tIndex < tables.length; tIndex++) {
         if (numFigure == tables[tIndex].getNumFigure()) {
            return tables[tIndex];
         }
      }
      return null;
   }
   
      /*
      Protected Method return the index of field array
      which correpsonds to the given table num
      
      @param numFigure Number of the table in which the specified field is found
      @return Index of the specified field in the fields array
   */
   protected EdgeField getField(int numFigure) {
      for (int fIndex = 0; fIndex < fields.length; fIndex++) {
         if (numFigure == fields[fIndex].getNumFigure()) {
            return fields[fIndex];
         }
      }
      return null;
   }
   

   /*
      Abstracted createDDL, toString and getters
   */
   public abstract String getDatabaseName();
   
   public abstract String generateDatabaseName();
   
   public abstract String getStatementsString();
   
   public abstract void createDDL();
   
   public abstract String getProductName();
   
   public abstract String getOutputFileType();
   
}//ConvertCreateDDL
