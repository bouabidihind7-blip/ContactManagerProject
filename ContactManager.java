import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ContactManager{
    public static void main(String[] args){
         
        Scanner input=new Scanner(System.in);
        boolean isrunning=true;
        while (isrunning){
                System.out.println("---------------------------");
                System.out.println("This your Contact Manager❤️❤️");
                System.out.println("---------------------------");
                System.out.println("1.Add a Person");
                System.out.println("---------------------------");
                System.out.println("2.Remove a Person");
                System.out.println("---------------------------");
                System.out.println("3.Search for a Person");
                System.out.println("---------------------------");
                System.out.println("4.View all contacts");
                System.out.println("---------------------------");
                 System.out.println("5.Update contact");
                System.out.println("---------------------------");

                System.out.println("6.Exit");
                System.out.println("---------------------------");
                System.out.println("Enter Your Choice:");
                if (!input.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                input.nextLine(); // clear wrong input
                continue; // restart the loop
                }

                int choice = input.nextInt();
                input.nextLine();
            switch (choice) {
            case 1 -> AddPerson(input);
            case 2 -> removePerson( input);
            case 3 -> searchPerson( input);
            case 4 ->  viewAll();
            case 5 ->  updateContact( input);
            case 6 ->{ System.out.println("Exiting...Thank u for Using Our Contact Manager!!");
                      isrunning=false;}
            default -> System.out.println("Invalid choice");
        }
           
        }  input.close(); }

            public static void AddPerson(Scanner input) {

    // Same validation code as before, nothing changed here
    System.out.println("Enter the Name:");
    String name = input.nextLine();
    if (name == null || name.trim().isEmpty()) { System.out.println("Invalid name"); return; }

    System.out.println("Enter the Phone number:");
    String phone = input.nextLine();
    if (!phone.matches("\\d+")) { System.out.println("Digits only!"); return; }

    System.out.println("Enter the age:");
    if (!input.hasNextInt()) { System.out.println("Age must be a number."); input.nextLine(); return; }
    int age = input.nextInt(); input.nextLine();
    if (age <= 0 || age > 120) { System.out.println("Invalid age."); return; }

    // The SQL command we want to send to MySQL
    // The ? marks are placeholders for the actual values (name, phone, age)
    // We don't write the values directly for security reasons
    String sql = "INSERT INTO contacts (name, phone, age) VALUES (?, ?, ?)";

    // "try with resources" → Java will automatically close the connection
    // when we're done, even if an error happens
    try (
        // Step 1: Open the connection to the database (using our DBConnection file)
        Connection conn = DBConnection.getConnection();

        // Step 2: Prepare the SQL command to be sent
        // Think of it like loading a gun before shooting
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {
        // Now we replace the ? placeholders with the actual values
        // setString(1, name) → first ? gets the name
        // setString(2, phone) → second ? gets the phone
        // setInt(3, age) → third ? gets the age
        stmt.setString(1, name);
        stmt.setString(2, phone);
        stmt.setInt(3, age);

        // Step 3: Actually SEND the SQL command to MySQL and execute it
        // executeUpdate() is used for INSERT, DELETE, UPDATE (anything that modifies data)
        stmt.executeUpdate();

        System.out.println("Person added!");

    // If anything goes wrong (wrong password, db not running, etc.)
    // SQLException catches the error and prints what went wrong
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

          public static void viewAll() {
    // No need for Scanner here since we're just fetching data, not asking for input

    // The SQL command to get ALL rows from the contacts table
    // * means "give me all columns" (name, phone, age)
    String sql = "SELECT * FROM contacts";

    try (
        // Step 1: Open the connection to the database
        Connection conn = DBConnection.getConnection();

        // Step 2: Prepare the SQL command
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Step 3: Execute and store the results in a ResultSet
        // ResultSet is like a table of results that comes back from MySQL
        // executeQuery() is used for SELECT (anything that READS data)
        // notice we use executeQuery() here NOT executeUpdate() like in AddPerson
        // because we're reading data, not modifying it
        ResultSet rs = stmt.executeQuery()
    ) {
        boolean any = false; // flag to check if there are any contacts

        // rs.next() moves to the next row in the results
        // like an iterator — it returns false when there are no more rows
        while (rs.next()) {
            any = true;
            System.out.println("-------------------");

            // rs.getString("name") → gets the value of the "name" column in current row
            // rs.getString("phone") → gets the value of the "phone" column
            // rs.getInt("age") → gets the value of "age" column as an integer
            System.out.println("Name : " + rs.getString("name"));
            System.out.println("Phone: " + rs.getString("phone"));
            System.out.println("Age  : " + rs.getInt("age"));
        }

        // If no rows came back, tell the user
        if (!any) System.out.println("No contacts yet.");

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

        public static void removePerson(Scanner input) {

    // Ask the user which contact to remove
    System.out.println("Enter the name to remove:");
    String name = input.nextLine();

    // The SQL command to delete a row from the contacts table
    // WHERE name = ? means "only delete the row where the name matches"
    // Without WHERE it would delete EVERYTHING in the table, so it's very important!
    String sql = "DELETE FROM contacts WHERE name = ?";

    try (
        // Step 1: Open the connection
        Connection conn = DBConnection.getConnection();

        // Step 2: Prepare the SQL command
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {
        // Replace the ? with the name the user entered
        stmt.setString(1, name);

        // Execute the command and store how many rows were affected
        // executeUpdate() returns an int → number of rows deleted
        int rows = stmt.executeUpdate();

        // If rows > 0 means at least one contact was deleted
        // If rows = 0 means no contact with that name was found
        System.out.println(rows > 0 ? "Person removed!" : "No contact found.");

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

           public static void updateContact(Scanner input) {

    // Ask the user which contact to update
    System.out.println("Enter the name to update:");
    String name = input.nextLine();

    // Ask for the new values
    System.out.println("Enter new phone:");
    String phone = input.nextLine();
    System.out.println("Enter new age:");
    int age = input.nextInt(); 
    input.nextLine(); // clear the newline after the number

    // The SQL command to update a specific contact
    // SET phone = ?, age = ? → the columns we want to change
    // WHERE name = ? → only update the row where name matches
    // Again without WHERE it would update EVERY row in the table!
    String sql = "UPDATE contacts SET phone = ?, age = ? WHERE name = ?";

    try (
        // Step 1: Open the connection
        Connection conn = DBConnection.getConnection();

        // Step 2: Prepare the SQL command
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {
        // Replace the ? placeholders with the actual values
        // 1st ? → new phone
        // 2nd ? → new age
        // 3rd ? → the name to find (WHERE condition)
        stmt.setString(1, phone);
        stmt.setInt(2, age);
        stmt.setString(3, name);

        // Execute and store how many rows were affected
        // same as removePerson — executeUpdate() returns number of rows changed
        int rows = stmt.executeUpdate();

        // rows > 0 → contact was found and updated ✅
        // rows = 0 → no contact with that name existed ❌
        System.out.println(rows > 0 ? "Contact updated!" : "No contact found.");

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}


                public static void searchPerson(Scanner input) {

    // Ask the user which contact to search for
    System.out.println("Enter the name to search:");
    String name = input.nextLine();

    // The SQL command to find a specific contact
    // WHERE name = ? means "only give me the row where name matches"
    String sql = "SELECT * FROM contacts WHERE name = ?";

    try (
        // Step 1: Open the connection
        Connection conn = DBConnection.getConnection();

        // Step 2: Prepare the SQL command
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {
        // Replace the ? with the name the user entered
        stmt.setString(1, name);

        // Execute and store the result
        // We use executeQuery() because we're READING data
        ResultSet rs = stmt.executeQuery();

        // rs.next() tries to move to the first row
        // if it returns true → a contact was found
        // if it returns false → no contact with that name exists
        if (rs.next()) {
            System.out.println("Contact Found:");
            System.out.println("Name : " + rs.getString("name"));
            System.out.println("Phone: " + rs.getString("phone"));
            System.out.println("Age  : " + rs.getInt("age"));
        } else {
            System.out.println("No contact found with the name: " + name);
        }

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}}
                
    
                              










































    
