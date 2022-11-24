package CertificateCreator;

/**
 * Stores all the table data into a Person object
 *
 * Each row per Person object
 */

public class People {

    private int id;
    private String name;
    private String surname;
    private int gradesAverage;

    public People(int id, String name, String surname, int gradesAverage) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gradesAverage = gradesAverage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getGrades_average() {
        return gradesAverage;
    }

    public void setGrades_average(int grades_average) {
        this.gradesAverage = grades_average;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", grades_average=" + gradesAverage +
                '}';
    }
}
