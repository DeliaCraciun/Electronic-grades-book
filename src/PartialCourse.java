import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

public class PartialCourse extends Course{
    PartialCourse(CourseBuilder builder) {
        super(builder);
    }

    public static class PartialCourseBuilder extends CourseBuilder{
        public PartialCourseBuilder(String courseName) {
            super(courseName);
        }

        @Override
        public Course build() {
            return new PartialCourse(this);
        }

    }
    @Override
    public ArrayList<Student> getGraduatedStudents() {

        ArrayList<Student> graduated_stud = new ArrayList<Student>();
        SortedSet<Grade> grades = getGrades();
        Iterator<Grade> index = grades.iterator();
        while(index.hasNext())
        {
            //Get the element
            Grade grade = index.next();
            Student student = grade.getStudent();
            if(grade.getTotal() >= 5) {

                graduated_stud.add(student);
            }

        }
        return graduated_stud;

    }
}
