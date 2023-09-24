/* 
 * Created by Bryan Wieschenberg
 * NameSurfer.java stores data on every recorded name from 1880-2021 and displays a menu for the user to get info about names
 */

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class NameSurfer {
    public static void main(String[] args) throws IOException {
        // Creating essential objects, arraylists, and variables
        FileInputStream fileByteStream = new FileInputStream("names/yob2021.txt");
        Scanner scnr = new Scanner(System.in);
        Scanner inFS = null;
        ArrayList<NameRecord> namesM = new ArrayList<NameRecord>();
        ArrayList<NameRecord> namesF = new ArrayList<NameRecord>();
        NameRecord startEnd = new NameRecord("startEnd");
        String line = "", newLineString = "";
        int rank = 0, tracker = -1;
        boolean newName = true, firstM = false;

        // Main loop that stores names into the arraylists and ranks each name in each year accordingly
        for (int year = startEnd.START; year <= startEnd.END; year++) {
            fileByteStream = new FileInputStream("names/yob" + year + ".txt");
            inFS = new Scanner(fileByteStream);
            rank = 0;
            firstM = false;
            // Scans each line of a file and stores the name and rank in NameRecord for each object in the arraylist
            while (inFS.hasNext()) {
                newName = true;
                rank++;
                tracker = -1;
                line = inFS.next();
                newLineString = line.substring(0, line.indexOf(","));
                NameRecord newLine = new NameRecord(newLineString);
                // If the line contains ",F,", the namesF arraylist will be used because it is a female name
                if (line.contains(",F,")) {
                    for (int i = 0; i < namesF.size(); i++) {
                        tracker++;
                        if (newLine.getName().equals(namesF.get(i).getName())) {
                            newName = false;
                            break;
                        }
                    }
                    
                    if (newName) {
                        namesF.add(newLine);
                        namesF.get(namesF.size()-1).setRank(year-1880, rank);
                    }
                    else {
                        namesF.get(tracker).setRank(year-1880, rank);
                    }
                }
                // If the line doesn't contain ",F," (meaning it has to contain ",M,"), the namesM arraylist will be used because it is a male name
                else {
                    if (!firstM) {
                        firstM = true;
                        rank = 1;
                    }
                    for (int i = 0; i < namesM.size(); i++) {
                        tracker++;
                        if (newLine.getName().equals(namesM.get(i).getName())) {
                            newName = false;
                            break;
                        }
                    }
                    
                    if (newName) {
                        namesM.add(newLine);
                        namesM.get(namesM.size()-1).setRank(year-1880, rank);
                    }
                    else {
                        namesM.get(tracker).setRank(year-1880, rank);
                    }
                }
            }
            // Lets the user know when the program gathers all data from each year file
            System.out.println("Completed year " + year + ".");
            inFS.close();
        }
        // Lets the user know when the program has gathered info on all years
        // It takes some time to gather all the information, as there are hundreds of thousands of names
        System.out.println("All data has been successfully gathered.\n");

        boolean running = true;
        while (running) {
            // Options the user has now that the program has gathered all information about each name and its rank
            System.out.println("1 - Find best year for a female name");
            System.out.println("2 - Find best rank for a female name");
            System.out.println("3 - Find a female name's rank in a specified year");
            System.out.println("4 - Find best year for a male name");
            System.out.println("5 - Find best rank for a male name");
            System.out.println("6 - Find a male name's rank in a specified year");
            System.out.println("7 - Quit");
            System.out.println("Enter your selection.");

            int option = scnr.nextInt(); // User can input its option
            String name = "";
            int year = 0;
            boolean validName = false;
            switch (option) {
                case 1: // 1 – Find best year for a female name
                    System.out.print("Enter a name: ");
                    name = scnr.next();
                    for (int i = 0; i < namesF.size(); i++) {
                        if (namesF.get(i).getName().equals(name)) {
                            System.out.println(name + "\'s best year: " + namesF.get(i).bestYear() + "\n");
                            validName = true;
                            break;
                        }
                    }
                    if (!validName) System.out.println("ERROR: Invalid name.\n");
                    break;
                case 2: // 2 – Find best rank for a female name
                    System.out.print("Enter a name: ");
                    name = scnr.next();
                    for (int i = 0; i < namesF.size(); i++) {
                        if (namesF.get(i).getName().equals(name)) {
                            int yearCase = namesF.get(i).bestYear();
                            System.out.println(name + "\'s best rank: " + namesF.get(i).getRank(yearCase-1880) + "\n");
                            validName = true;
                            break;
                        }
                    }
                    if (!validName) System.out.println("ERROR: Invalid name.\n");
                    break;
                case 3: // 3 – Find a female name's rank in a specified year
                    System.out.print("Enter a name: ");
                    name = scnr.next();
                    System.out.print("Enter a year: ");
                    year = scnr.nextInt();
                    for (int i = 0; i < namesF.size(); i++) {
                        if (namesF.get(i).getName().equals(name)) {
                            System.out.println(name + "\'s rank in " + year + ": " + namesF.get(i).getRank(year-1880) + "\n");
                            validName = true;
                            break;
                        }
                    }
                    if (!validName) System.out.println("ERROR: Invalid name.\n");
                    break;
                case 4: // 4 – Find best year for a male name
                    System.out.print("Enter a name: ");
                    name = scnr.next();
                    for (int i = 0; i < namesM.size(); i++) {
                        if (namesM.get(i).getName().equals(name)) {
                            System.out.println(name + "\'s best year: " + namesM.get(i).bestYear() + "\n");
                            validName = true;
                            break;
                        }
                    }
                    if (!validName) System.out.println("ERROR: Invalid name.\n");
                    break;
                case 5: // 5 – Find best rank for a male name
                    System.out.print("Enter a name: ");
                    name = scnr.next();
                    for (int i = 0; i < namesM.size(); i++) {
                        if (namesM.get(i).getName().equals(name)) {
                            int yearCase = namesM.get(i).bestYear();
                            System.out.println(name + "\'s best rank: " + namesM.get(i).getRank(yearCase-1880) + "\n");
                            validName = true;
                            break;
                        }
                    }
                    if (!validName) System.out.println("ERROR: Invalid name.\n");
                    break;
                case 6: // 6 – Find a male name's rank in a specified year
                    System.out.print("Enter a name: ");
                    name = scnr.next();
                    System.out.print("Enter a year: ");
                    year = scnr.nextInt();
                    for (int i = 0; i < namesM.size(); i++) {
                        if (namesM.get(i).getName().equals(name)) {
                            System.out.println(name + "\'s rank in " + year + ": " + namesM.get(i).getRank(year-1880) + "\n");
                            validName = true;
                            break;
                        }
                    }
                    if (!validName) System.out.println("ERROR: Invalid name.\n");
                    break;
                case 7: // 7 – Quit
                    System.out.print("Program terminated.");
                    running = false;
                    break;
                default: // Default case
                    System.out.println("Please enter a valid number. (1-7)\n");
                    break;
            }
        }
        // Close all objects
        scnr.close();
    }
}
