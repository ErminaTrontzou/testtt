package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String username;
    private String password;
    private boolean is_admin;
    private boolean disabled;
    private static final int encryptFactorLength = 2048;

    public User(){}

    public User(int id, String first_name, String last_name, String email, String username, String password, boolean is_admin, boolean disabled){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
        this.disabled = disabled;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return this.id;
    }
    public void setId(int newID){
        this.id = newID;
    }

    @Column(name = "first_name")
    public String getFirst_name(){
        return this.first_name;
    }
    public void setFirst_name(String newFirstName){
        this.first_name = newFirstName;
    }

    @Column(name = "last_name")
    public String getLast_name(){
        return this.last_name;
    }
    public void setLast_name(String newLastName){
        this.last_name = newLastName;
    }

    @Column(name = "email")
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    @Column(name = "username")
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    @Column(name = "password")
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    @Column(name = "is_admin")
    public boolean getIs_admin(){
        return this.is_admin;
    }
    public void setIs_admin(boolean newIsAdmin){
        this.is_admin = newIsAdmin;
    }

    @Column(name = "disabled")
    public boolean getDisabled(){
        return this.disabled;
    }
    public void setDisabled(boolean newDisabled){
        this.disabled = newDisabled;
    }
}
