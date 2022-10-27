package entity;

public class User {
    private String name;
    private String lastName;
    private String login;
    private String password;
    private int age;
    private String gender;
    private int id;

    public User(){};

    public User(int id, String firstName, String lastName, String login,  int age, String gender) {
        this.id = id;
        this.name= firstName;
        this.lastName = lastName;
        this.login = login;
        this.age = age;
        this.gender = gender;
        this.password = "";
    }
    public User( String firstName, String lastName, String login,  int age, String gender) {
        this.name= firstName;
        this.lastName = lastName;
        this.login = login;
        this.age = age;
        this.gender = gender;
        this.password = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
