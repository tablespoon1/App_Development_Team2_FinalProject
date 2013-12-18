package DatabaseComponents;

//import MainFiles.*;

import java.util.*;

/*
   Class which stores a Table of an input file in deliminated, standard format
*/
public class EdgeTable {
   private int numFigure;
   private String name;
   private ArrayList alRelatedTables, alNativeFields;
   private int[] relatedTables, relatedFields, nativeFields;
   
   /*
      Constructor breaks the paramater into Tokens and
      initializes attributes
      
      @param inputString Delimnated table information
   */
   public EdgeTable(String inputString) {
      //StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
     // numFigure = Integer.parseInt(st.nextToken());
      //name = st.nextToken();
      //alRelatedTables = new ArrayList();
      //alNativeFields = new ArrayList();
   }
   
   /*
      Getters and Setters
   */
   
   public int getNumFigure() {
      return numFigure;
   }
   
   public String getName() {
      return name;
   }
   
   public void addRelatedTable(int relatedTable) {
      alRelatedTables.add(new Integer(relatedTable));
   }
   
   public int[] getRelatedTablesArray() {
      return relatedTables;
   }
   
   public int[] getRelatedFieldsArray() {
      return relatedFields;
   }
   
   public void setRelatedField(int index, int relatedValue) {
      relatedFields[index] = relatedValue;
   }
   
   public int[] getNativeFieldsArray() {
      return nativeFields;
   }

   public void addNativeField(int value) {
      alNativeFields.add(new Integer(value));
   }

   /*
      Method which moves a nativeField up one index (e.g. 2 to 1)
      and moves the other field down one index (e.g. 1 to 2)
      
      @param index The index of the field in array nativeFields to move down
   */
   public void moveFieldUp(int index) { //move the field closer to the beginning of the list
      if (index == 0) {
         return;
      }
      int tempNative = nativeFields[index - 1]; //save element at destination index
      nativeFields[index - 1] = nativeFields[index]; //copy target element to destination
      nativeFields[index] = tempNative; //copy saved element to target's original location
      int tempRelated = relatedFields[index - 1]; //save element at destination index
      relatedFields[index - 1] = relatedFields[index]; //copy target element to destination
      relatedFields[index] = tempRelated; //copy saved element to target's original location
   }
   
   /*
      Method which moves a nativeField down one index (e.g. 1 to 2)
      and moves the other field up one index (e.g. 2 to 1)
      
      @param index The index of the field in array nativeFields to move up
   */
   public void moveFieldDown(int index) { //move the field closer to the end of the list
      if (index == (nativeFields.length - 1)) {
         return;
      }
      int tempNative = nativeFields[index + 1]; //save element at destination index
      nativeFields[index + 1] = nativeFields[index]; //copy target element to destination
      nativeFields[index] = tempNative; //copy saved element to target's original location
      int tempRelated = relatedFields[index + 1]; //save element at destination index
      relatedFields[index + 1] = relatedFields[index]; //copy target element to destination
      relatedFields[index] = tempRelated; //copy saved element to target's original location
   }

   /*
      Method which converts temporary ArrayLists into arrays nativeFields,
      temp and relatedFields
   */
   public void makeArrays() { //convert the ArrayLists into int[]
      Integer[] temp;
      temp = (Integer[])alNativeFields.toArray(new Integer[alNativeFields.size()]);
      nativeFields = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         nativeFields[i] = temp[i].intValue();
      }
      
      temp = (Integer[])alRelatedTables.toArray(new Integer[alRelatedTables.size()]);
      relatedTables = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         relatedTables[i] = temp[i].intValue();
      }
      
      relatedFields = new int[nativeFields.length];
      for (int i = 0; i < relatedFields.length; i++) {
         relatedFields[i] = 0;
      }
   }
   
   /*
      toString returns table name, number, contents of
      arrays nativeFields, relatedTables and relatedFields
      
      @return table name, number, contents of
              arrays nativeFields, relatedTables and relatedFields in
              deliminated format

   */
   /*
   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("Table: " + numFigure + "\r\n");
      sb.append("{\r\n");
      sb.append("TableName: " + name + "\r\n");
      sb.append("NativeFields: ");
      for (int i = 0; i < nativeFields.length; i++) {
         sb.append(nativeFields[i]);
         if (i < (nativeFields.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\nRelatedTables: ");
      for (int i = 0; i < relatedTables.length; i++) {
         sb.append(relatedTables[i]);
         if (i < (relatedTables.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\nRelatedFields: ");
      for (int i = 0; i < relatedFields.length; i++) {
         sb.append(relatedFields[i]);
         if (i < (relatedFields.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\n}\r\n");
      
      return sb.toString();
   }
   */
}
