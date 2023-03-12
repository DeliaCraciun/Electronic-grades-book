import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

public class FullCourse extends Course {
    FullCourse(FullCourseBuilder builder) {
        super(builder);
    }


    public static class FullCourseBuilder extends CourseBuilder {
        public FullCourseBuilder(String courseName) {
            super(courseName);
        }

        @Override
        public Course build() {
            return new FullCourse(this);
        }
    }
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduated_stud = new ArrayList<Student>();
        SortedSet<Grade> grades = getGrades();
        Iterator<Grade> index = grades.iterator();
        while(index.hasNext())
        {
            //Get the element
            Grade grade = index.next();
            Student student = grade.getStudent();
            if(grade.getPartialScore() >= 3 && grade.getExamScore() >= 2) {

                graduated_stud.add(student);
            }

        }
        return graduated_stud;
    }
}


