import java.util.ArrayList;
public class Grade implements Comparable,Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course; // Numele cursului

    public Grade(Student student, String course)
    {
        this.student = student;
        this.course = course;
    }
    public void setPartialScore(Double partialScore)
    {
        this.partialScore = partialScore;
    }
    public void setExamScore(Double examScore)
    {
        this.examScore = examScore;
    }
    public Double getPartialScore()
    {
        return this.partialScore;
    }
    public Double getExamScore()
    {
        return this.examScore;
    }
    public Grade getGrade()
    {
        Grade x = new Grade(this.student, this.course);
        x.setExamScore(this.examScore);
        x.setPartialScore(this.partialScore);
        return x;
    }
    public Student getStudent()
    {
        return this.student;
    }
    public int compareTo(Object o) {
        Grade grade = (Grade) o;

        return this.getTotal().compareTo(grade.getTotal());
        //return val: > 0 this > obj
        //< 0 this < obj
        //== 0 this == obj
    }
    public Double getTotal() {
        Double total = this.partialScore + this.examScore;

        return (Double) total;
    }
    public Object clone() throws CloneNotSupportedException {

            Grade cloned_grade = (Grade) super.clone();
            cloned_grade.setPartialScore(this.partialScore);
            cloned_grade.setExamScore(this.examScore);
           // System.out.println("ALA BALA"+cloned_grade.getStudent().getFirstName());
            return cloned_grade;

    }
    public String toString()
    {
        return course + " " + student.getFirstName() + " " +
                student.getLastName() + " " + getTotal();

    }
    public String getCourse()
    {
        return this.course;
    }


}
