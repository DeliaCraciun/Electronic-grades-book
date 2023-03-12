public class Notification {
    Grade grade;

    public Notification(Grade grade){
        this.grade = grade;
    }
    public String toString()
    {
        if(grade.getStudent().getMother() != null
        && grade.getStudent().getFather() != null)
        {
            String fin = grade.getStudent().getMother()+" The student " + grade.getStudent().getFirstName() +" "+ grade.getStudent().getLastName() +
                    " had its grades modified "+grade.getPartialScore() +" "+ grade.getExamScore() +" " + grade.getTotal() +" "+ grade.getCourse()+"\n";
            String fin1 = "\nNew notification for: " + grade.getStudent().getFather()+" The student " + grade.getStudent().getFirstName() +" "+ grade.getStudent().getLastName() +
                    " had its grades modified "+grade.getPartialScore() +" "+ grade.getExamScore() +" " + grade.getTotal()+" "+ grade.getCourse() +"\n";
            grade.getStudent().getMother().addNotif(fin);
            grade.getStudent().getFather().addNotif(fin1);
            fin +=fin1;
            return fin;
        }
        if(grade.getStudent().getMother() != null)
        {
            String fin = grade.getStudent().getMother()+" The student " + grade.getStudent().getFirstName() +" "+ grade.getStudent().getLastName() +
                    " had its grades modified "+grade.getPartialScore() +" "+ grade.getExamScore() +" " + grade.getTotal() +" "+ grade.getCourse()+"\n";
            grade.getStudent().getMother().addNotif(fin);
            return fin;
        }

        if(grade.getStudent().getFather() != null)
        {
            String fin = grade.getStudent().getFather()+" The student " + grade.getStudent().getFirstName() +" "+ grade.getStudent().getLastName() +
                    " had its grades modified "+grade.getPartialScore() +" "+ grade.getExamScore() +" " + grade.getTotal()+" "+ grade.getCourse()+"\n";
            grade.getStudent().getFather().addNotif(fin);
            return  fin;
        }

        return null;

    }
}
