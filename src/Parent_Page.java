import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Parent_Page extends JFrame implements ActionListener {
    JTextField text;
    JButton button;
    JLabel label;
    JTextArea area;
    JScrollPane rb;
    JList<Course> list;
    DefaultListModel<Course> model;
    JPanel rbc;
    Student final_stud = new Student("0","0");


    public Parent_Page()
    {
        super("parent_page");
        //luam datele introduse in clasa Test
        Catalog cat = Catalog.getInstance();

        this.setTitle("Courses");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(3);

        rbc = new JPanel();
        rbc.setLayout(new GridLayout(10, 10));
        this.label = new JLabel("Parent name:");
        this.text = new JTextField(150);

        this.list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener( new ListSelectionListener() {
            public void valueChanged (ListSelectionEvent e) {

                if ( !e.getValueIsAdjusting() ) {
                    Course selected_course = list.getSelectedValue();
                    rbc.removeAll();
                    rbc.revalidate();
                    rbc.repaint();
                    list.setVisible(false);
                    button.setVisible(false);
                    String detalii = "Teacher: "+selected_course.getTeacher().toString();
                    detalii += "\nAssistents: " +selected_course.getAssistants();
                    for(Map.Entry<String, Group> entry: selected_course.getGroups().entrySet())
                    {
                        if(entry.getValue().getStudents().contains(final_stud))
                        {
                            detalii += "\nGroup: " + entry.getKey();
                            detalii += "\nStudent assistent: " + entry.getValue().getAssistant();
                        }
                    }
                    for(Grade grade : selected_course.getGrades())
                    {
                        if(grade.getStudent().getLastName().equals(final_stud.getLastName())
                                && grade.getStudent().getFirstName().equals(final_stud.getFirstName()))
                        {
                            detalii +="\nGrades: "+"\nPartial score: "+ grade.getPartialScore()+
                                    "\nExam score"+grade.getExamScore()
                                    +"\nTotal: " + grade.getTotal();
                        }
                    }
                    JTextArea detalii_curs = new JTextArea(detalii);
                    rbc.add(detalii_curs);
                }
            }
        });
        model = new DefaultListModel<>();

        rbc.add(this.label);
        rbc.add(this.text);
        rbc.add(this.list);
        this.button = new JButton("Search");
        this.button.addActionListener(this);
        this.area = new JTextArea();
        this.rb = new JScrollPane(this.area);
        this.area.setEditable(false);
        this.rb.setVisible(false);
        this.list.setVisible(false);
        this.add(rbc, "North");
        this.add(this.rb, "Center");
        this.add(this.list,"Center");
        this.add(this.button, "South");
        this.pack();
        this.setSize(600, 400);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //this.rb.setVisible(false);
        Catalog cat = Catalog.getInstance();
        rbc.removeAll();
        rbc.revalidate();
        rbc.repaint();
        rbc.setVisible(false);
        list.setVisible(false);
        button.setVisible(false);

        if(e.getSource() instanceof JButton)
        {
            String parent_name = this.text.getText();
            for(Course course : cat.getCourses())
            {
                String[] parts = parent_name.split(" ");
                String firstName = parts[0];
                String lastName = parts[1];
                for(Student student : course.getAllStudents())
                {
                    // System.out.println(student.getFirstName());
                    if(student.getMother() != null) {
                        if (student.getMother().getLastName().equals(lastName)
                                && student.getMother().getFirstName().equals(firstName)) {
                            for(String x : student.getMother().parent_notification)
                            {
                                String[] parts1 = x.split("\n");
                                for(String str : parts1)
                                {
                                    JTextArea notif = new JTextArea(String.valueOf(str)+"\n");
                                    rbc.add(notif,"Center");
                                }
                            }
//                            String x = String.valueOf(student.getMother().parent_notification);
//                            System.out.println(student.getMother().parent_notification);
//                            JTextArea notif = new JTextArea(x);
//                            rbc.add(notif);
                        }
                    }
                    if(student.getFather() != null) {
                        if (student.getFather().getLastName().equals(lastName)
                                && student.getFather().getFirstName().equals(firstName)) {
                            for(String x : student.getFather().parent_notification)
                            {
              //                  System.out.println(x.toString());
                                String[] parts1 = x.split("\n");
                                for(String str : parts1)
                                {
                                    JTextArea notif = new JTextArea(String.valueOf(str)+"\n");
                                    rbc.add(notif,"Center");
                                }
                            }
//                            String x = String.valueOf(student.getFather().parent_notification);
//                            JTextArea notif = new JTextArea(x);
//                            rbc.add(notif);
                        }
                    }
                }
            }
            rbc.setVisible(true);
            //list.setModel(model);
            //list.setVisible(true);


        }

    }
}
