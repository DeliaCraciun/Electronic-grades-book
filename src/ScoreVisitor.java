import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class ScoreVisitor implements Visitor {
    HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScore;
    HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialExam;


    public ScoreVisitor() {
        this.examScore = new HashMap<>();
        this.partialExam = new HashMap<>();
    }

    @Override
    public void visit(Assistant assistant) {
        Catalog cat = Catalog.getInstance();

        ArrayList<Tuple<Student, String, Double>> data = new ArrayList<>();
        ArrayList<Course> courses = cat.getCourses();
        data = partialExam.get(assistant);
        //  System.out.println("CHECK DATA:" +data);
        if (data != null) {
            for (int i = 0; i < 1; i++) {
                Student student = data.get(i).student;
                //  System.out.println("Introduce partial exam:"+student.getFirstName());
                String course = data.get(i).course;
                Double grade = data.get(i).grade;
                HashMap<Student, Grade> student_grade;
                //  new_grade.setExamScore(null);
                for (int j = 0; j < courses.size(); j++) {
                    //daca am gasit cursul in lista de cursuri
                    if (courses.get(j).getCourse().equals(course)) {
                        int index = j;
                        Course course_from_cat = (Course) courses.get(index);
                        student_grade = course_from_cat.gettAllStudentGrades();
                        Grade nota = course_from_cat.getGrade(student);
                        if (nota == null) {
                            Grade grade1 = new Grade(student, course_from_cat.getCourse());
                            //modify his grade because it's already in the Course
                            grade1.setPartialScore(grade);
                            grade1.setExamScore(0.0);

                            course_from_cat.addGrade(grade1);

                            Map<String, Group> all_groups = course_from_cat.getGroups();
                            for (Map.Entry<String, Group> entry : all_groups.entrySet()) {
                                TreeSet<Student> xx = entry.getValue().getStudents();
                                for (Student par_stud : xx) {
                                    //System.out.println("TATA COPIL:"+par_stud.getFather());
                                    if (par_stud.getLastName().equals(student.getLastName())
                                            && par_stud.getFirstName().equals(student.getFirstName())) {
                                        if (cat.findObserver(par_stud) == true)
                                        {
                                            //System.out.println("NEW PARTIAL NOTIFICATION ");
                                            cat.notifyObservers(course_from_cat.getGrade(par_stud));
                                        }

                                    }
                                }

                            }

                        } else {

                            nota.setPartialScore(grade);

                            Map<String, Group> all_groups = course_from_cat.getGroups();
                            for (Map.Entry<String, Group> entry : all_groups.entrySet()) {
                                TreeSet<Student> xx = entry.getValue().getStudents();
                                for (Student par_stud : xx) {
                                    if (par_stud.getLastName().equals(student.getLastName())
                                            && par_stud.getFirstName().equals(student.getFirstName())) {
                                        if (cat.findObserver(par_stud) == true)
                                        {
                                            // System.out.println("RESET PARTIAL NOTIFICATION");
                                            cat.notifyObservers(course_from_cat.getGrade(par_stud));
                                        }

                                    }
                                }

                            }
                        }
                    }

                }
            }

        }


    }

    @Override
    public void visit(Teacher teacher) {
        Catalog cat = Catalog.getInstance();

        ArrayList<Tuple<Student, String, Double>> data = new ArrayList<>();
        ArrayList<Course> courses = cat.getCourses();
        data = examScore.get(teacher);
        //  System.out.println("CHECK DATA:" +data);
        if (data != null) {
            for (int i = 0; i < 1; i++) {
                Student student = data.get(i).student;
                String course = data.get(i).course;
                Double grade_double = data.get(i).grade;
                HashMap<Student, Grade> student_grade;
                for (int j = 0; j < courses.size(); j++) {
                    //daca am gasit cursul in lista de cursuri
                    if (courses.get(j).getCourse().equals(course)) {
                        int index = j;
                        Course course_from_cat = (Course) courses.get(index);
                        student_grade = course_from_cat.gettAllStudentGrades();
                        Grade nota = course_from_cat.getGrade(student);
                        if (nota == null) {
                            Grade grade1 = new Grade(student, course_from_cat.getCourse());
                            //modify his grade because it's already in the Course
                            grade1.setExamScore(grade_double);
                            grade1.setPartialScore(0.0);

                            course_from_cat.addGrade(grade1);

                            Map<String, Group> all_groups = course_from_cat.getGroups();
                            for (Map.Entry<String, Group> entry : all_groups.entrySet()) {
                                TreeSet<Student> xx = entry.getValue().getStudents();
                                for (Student par_stud : xx) {
                                    if (par_stud.getLastName().equals(student.getLastName())
                                            && par_stud.getFirstName().equals(student.getFirstName())) {
                                        if (cat.findObserver(par_stud) == true)
                                        {
                                           // System.out.println("NEW GRADE EXAM NOTIFICATION");
                                            cat.notifyObservers(course_from_cat.getGrade(par_stud));
                                        }

                                    }
                                }

                            }


                        } else{

                            //modify his grade because it's already in the Course
                            course_from_cat.getGrade(student).setExamScore(grade_double);

                        Map<String, Group> all_groups = course_from_cat.getGroups();
                        for (Map.Entry<String, Group> entry : all_groups.entrySet()) {
                            TreeSet<Student> xx = entry.getValue().getStudents();
                            for (Student par_stud : xx) {
                                if (par_stud.getLastName().equals(student.getLastName())
                                        && par_stud.getFirstName().equals(student.getFirstName())) {
                                   if (cat.findObserver(par_stud) == true)
                                    {
                                        //System.out.println("RESET GRADE EXAM NOTIFICATION");
                                        cat.notifyObservers(course_from_cat.getGrade(par_stud));
                                    }
                                    }

                                }
                            }

                        }
                    }
                }

            }
        }


    }

    public void addExamGrade(Teacher teacher, Student student, String course, Double grade) {
        ArrayList<Tuple<Student, String, Double>> list = new ArrayList<>();
        //daca acest profesor a pus deja o nota
        //     if(!examScore.containsKey(teacher))
        {

            Tuple<Student, String, Double> x = new Tuple<>(student, course, grade);
            list.add(x);
            examScore.put(teacher, list);
        }

        // x.add(new Tuple<>(student_course,grade));
        //  examScore.put(teacher, new Tuple<Student, String, Double>(student,course,grade));
        // System.out.println("--->"+list);

    }

    public void addPartialExamGrades(Assistant assistant, Student student, String course, Double grade) {

        ArrayList<Tuple<Student, String, Double>> list = new ArrayList<>();

        {

            Tuple<Student, String, Double> x = new Tuple<>(student, course, grade);
            list.add(x);
            partialExam.put(assistant, list);
        }

    }

    //    public void print(Assistant assistant) {
//        ArrayList<Tuple<Student, String, Double>> tuples = partialExam.get(assistant);
//        for (Tuple tuple : tuples) {
//            System.out.println("!!!!!!!Studentul " + tuple.getStudent() + " are nota " + tuple.getNota());
//        }
//    }
    private class Tuple<T1, T2, T3> {
        T1 student;
        T2 course;
        T3 grade;

        public Tuple(T1 student, T2 course, T3 grade) {
            this.student = student;
            this.course = course;
            this.grade = grade;

        }

        public String toString() {
            return (String) student.toString() + " " + course + " " + grade;
        }

        public Student getStudent() {
            return (Student) this.student;
        }

        public Double getNota() {
            return (Double) this.grade;
        }
    }

}
