public class Student extends User implements Comparable<Student>{
    private Parent mother;
    private Parent father;
    public Student(String firstName, String lastName)
    {
        super(firstName, lastName);
    }
    public void setMother(Parent mother) {
        this.mother = mother;
    }
    public void setFather(Parent father) {
        this.father = father;
     //   System.out.println(this.father);
    }
    public Parent getMother()
    {
        return this.mother;
    }
    public Parent getFather()
    {
        return this.father;
    }
    @Override
    public int compareTo(Student o) {
        int res = this.getLastName().compareTo(o.getLastName());

        if(res == 0)
        {
            return this.getFirstName().compareTo(o.getFirstName());
        }
        return res;
    }
}
