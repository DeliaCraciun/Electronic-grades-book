import java.util.HashMap;
import java.util.Map;

public class BestTotalScore implements Strategy{
    @Override
    public Student doOperation(HashMap<Student, Grade> x) {
        Double max_grade = 0.0;
        Student max_student = null;
        for(Map.Entry<Student, Grade> entry : x.entrySet())
        {
            Student st = entry.getKey();
            Grade gr = entry.getValue();
            if(max_grade < gr.getTotal())
            {
                max_student = st;
                max_grade = gr.getTotal();
            }
        }
        return max_student;
    }
    public String toString()
    {
        return "BestTotalScore";
    }
}
