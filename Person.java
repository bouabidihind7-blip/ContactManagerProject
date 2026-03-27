public class Person {
    private String phone;
    private String name;
    private int age;

    public Person(String name,String phone,int age){
        this.age=age;
        this.phone=phone;
        this.name=name;
    }
     public String getName(){
        return name;
      }
    public String getPhone(){
        return phone;
    }  
    public int getAge(){
        return age;
    }
}
