package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "is_admin")
    private boolean is_admin;
    @Column(name = "disabled")
    private boolean disabled;

    @JsonIgnore
    @OneToMany(mappedBy = "user", targetEntity = UserCollection.class ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserCollection> userCollections;

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


    public int getId(){
        return this.id;
    }
    public void setId(int newID){
        this.id = newID;
    }

    public String getFirst_name(){
        return this.first_name;
    }
    public void setFirst_name(String newFirstName){
        this.first_name = newFirstName;
    }

    public String getLast_name(){
        return this.last_name;
    }
    public void setLast_name(String newLastName){
        this.last_name = newLastName;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public boolean getIs_admin(){
        return this.is_admin;
    }
    public void setIs_admin(boolean newIsAdmin){
        this.is_admin = newIsAdmin;
    }

    public boolean getDisabled(){
        return this.disabled;
    }
    public void setDisabled(boolean newDisabled){
        this.disabled = newDisabled;
    }
}
