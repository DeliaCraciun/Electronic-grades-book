import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.util.List;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {

        Catalog cat = Catalog.getInstance();
        JSONParser parser = new JSONParser();
        try{
            FileReader file = new FileReader("E:\\Aplicatii\\Catalog electronic - Copy\\Test.json");
            JSONObject jsonObject = (JSONObject) parser.parse(file);
            JSONArray jsonArray = (JSONArray) jsonObject.get("courses");

            ArrayList<String> types_courses = new ArrayList<>();

            // Mergem prin toate cursurile
            for (int i = 0; i < jsonArray.size(); i++) {
                //get objects from Course
                JSONObject courseObject = (JSONObject) jsonArray.get(i);
                JSONObject teacher = (JSONObject) courseObject.get("teacher");
                JSONArray assistants = new JSONArray();
                assistants = (JSONArray) courseObject.get("assistants");
                JSONArray groups = new JSONArray();
                groups = (JSONArray) courseObject.get("groups");

                //TIP CURS
                String type = (String) courseObject.get("type");
                types_courses.add(type);

                //STRATEGIE CURS
                String strategy = (String) courseObject.get("strategy");
                Strategy final_strategy = null;
                if(strategy.equals("BestExamScore"))
                {
                    final_strategy = new BestExamScore();
                }
                if(strategy.equals("BestPartialScore"))
                {
                    final_strategy = new BestPartialScore();
                }
                if(strategy.equals("BestTotalScore"))
                {
                    final_strategy = new BestTotalScore();
                }
                //nume CURS
                String courseName = (String) courseObject.get("name");

                //nume PROFERSOR
                String firstName_teacher = (String) teacher.get("firstName");
                String lastName_teacher = (String) teacher.get("lastName");

                //nume ASISTENTI
                List<String> firstName_assistants = new ArrayList<>();
                List<String> lastName_assistants = new ArrayList<>();

                //declarare pt FullCourse
                FullCourse.FullCourseBuilder full_course = new FullCourse.FullCourseBuilder((String) courseName);
                full_course.setStrategy(final_strategy);
                full_course.setFullProfessor(new Teacher(firstName_teacher,lastName_teacher));

                //declarare pt PartialCourse
                PartialCourse.PartialCourseBuilder partial_course = new PartialCourse.PartialCourseBuilder((String) courseName);
                partial_course.setStrategy(final_strategy);
                partial_course.setFullProfessor(new Teacher(firstName_teacher,lastName_teacher));

                Course final_course = null;
                if(type.equals("FullCourse"))
                    final_course = (FullCourse) full_course.build();
                if(type.equals("PartialCourse"))
                    final_course = (PartialCourse) partial_course.build();


                cat.addCourse((Course) final_course);

                for(int j = 0; j < assistants.size(); j++)
                {
                    String firstName_ass =(String) ((JSONObject)assistants.get(j)).get("firstName");
                    String lastName_ass =(String) ((JSONObject)assistants.get(j)).get("lastName");
                    firstName_assistants.add(firstName_ass);
                    lastName_assistants.add(lastName_ass);

                }

                //detalii GROUPS
                List<String> IDs = new ArrayList<>();
                List<String> firstName_group_ass = new ArrayList<>();
                List<String> lastName_group_ass = new ArrayList<>();
                //pentru fiecare key=ID grupa avem o lista de nume de studenti
                HashMap<String,List<String>> firstName_group_students = new HashMap<>();
                HashMap<String, List<String>> lastName_group_students = new HashMap<>();
                for(int j = 0; j < groups.size(); j++)
                {
                    IDs.add(j,(String) ((JSONObject)groups.get(j)).get("ID"));
                    JSONObject gr_ass = (JSONObject) ((JSONObject) groups.get(j)).get("assistant");
                    firstName_group_ass.add((String) gr_ass.get("firstName"));
                    lastName_group_ass.add((String) gr_ass.get("lastName"));

                    JSONArray students = (JSONArray) ((JSONObject) groups.get(j)).get("students");
                    HashMap<String, String> mothers = new HashMap<>();
                    HashMap<String, String> fathers = new HashMap<>();
                    List<String> firstName_student = new ArrayList<>();
                    List<String> lastName_student = new ArrayList<>();
                    TreeSet<Student> all_stud = new TreeSet<>();
                    //luam numele si parintii fiecarui student din grupa
                    for(int k = 0; k < students.size(); k++)
                    {
                        firstName_student.add((String) ((JSONObject)students.get(k)).get("firstName"));
                        lastName_student.add((String) ((JSONObject)students.get(k)).get("lastName"));
                        JSONObject mother = (JSONObject) ((JSONObject) students.get(k)).get("mother");
                        //System.out.println(mother.get("lastName"));
                        JSONObject father = (JSONObject) ((JSONObject) students.get(k)).get("father");
                        UserFactory u = new UserFactory();
                        User x_user = u.factory("Student",firstName_student.get(k).toString(),lastName_student.get(k).toString());
                        Student student = (Student) x_user;

                        if(mother == null)
                        {
                            mothers.put((String) "0", (String) "0");
                        }
                        else
                        {
                            UserFactory u_par = new UserFactory();
                            User x_user_par = u_par.factory("Parent",(String) mother.get("firstName"),(String)mother.get("lastName"));
                            Parent mom = (Parent) x_user_par;
                            mothers.put((String)( mother.get("lastName")), (String) (mother.get("firstName")));
                            student.setMother(mom);
                            Vector<Observer> obs = cat.getObservers();
                            int ok =0;
                            for(Observer x : obs)
                            {
                                if(((Parent)x).getLastName().equals(mom.getLastName())
                                    && ((Parent)x).getFirstName().equals(mom.getFirstName()) )
                                {
                                    ok = 1;
                                }
                            }
                            if(ok == 0)
                                cat.addObserver(mom);

                        }
                        if(father == null)
                        {
                            fathers.put((String) "0", (String) "0");
                        }
                        else
                        {
                            UserFactory u_par1 = new UserFactory();
                            User x_user_par1 = u_par1.factory("Parent",(String) father.get("firstName"),(String)father.get("lastName"));
                            Parent dad = (Parent) x_user_par1;
                            fathers.put((String) father.get("lastName"), (String) father.get("firstName"));
                            student.setFather(new Parent((String) father.get("firstName"),(String)father.get("lastName")));
                            if(!cat.getObservers().contains(dad))
                                cat.addObserver(dad);
                        }
                        all_stud.add(student);
                      //  System.out.println("TATA: "+fathers.get(lastName_student.get(k)));
                    }
                    firstName_group_students.put(IDs.get(j), firstName_student);
                    lastName_group_students.put(IDs.get(j), lastName_student);
                    Assistant assistant = new Assistant(firstName_group_ass.get(j), lastName_group_ass.get(j));
                    Group group = new Group(IDs.get(j), assistant);

                    group.addAll(all_stud);
                    final_course.addAssistant(IDs.get(j),assistant );
                    final_course.addGroup(group);


                }

            }

            ScoreVisitor visitor = new ScoreVisitor();
            JSONArray partialScores = (JSONArray) jsonObject.get("partialScores");
            List<String> partial_assistant_firstName = new ArrayList<>();
            List<String> partial_assistant_lastName = new ArrayList<>();
            List<String> partial_student_firstName = new ArrayList<>();
            List<String> partial_student_lastName = new ArrayList<>();
            List<String> partial_courses = new ArrayList<>();
            List<Double> partial_grades = new ArrayList<>();
            for(int i = 0; i < partialScores.size(); i++)
            {
                //set Teacher
                JSONObject assistant_partial = (JSONObject) ((JSONObject) partialScores.get(i)).get("assistant");
                partial_assistant_lastName.add((String) assistant_partial.get("lastName"));
                partial_assistant_firstName.add((String) assistant_partial.get("firstName"));
                Assistant ass = new Assistant(partial_assistant_firstName.get(i), partial_assistant_lastName.get(i));

                //set Student
                JSONObject student_partial = (JSONObject) ((JSONObject) ((JSONObject) partialScores.get(i)).get("student"));
                partial_student_firstName.add((String) student_partial.get("firstName"));
                partial_student_lastName.add((String) student_partial.get("lastName"));
                Student std = cat.findStudent((String) student_partial.get("firstName"),(String) student_partial.get("lastName"));


                //set COURSES
                String courses_partial = (String) ( ((JSONObject) partialScores.get(i)).get("course"));
                partial_courses.add((String) courses_partial);

                //set Grades
                Double gr = Double.valueOf((Double) ((JSONObject) partialScores.get(i)).get("grade"));
                partial_grades.add(gr);

                visitor.addPartialExamGrades(ass, std, courses_partial, gr);
                ass.accept(visitor);
               // visitor.visit(ass);

            }
            JSONArray examScores = (JSONArray) jsonObject.get("examScores");
            List<String> exam_teacher_firstName = new ArrayList<>();
            List<String> exam_teacher_lastName = new ArrayList<>();
            List<String> exam_student_firstName = new ArrayList<>();
            List<String> exam_student_lastName = new ArrayList<>();
            List<String> exam_courses = new ArrayList<>();
            List<Double> exam_grades = new ArrayList<>();
            for(int i = 0; i < examScores.size(); i++)
            {
                //set Teacher
                JSONObject teacher_exam = (JSONObject) ((JSONObject) examScores.get(i)).get("teacher");
                exam_teacher_lastName.add((String) teacher_exam.get("lastName"));
                exam_teacher_firstName.add((String) teacher_exam.get("firstName"));
                Teacher tchr = new Teacher(exam_teacher_firstName.get(i), exam_teacher_lastName.get(i));

                //set Student
                JSONObject student_exam = (JSONObject) ((JSONObject) ((JSONObject) examScores.get(i)).get("student"));
                exam_student_firstName.add((String) student_exam.get("firstName"));
                exam_student_lastName.add((String) student_exam.get("lastName"));
                Student std = new Student(exam_student_firstName.get(i), exam_student_lastName.get(i));

                //set COURSES
                String courses_exam = (String) ( ((JSONObject) examScores.get(i)).get("course"));
                exam_courses.add((String) courses_exam);

                //set Grades
                Double gr = Double.valueOf((Double) ((JSONObject) examScores.get(i)).get("grade"));
                exam_grades.add( gr);

                visitor.addExamGrade(tchr, std, courses_exam, gr);
               tchr.accept(visitor);
              //  visitor.visit(tchr);
            }
            //Afisam tot ce e afla in catalog
            System.out.println(cat);

            for(Course x : cat.getCourses())
            {
                System.out.println(x.getStrategy()+": " +x.executeStrategy(x.gettAllStudentGrades()) +" la "+ x.getCourse());
            }
            System.out.println(types_courses.get(0));
            for(int i = 0; i < types_courses.size(); i++)
            {
                if(types_courses.get(i).equals("FullCourse"))
                {
                    ArrayList<Student> grad_stud = ((FullCourse)cat.getCourses().get(i)).getGraduatedStudents();
                    System.out.println("Graduated students for the FULL COURSE: "+cat.getCourses().get(i).getCourse()+" "+grad_stud);
                }
                if(types_courses.get(i).equals("PartialCourse"))
                {
                    ArrayList<Student> grad_stud = ((PartialCourse)cat.getCourses().get(i)).getGraduatedStudents();
                    System.out.println("Graduated students for the PARTIAL COURSE: "+cat.getCourses().get(i).getCourse()+" "+grad_stud);
                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        UI interfata = new UI();
        Parent_Page parent_page = new Parent_Page();

        System.out.println("TRY MEMENTO:\n");
        Course first_c = cat.getCourses().get(0);
        first_c.makeBackup();
        System.out.println("BACKUP FOR COURSE:\n");
        for(Grade gr : first_c.getGrades())
        {
            System.out.println(gr);
        }
        Student try_stud = new Student("--->Delia","Craciun");
        try_stud.setMother(new Parent("Florentina","Craciun"));
        try_stud.setFather(new Parent("Lucina","Craciun"));
        Grade stud = new Grade(try_stud,first_c.getCourse());
        stud.setPartialScore(10000.0);
        stud.setExamScore(3362.0);
        first_c.addGrade(stud);
        System.out.println("\nADDED NEW GRADE FOR STUDENT\n");
        for(Grade gr : first_c.getGrades())
        {
            System.out.println(gr);
        }
        first_c.undo();
        System.out.println("\nUNDO:\n");
        for(Grade gr : first_c.getGrades())
        {
            System.out.println(gr);
        }

    }
}