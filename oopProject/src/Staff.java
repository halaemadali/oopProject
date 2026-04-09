import java.time.LocalDate;

public abstract class Staff {

    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;  //ADMIN or RECEPTIONIST
    private int workingHours;

      //Constructor
    public Staff(String username, String password, LocalDate dateOfBirth, Role role, int workingHours) {

        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.workingHours = workingHours;
      
    }

    

   //Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    

    //Setters
    public void setUsername(String username) {

        if (username == null || username.isEmpty()) {
            System.out.println("username cannot be empty");
            return;
        }
            this.username = username;

    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            System.out.println("INVALID PASSWORD");
            return;
        } else if (password.length() < 6) {
            System.out.println("PASSWORD MUST BE AT LEAST 6 CHARACTERS");
            return;
        } else {
            this.password = password;
        }
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {
            System.out.println("INVALID DATE OF BIRTH");
            return;
        }
        this.dateOfBirth = dateOfBirth;

    }

    public void setWorkingHours(int workingHours) {
        if(workingHours <= 0){
            System.out.print("INVALID WORKING HOURS");
            return;
        }

        this.workingHours = workingHours;
    }


          //Common Behaviors
      
      //Prints all guests in the system
       public void viewAllGuests(){
        for(Guest g : HotelDatabase.guests) {
            System.out.println(g);
        }
    }

    
    // Prints all rooms in the system
     public void viewAllRooms() {
        for (Room r : HotelDatabase.rooms){
            System.out.println(r);
         }
    }
    

      // Prints all reservations in the system
     public void viewAllReservations() {
        for (Reservation res : HotelDatabase.reservations) {
            System.out.println(res);
        }
    }
    

  //Returns a string representation of the Staff member
     @Override
    public String toString(){
        return "username: "+ username + " ,Role" + role + " ,Working Hours " + workingHours ;

    }



}

    


  
