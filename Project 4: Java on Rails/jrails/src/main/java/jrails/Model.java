package jrails;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.*;

public class Model {

    static List<String> dbNames = new LinkedList<>();
    private int idNo = 0;

    public void save() {

        Class<?> c = this.getClass();  // get the class
        String dbName = c.getName() + ".csv"; // multiple data bases based on the names of the classes

        // check if a db that stores the entries of this class exists
        try {
            File file = new File(dbName); // specify the file name

            if (!file.exists()) {
                file.createNewFile();  // if the file is not present, then create one.
            }

            if (! dbNames.contains(dbName)) {
                dbNames.add(dbName);
            }


        } catch (Exception e) {
            throw new UnsupportedOperationException("Error in setting up db.");
        }

        //if id is 0, attribute a unique id to this
        boolean existsInDB = handleId(c); // true means this exists in db ; false means this doesn't exist in db

        if(existsInDB) { // updating a specific line in the file
            updateLine(c, dbName);
        }
        else {    // appending to the end of db
            appendLine(c, dbName);
        }
    }

    public int id() {
        return idNo;
    }


    public static <T> T find(Class<T> c, int id) {
        String dbName = c.getName() + ".csv"; //get the db file name for the given class
        File dbExist = new File(dbName);
        if(!dbExist.exists()) {
            return null;
        }
        Object ins = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(dbName));
            String line = reader.readLine();
            while (line != null) { //check if the given id matches any in db
                String[] words = dbLineReader(line);
                String each_id = words[0];
                if (Integer.parseInt(each_id) == id) { //if the id matches, get all the information
                    //get constructor of the class and initialize an instance
                    try {
                        // Initialize a new instance
                        Constructor<?> cons = c.getConstructor();
                        ins = cons.newInstance();
                        Field[] fields = c.getFields();

                        //set the id of the instance we created to be the same given id
                        Field id_no_field = c.getSuperclass().getDeclaredField("idNo");
                        id_no_field.set(ins, id);

                        //set all the fields of the instance based on data entries
                        for (int i = 0; i < fields.length; i++) {

                            // convert the words from the db to the right type
                            Type fieldType = fields[i].getType();

                            if (fieldType.equals(boolean.class)) { // field is boolean
                                boolean entry = Boolean.parseBoolean(words[i+1]);
                                fields[i].setBoolean(ins, entry);
                            }

                            else if (fieldType.equals(int.class)) { // field is integer
                                int entry = Integer.parseInt(words[i+1]);
                                fields[i].setInt(ins, entry);
                            }
                            else { //field is string
                                String entry = words[i + 1];

                                if (entry.equals("null")) { //string could be null
                                    fields[i].set(ins, null);
                                }
                                else if (entry.equals("empty")) { //string could be empty
                                    fields[i].set(ins, "");
                                }
                                else { //regular non-empty string
                                    fields[i].set(ins, entry);
                                }
                            }
                        }//object is updated
                    } catch (Exception e) {
                        throw new UnsupportedOperationException("Issue with creating an instance!" + e);
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            throw new UnsupportedOperationException("Issue with BufferedReader.");
        }

        try {
            c.cast(ins); // see if casting throws any error
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException("Casting Error!"); // log the exception or other error handling
        }

        return c.cast(ins); // cast to T
    }


    public static <T> List<T> all(Class<T> c) {
        List<T> allIns = new LinkedList<>();
        String dbName = c.getName() + ".csv"; //get the db file name for the given class
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(dbName));
            String line = reader.readLine();
            while (line != null) {  //check if the given id matches any in db
                //get constructor of the class and initialize an instance
                try {
                    String[] words = dbLineReader(line);
                    int id = Integer.parseInt(words[0]);
                    // Initialize a new instance
                    Constructor<?> cons = c.getConstructor();
                    Object ins = cons.newInstance();
                    Field[] fields = c.getFields();

                    Field id_no_field = c.getSuperclass().getDeclaredField("idNo");  // get the id field
                    id_no_field.set(ins, id); // set the id of the instance

                    for (int i = 0; i < fields.length; i++) { //set all the fields of the instance based on data entries

                        // convert the words from the db to the right type
                        Type fieldType = fields[i].getType();

                        if (fieldType.equals(boolean.class)) { // field is boolean
                            boolean entry = Boolean.parseBoolean(words[i+1]);
                            fields[i].setBoolean(ins, entry);
                        }

                        else if (fieldType.equals(int.class)) { // field is integer
                            int entry = Integer.parseInt(words[i+1]);
                            fields[i].setInt(ins, entry);
                        }
                        else { //field is string
                            String entry = words[i+1];

                            if (entry.equals("null")) { //string could be null
                                fields[i].set(ins, null);
                            }
                            else if (entry.equals("empty")) { //string could be empty
                                fields[i].set(ins, "");
                            }
                            else { //regular non-empty string
                                fields[i].set(ins, entry);
                            }
                        }
                    }
                    //Add each instance to the list
                    allIns.add(c.cast(ins)); // cast to T
                    line = reader.readLine();
                } catch (Exception e) {
                    throw new UnsupportedOperationException("Issue with creating an instance!" + e);
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new UnsupportedOperationException("Issue with BufferedReader.");
        }

        return allIns;
    }

    public void destroy() {
        // throw if no file that stores the class data exists
        Class  <?> c = this.getClass();
        String dbName = c.getName() + ".csv";
        File dbFile = new File(dbName);
        try {
            if (!dbFile.exists()) {
                throw new UnsupportedOperationException("File does not exist!");
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException("No data base that stores this class data.");
        }

        // create a new file to store all lines except the one with id
        File newFile = new File("tempFile.csv");

        // copy all the lines except the one with the instance that we'll delete
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(dbName)); // reads db
            bw = new BufferedWriter(new FileWriter(newFile, true)); // appends to the file
            boolean inDB = false;
            String line = br.readLine();
            while (line != null) {
                //get the id value in the line and turn it into an integer type
                int lineId = Integer.parseInt(dbLineReader(line)[0]);
                //check if the id of the line matches the id of the instance
                if (lineId != this.idNo) {
                    //write to the newFile all the linex except the one with the id
                    bw.write(line);
                    bw.newLine();
                } else {
                    inDB = true;
                }
                line = br.readLine();
            }
            br.close();
            bw.flush();
            bw.close();

            //instance is not in db so delete the new file and keep the old db
            if (inDB == false) {
                newFile.delete();
                throw new UnsupportedOperationException("Not in DB!");
            }
        } catch(Exception e) {
            throw new UnsupportedOperationException("Issue in reading the db.");
        }

        // Delete the db file and rename the newFile
        try {
            if(! dbFile.delete()) {
                throw new UnsupportedOperationException("Failure to delete " + dbFile.getName());
            }
            File toName = new File(dbName);
            if(! newFile.renameTo(toName)) {
                throw new UnsupportedOperationException("Failure to change the name of " + newFile.getName());
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException("Issue in deleting the db or renaming the current file." + e);
        }
    }

    public static void reset() {
        try {
            for (String dbName : dbNames) {
                File db = new File(dbName);
                db.delete();
            }
            File uniqueIdFile = new File("uniqueId.txt");
            if (uniqueIdFile.exists()) {
                uniqueIdFile.delete();
            }
        } catch(Exception e) {
            throw new UnsupportedOperationException("Issue with delteing the files.");
        }
    }


    private static String[] dbLineReader(String line) {
        return line.split("/~/~/~/");
    }

    // Purpose: handles the id of the instance: (1) creates a unique id file if not exist,
    //                                           (2) and attributes a new unique id value if 0
    //Return : (1) true: if id of instance is 0 bc it cannot be in db
    //         (2) false: if id of instance is non-0 bc it is in db
    private boolean handleId(Class<?> c) {
        File file = new File("uniqueId.txt");
        // if the file doesn't already exist,
        // (1) creates the uniqueId file; (2) writes the unique id 2 to uniqueId file
        try {
            if (!file.exists()) {
                file.createNewFile();
                BufferedWriter br = new BufferedWriter(new FileWriter(file));
                br.write("1");
                br.flush();
                br.close();
            }
        } catch(Exception e) {
            throw new UnsupportedOperationException("Issue in creating a file.");
        }

        // if id number equals 0, then attain a new id number for that instance.
        if (this.idNo == 0) {

            // read the unique id from the file and update it in the file
            BufferedReader br = null;
            int uniqueId = -1;
            try {
                //get the unique id from the file
                br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                uniqueId = Integer.parseInt(line);

                //update the unique id
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                int newUniqueId = uniqueId+1;
                bw.write(String.valueOf(newUniqueId));
                bw.flush();
                bw.close();


            } catch (Exception e) {
                throw new UnsupportedOperationException("Issue in reading the file.");
            }

            // change the unique id of the instance
            Field id_field = null;
            try {
//                id_field = c.getSuperclass().getDeclaredField("idNo");
//                id_field.set(this, uniqueId);
                this.idNo = uniqueId;
            } catch (Exception e) {
                throw new UnsupportedOperationException("Issue in setting the id field." + e);
            }
            // return false bc id is not in db
            return false;
        }
        // return true bc id is in db
        return true;
    }


    private void updateLine(Class <?> c, String dbName) {
        Field[] fs = c.getFields(); //get fields of the class

        //store the contents of the file in a list
        List<String> linesFile = new LinkedList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dbName));
            String line = br.readLine();
            while (line != null) {
                int lineId = Integer.parseInt(dbLineReader(line)[0]); //get the id value in the line and turn it into an integer type
                if (lineId != this.idNo) { //check if the id of the line matches the id of the instance
                    linesFile.add(line); //write to the newFile all the linex except the one with the id
                } else {
                    linesFile.add(writeInstance(fs));
                }
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Issue in storing the lines of the file." + e);
        }

        //write all the lines to a nef file, delete the old file, and change the name of the file

        // create a new file to store all lines except the one with id
        File newFile = new File("tempFile.csv");

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(newFile, true)); // appends to the file
            for (String line : linesFile) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch(Exception e) {
            throw new UnsupportedOperationException("Issue in writing to the temp file." + e);
        }

        // Delete the db file and rename the newFile
        File existingDb = new File(dbName);
        try {
            if(! existingDb.delete()) {
                throw new UnsupportedOperationException("Failure to delete " + existingDb.getName());
            }
            File toName = new File(dbName);
            if(! newFile.renameTo(toName)) {
                throw new UnsupportedOperationException("Failure to change the name of " + newFile.getName());
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException("Issue in deleting the db or renaming the current file.");
        }
    }

    private void appendLine(Class <?> c, String dbName) {
        Field[] fs = c.getFields(); //get fields of the class

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(dbName, true));
            String line = writeInstance(fs); // get the info of this in a line
            bw.write(line); // append the line to the end of the file
            bw.newLine();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Issue in UpdatedLine: Writing to DB.");
        }

        try {
            bw.flush();
            bw.close();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Issue in UpdateLine: Closing BW.");
        }

    }

    // Returns the all the info of the fields of this in a line of string
    private String writeInstance(Field[] fs) {
        StringBuilder allField = new StringBuilder();

        allField.append(this.idNo); // append the id of the object

        for (Field field : fs) { //append all the fields' values

            if (field.isAnnotationPresent(Column.class)) {

                String f_str = "NoValue"; // get the value of the field of the object
                try {
                    f_str = String.valueOf(field.get(this));
                    if (f_str.equals("")) {
                        f_str = "empty";
                    }
                } catch (NullPointerException npe) {
                    f_str = "null";
                } catch (Exception e) {
                    throw new UnsupportedOperationException("Issue in WriteInstance: Failed to Get Field Value.");
                }

                allField.append("/~/~/~/").append(f_str); // append the seperator and the word
            }

        }
        return allField.toString();
    }






}