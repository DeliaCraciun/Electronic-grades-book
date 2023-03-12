import java.util.ArrayList;

public class Parent extends User implements Observer {
    ArrayList<String> parent_notification;
    public Parent(String firstName, String lastName)
    {
        super(firstName,lastName);
        this.parent_notification = new ArrayList<>();
    }

    @Override
    public void update(Notification notification) {
        System.out.println("New notification for: "+notification);
    }
    public void addNotif(String x)
    {
        parent_notification.add(x);
    }
}
