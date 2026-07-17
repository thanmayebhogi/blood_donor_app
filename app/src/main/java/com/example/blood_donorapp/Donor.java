import com.example.blood_donorapp.R;
public class Donor {
    String name, bloodGroup, city, phone;

    public Donor(String name, String bloodGroup, String city, String phone) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.phone = phone;
    }
    // Getters
    public String getName() { return name; }
    public String getBloodGroup() { return bloodGroup; }
    public String getCity() { return city; }
    public String getPhone() { return phone; }
}