import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class Group<Student> extends TreeSet<Student>{
    private Assistant assistant;
    private String ID;
    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        super(comp);
        this.assistant = assistant;
        this.ID = ID;
    }
    public Group(String ID, Assistant assistant) {
        this.assistant = assistant;
        this.ID = ID;

    }
    public void setAssistant(Assistant assistant)
    {
        this.assistant = assistant;
    }
    public String getID()
    {
        return this.ID;
    }
    public boolean equals(Object o) {
        Group new_group = (Group) o;
        if (!(o instanceof Group))
        {
            return false;
        }
        return this.ID.equals(new_group.ID);
    }
    public Assistant getAssistant()
    {
        return this.assistant;
    }
    public TreeSet<Student> getStudents() {
        return this;
    }
    public String toString()
    {
        return "Acesta este ID ul grupei: "+ID;
    }
}


