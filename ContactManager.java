import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager{
    public static void main(String[] args){
         
         ArrayList<Person> people=new ArrayList<>();
        Scanner input=new Scanner(System.in);
        boolean isrunning=true;
        while (isrunning){
                System.out.println("This your Contact Manager");
                System.out.println("1.Add a Person");
                System.out.println("2.Remove a Person");
                System.out.println("3.Search for a Person");
                System.out.println("4.View all contacts");
                System.out.println("5.Exit");
                System.out.println("Enter Your Choice:");
                int choice=input.nextInt(); 
                input.nextLine();
            switch (choice) {
            case 1 -> AddPerson(people,input);
            case 2 -> removePerson( people, input);
            case 3 -> System.out.println("Search selected");
            case 4 ->  viewAll(people);
            case 5 ->{ System.out.println("Exiting... Exit");
                      isrunning=false;}
            default -> System.out.println("Invalid choice");
        }
           
        }  input.close(); }
 
            public static void AddPerson(ArrayList<Person> people,Scanner input){
                System.out.println("Enter the Name:");
                String name=input.nextLine();
                System.out.println("Enter the Phone number:");
                String phone=input.nextLine();
                System.out.println("Enter the age:");
                int age=input.nextInt();
                input.nextLine(); 
                Person p= new Person(name, phone, age);
                people.add(p);
                System.out.println("Person added!");
            }

           public static void viewAll(ArrayList<Person> people) {
            if( people.isEmpty())
                System.err.println("No Contacts Yet");
            else{
                System.out.println("All Contacts:");
               for (Person person : people ){
                System.out.println("The name: "+person.getName());
                System.out.println("The phone: "+person.getPhone());
                System.out.println("The age: "+person.getAge());
                }
            }
           }

         public static void removePerson(ArrayList<Person> people,Scanner input)  {
            System.out.println("Enter the Name you want to remove :");
            String  nameToRemove=input.nextLine();
            boolean found = false;
                for ( int i=0;i<people.size();i++) {
                    if (people.get(i).getName().equalsIgnoreCase(nameToRemove)) {
                        people.remove(people.get(i)); // remove the object from the list
                        System.out.println("Person removed!");
                        found = true;
                        break; // stop the loop after removing
                    }
                }
                if (!found) {
                    System.out.println("No contact with that name found.");
                }
                }}
                












































    
