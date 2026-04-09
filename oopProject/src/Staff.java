import java.time.LocalDate;

public abstract class Staff {

    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;
    private int workingHours;

      //Constructor
    public Staff(String username, String password, LocalDate dateOfBirth, Role role, int workingHours) {

        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.workingHours = workingHours;
      
    }

  

  
