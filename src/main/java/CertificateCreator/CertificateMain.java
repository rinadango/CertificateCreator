package CertificateCreator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class CertificateMain {

    // A list of People objects
    public static ArrayList<People> listOfPeople = new ArrayList<>();

    // Object of a Scanner used throughout the code
    public static Scanner scan = new Scanner(System.in);

    // Object of the DatabaseConnector class
    public static DatabaseConnector databaseConnector = new DatabaseConnector();

    public static void main(String[] args) throws SQLException, IOException, DocumentException {

        System.out.println("\nWelcome to Certificate Creator\n");

        // Adds all the table to the list
        ResultSet connectionAll = databaseConnector.connectionToDatabase("SELECT * FROM people");
        tableGetter(connectionAll,listOfPeople);

        // Calculates the size of the list
        int howManyPeopleTotal = listOfPeople.size();
        System.out.println("The size of the group: " + howManyPeopleTotal);

        // Empties the list for the purpose of reusing
        listOfPeople.clear();

        // The program keeps looping until it is killed based on a condition
        while (true){

            //Prints the initial instructions
            instructionsPrinter();

            String gradeSelected = scan.next();
            if (gradeSelected.equalsIgnoreCase("Attendance")){
                // Connects to the SQL database and retrieves the data from a predefined table
                ResultSet connection = databaseConnector.connectionToDatabase("SELECT * FROM people WHERE grades_average < 8");
                tableGetter(connection, listOfPeople);
            } else if (gradeSelected.equalsIgnoreCase("Completion")){
                ResultSet connection = databaseConnector.connectionToDatabase("SELECT * FROM people WHERE grades_average >= 8");
                tableGetter(connection, listOfPeople);
            } else if (gradeSelected.equalsIgnoreCase("EXIT")){
                break;
            } else {
                System.out.println("Wrong input! EXITING");
                break;
            }

            peoplePrinter();

            System.out.println("How do you want the '" + gradeSelected.toUpperCase(Locale.ROOT) + " CERTIFICATES' printed");
            System.out.println("If all - 1, one by one - 2, none - 0\n");

            int printingSelection = scan.nextInt();
            if(printingSelection == 1){
                for (People listOfPerson : listOfPeople) {

                    // Creates all the certificates based on Attendance or Completion
                    if(listOfPerson.getGrades_average() < 8){ // All attendance certificates are printed if the Person object fulfills the condition
                        certificateCreator(listOfPerson, "Attendance", "ATTENDED");
                    } else if(listOfPerson.getGrades_average() >= 8){ // All attendance certificates are printed if the Person object fulfills the condition
                        certificateCreator(listOfPerson, "Completion", "FULLY COMPLETED");
                    } else{
                        System.out.println("Selection does not EXIST");
                    }
                }
            } else if (printingSelection == 2){

                // Creates the certificates for one person at a time
                for (People listOfPerson : listOfPeople) {
                    System.out.println("Print a certificate for " + listOfPerson.getName() + " " + listOfPerson.getSurname() + " ? Type 'Yes' to print, 'No' to not print");
                    System.out.println();
                    String print = scan.next();

                    if(print.equalsIgnoreCase("Yes")){
                        if(listOfPerson.getGrades_average() <= 8){
                            certificateCreator(listOfPerson, "Attendance", "ATTENDED");
                        } else if(listOfPerson.getGrades_average() >= 8){
                            certificateCreator(listOfPerson, "Completion", "FULLY COMPLETED");
                        } else{
                            System.out.println("Selection does not EXIST");
                        }
                    } else{
                        System.out.println("The certificate has not been printed!");
                    }
                }
            } else {
                System.out.println("No certificates were printed!");
            }

        }

    }

    /**
     * Prints all the people from the list of People taken from the SQL table together with their average grade
     */
    private static void peoplePrinter() {
        System.out.println();
        for (People listOfPerson : listOfPeople) {
            System.out.println(listOfPerson.getName() + " " + listOfPerson.getSurname() + " with grade average of " + listOfPerson.getGrades_average());
        }
        System.out.println();
    }

    /**
     * Create certificates based on Attendance or Completion
     *
     * @param listOfPerson the list of Person objects
     * @param typeOfCertificate specify whether the certificate is Attendance or Completion
     * @param whatHappend defined what happened that the person was able to receive the certificate
     * @throws DocumentException
     * @throws IOException
     */
    private static void certificateCreator(People listOfPerson, String typeOfCertificate, String whatHappend) throws DocumentException, IOException {
        String name = listOfPerson.getSurname();
        String pdfName = String.format("src/main/java/final_project/certificate_%s.pdf",name); // Path and pdf file name

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfName)); // Creation of pdf
        document.open();

        // Add image to the pdf
        Image img = Image.getInstance("src/main/java/final_project/certified_hq_big.png");
        img.scaleAbsolute(150f, 150f);
        img.setAlignment(Element.ALIGN_CENTER);
        document.add(img);

        // Message in the pdf file
        String congratulations = "\nCertificate of " + typeOfCertificate + "\n\nCongratulations " + listOfPerson.getName() + " " + listOfPerson.getSurname() + "!\n"
                + "You have " + whatHappend +  " the Course and were eligible to receive a Certificate of " + typeOfCertificate + ".";

        // Add the message to the pdf
        Paragraph paragraph = new Paragraph(congratulations);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        // Add the company name to the pdf
        Paragraph companyParagraph = new Paragraph("\n\nCompany and CO.");
        companyParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(companyParagraph);

        document.close();

        System.out.println("Certificate created");
    }

    /**
     * Retrieves the values of a table; BASICALLY the whole table based on a condition
     *
     * @param connection pass SQL connector
     * @throws SQLException
     */
    private static void tableGetter(ResultSet connection, ArrayList<People> list) throws SQLException {
        while (connection.next()) {
            int id = connection.getInt("id");
            String name = connection.getString("name");
            String surname = connection.getString("surname");
            int gradeAverage = connection.getInt("grades_average");

            People people = new People(id, name, surname, gradeAverage);

            list.add(people);
        }
    }

    /**
     * Simple instructions printer
     */
    private static void instructionsPrinter() {

        System.out.println("\nFor Certificate of Attendance enter 'Attendance'");
        System.out.println("For Certificate of Completion enter 'Completion'\n");
        System.out.println("|| To end the the program and print the certificates enter 'EXIT' ||");
    }

}
