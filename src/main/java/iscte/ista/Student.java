package iscte.ista;
public class Student {
    private String name;
    private int student_number;

    public Student() {
    }

    public Student (int student_number, String name){
        this.name = name;
        this.student_number = student_number;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setStudent_number(int student_number) {
        this.student_number = student_number;
    }

    public String getName() {
        return name;
    }

    public int getStudent_number() {
        return student_number;
    }

    public String toCSV() {
        return student_number + "," + name;
    }
}