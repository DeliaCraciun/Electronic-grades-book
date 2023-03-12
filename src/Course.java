import java.util.*;

public abstract class Course {
    private final String Course_name;
    private final Teacher full_professor;
    private final Set<Assistant> assistants;
    private SortedSet<Grade> grades;
    private final Map<String, Group> groups;
    private final int credit_points;
    private Strategy strategy;

    private Snapshot snapshot = new Snapshot();
    Course(CourseBuilder builder) {
        this.Course_name = builder.courseName;
        this.full_professor = builder.fullProfessor;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
        this.credit_points = builder.creditPoints;
        this.strategy = builder.strategy;

    }

    public static abstract class CourseBuilder {
        private final String courseName;
        private Teacher fullProfessor;
        private Set<Assistant> assistants = new HashSet<>();
        private SortedSet<Grade> grades = new TreeSet<>();
        private Map<String, Group> groups = new HashMap<>();
        private int creditPoints;
        private Strategy strategy;
        public CourseBuilder(String courseName) {

            this.courseName = courseName;
        }

        public CourseBuilder setFullProfessor(Teacher fullProfessor) {
            this.fullProfessor = fullProfessor;
            return this;
        }

        public CourseBuilder setCreditPoints(int creditPoints) {
            this.creditPoints = creditPoints;
            return this;
        }
        public  CourseBuilder setStrategy(Strategy strategy)
        {
            this.strategy = strategy;
            return  this;
        }

        public abstract Course build();

    }
    private class Snapshot{

        private SortedSet<Grade> Backup;
        public Snapshot()
        {
           Backup = new TreeSet<>();
        }
    }
    public void makeBackup() throws CloneNotSupportedException {
        this.snapshot.Backup = new TreeSet<>();
        Iterator<Grade> index = grades.iterator();
        while(index.hasNext())
        {
            //Get the element
            Grade grade = index.next();
            Student student = grade.getStudent();
            this.snapshot.Backup.add((Grade) grade.clone());

        }
       // this.snapshot.Backup = grades;
       // System.out.println("????BACKUP:"+this.snapshot.Backup);
    }

    public void undo() {

        SortedSet<Grade> old = this.getGrades();
        this.grades = this.snapshot.Backup;
    //    System.out.println("UNDO:"+old);
        this.snapshot.Backup = null;

    }
    public String toString()
    {
        return "NUME CURS " + Course_name;
    }

    public Student executeStrategy(HashMap<Student, Grade> x){
        return strategy.doOperation(x);
    }

    public void addAssistant(String ID, Assistant assistant)
    {
        if(groups.containsKey(ID))
        {
            Group found_group = groups.get(ID);
            found_group.setAssistant(assistant);
        }
        if(!assistants.contains(assistant))
        {
            assistants.add(assistant);
        }
    }
    public void addStudent(String ID, Student student)
    {
        if(groups.containsKey(ID))
        {
            Group found_group = groups.get(ID);
            found_group.add(student);
           // System.out.println("ADAUGAM STUDENT"+found_group.toString());
        }
    }
    public void addGroup(Group group)
    {
        groups.put(group.getID(),group);
    }
    public void addGroup(String ID, Assistant assistant)
    {
        Group new_group = new Group(ID, assistant);
        groups.put(ID, new_group);
    }
    public void addGroup(String ID, Assistant assist, Comparator<Student> comp)
    {
        Group new_group = new Group(ID, assist, comp);
        groups.put(ID, new_group);
    }
    public Grade getGrade(Student student)
    {
        for(Grade x : grades)
        {
            if(x.getStudent().getLastName().equals(student.getLastName()))
            {
                if(x.getStudent().getFirstName().equals(student.getFirstName()))
                {
                    return x;
                }
            }
        }
        //System.out.println("GRADE UL E NULL?");
        return null;
    }
    public void addGrade(Grade grade)
    {
        grades.add(grade);
    }
    //getters
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> all_students = new ArrayList<>();
        for (Map.Entry<String, Group> entry : groups.entrySet()) {
            Group value = entry.getValue();
            all_students.addAll(value);
        }
        return all_students;
    }

    public HashMap<Student, Grade> gettAllStudentGrades()
    {
        HashMap<Student, Grade> all_grades = new HashMap<Student, Grade>();
        Iterator<Grade> index = grades.iterator();
        while(index.hasNext())
        {
            //Get the element
            Grade grade = index.next();
            Student student = grade.getStudent();
            all_grades.put(student, grade);

        }
        return all_grades;
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    public Student getBestStudent() {
       return strategy.doOperation(gettAllStudentGrades());
    }

    public String getCourse()
    {
        return Course_name;
    }
    public Teacher getTeacher()
    {
        return this.full_professor;
    }
    public Set<Assistant> getAssistants()
    {
        return this.assistants;
    }
    public SortedSet<Grade> getGrades()
    {
        return this.grades;
    }
    public Map<String, Group> getGroups()
    {
        return this.groups;
    }
    public int getCredit_points()
    {
        return credit_points;
    }
    public Strategy getStrategy(){return this.strategy;}
//    public Snapshot getSnapshot(){
//        return this.Snapshot;
//    }


}
