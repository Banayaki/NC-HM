public class Student {

    private String firstName;
    private String secondName;
    private String groupNumber;

    public Student(String firstName, String secondName, String groupNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.groupNumber = groupNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }
}
