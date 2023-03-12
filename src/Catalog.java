import java.lang.reflect.Array;
import java.util.*;

public class Catalog implements Subject {
    private static Catalog obj = null;
    private ArrayList<Course> Courses = new ArrayList<>();
    private Vector<Observer> obs = new Vector();
    // private ArrayList<Observer> obs = new ArrayList<>();
    private boolean changed = false;

    public Catalog() {
        // this.Courses = new ArrayList<>();
    }

    public static Catalog getInstance() {
        if (obj == null)
            obj = new Catalog();
        return obj;
    }

    public void addCourse(Course course) {

        boolean res = Courses.contains(course);

        if (!res)
            Courses.add(course);


    }

    public void removeCourse(Course course) {

        boolean res = Courses.contains(course);

        if (res)
            Courses.remove(course);

    }

    public void Show_courses() {
        System.out.println(Courses);
    }

    @Override
    public void addObserver(Observer observer) {
        if (!obs.contains(observer)) {
          //  System.out.println("OVSERVEI:"+observer);
            obs.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (obs.contains(observer)) {
            obs.remove(observer);
        }

    }

    public Vector<Observer> getObservers() {
        return this.obs;
    }

    public Observer getObserver(Student student) {
        Parent mother = student.getMother();
        Parent father = student.getFather();

        for (Observer x : obs) {
            if (mother != null) {
                if (((Parent) x).getLastName().equals(mother.getLastName())
                        && ((Parent) x).getFirstName().equals(mother.getFirstName())) {
                    // System.out.println("fascsa");
                    return x;
                }
            }
            if (father != null) {
                if (((Parent) x).getLastName().equals(father.getLastName())
                        && ((Parent) x).getFirstName().equals(father.getFirstName())) {
                    return x;
                }
            }
        }
        return null;
    }
    public boolean findObserver(Student student) {
        Parent mother = student.getMother();
        Parent father = student.getFather();

        for (Observer x : obs) {
            if (mother != null) {
                if (((Parent) x).getLastName().equals(mother.getLastName())
                        && ((Parent) x).getFirstName().equals(mother.getFirstName())) {
                    return true;
                }
            }
            if (father != null) {
                if (((Parent) x).getLastName().equals(father.getLastName())
                        && ((Parent) x).getFirstName().equals(father.getFirstName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean findObserverMother(Student student) {
        Parent mother = student.getMother();
      //  Parent father = student.getFather();

        for (Observer x : obs) {
            if (mother != null) {
                if (((Parent) x).getLastName().equals(mother.getLastName())
                        && ((Parent) x).getFirstName().equals(mother.getFirstName())) {
                    return true;
                }
            }
//            if (father != null) {
//                if (((Parent) x).getLastName().equals(father.getLastName())
//                        && ((Parent) x).getFirstName().equals(father.getFirstName())) {
//                    return true;
//                }
//            }
        }
        return false;
    }
    public boolean findObserverFather(Student student) {
       // Parent mother = student.getMother();
        Parent father = student.getFather();

        for (Observer x : obs) {
//            if (mother != null) {
//                if (((Parent) x).getLastName().equals(mother.getLastName())
//                        && ((Parent) x).getFirstName().equals(mother.getFirstName())) {
//                    return true;
//                }
//            }
            if (father != null) {
                if (((Parent) x).getLastName().equals(father.getLastName())
                        && ((Parent) x).getFirstName().equals(father.getFirstName())) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void notifyObservers(Grade grade) {
        // notificare observatori de schimbare
        if(findObserverMother(grade.getStudent()) == true && findObserverFather(grade.getStudent()) == true)
        {
            Observer observer1 = grade.getStudent().getMother();
            Notification notification = new Notification(grade);
            if (observer1 != null)
                observer1.update(notification);
            return;
        }

        if(findObserverMother(grade.getStudent()) == true)
        {
            Observer observer = grade.getStudent().getMother();
            Notification notification = new Notification(grade);
            if (observer != null)
                observer.update(notification);
        }
        if(findObserverFather(grade.getStudent()) == true)
        {
            Observer observer = grade.getStudent().getFather();
            Notification notification = new Notification(grade);
            if (observer != null)
                observer.update(notification);
        }
      //  Observer observed_now = (Observer) getObserver(grade.getStudent());


    }

    public Student findStudent(String firstname, String lastname) {
        for (int i = 0; i < Courses.size(); i++) {
            for (Map.Entry<String, Group> entry : Courses.get(i).getGroups().entrySet()) {
                TreeSet<Student> students = entry.getValue();
                for (Student res : students) {
                    if (firstname.equals(res.getFirstName())
                            && lastname.equals(res.getLastName()))
                        return res;
                }
            }
        }
        return null;
    }

    protected void setChanged() {
// comanda modificare stare obiect observat
        changed = true;
    }

    public ArrayList<Course> getCourses() {
        return this.Courses;
    }

    public String toString() {
        String fin = new String();
        ArrayList<Course> courses = getCourses();

        fin += "Catalog\n";

        for (int i = 0; i < courses.size(); i++) {
            Course curs = courses.get(i);
            fin += "Curs: " + curs.getCourse() + "\n" + "Teacher: " + curs.getTeacher() + "\n" + "Assistants: "
                    + curs.getAssistants() + "\n";
            for (Map.Entry<String, Group> entry : curs.getGroups().entrySet()) {
                String ID = entry.getKey();
                Group value = entry.getValue();
                TreeSet<Student> students = value.getStudents();
                fin += "Group with ID: " + ID + " has the next students:\n";
                for (Student x : students) {
                    Grade grade = curs.getGrade(x);
                    Parent mother = x.getMother();
                    Parent father = x.getFather();

                    fin += "\nSTUDENT: " + x.getFirstName() + " " + x.getLastName() + " has the next grades: ";
                    fin += "partial grade: " + grade.getPartialScore() + " exam grade " + grade.getExamScore() + "\n";
                    fin += "TOTAL GRADE: " + grade.getTotal() + "\n";
                    if (mother != null)
                        fin += "MOTHER: " + x.getMother() + " ";
                    if (father != null)
                        fin += "\nFATHER: " + x.getFather() + " ";
                    fin += "\n";
                }

            }

        }

        return fin;

    }
}
