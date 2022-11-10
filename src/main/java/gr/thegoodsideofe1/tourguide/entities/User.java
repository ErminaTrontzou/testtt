package gr.thegoodsideofe1.tourguide.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "is_admin")
    private boolean isAdmin;
    @Column(name = "disabled")
    private boolean disabled;

    @JsonIgnore
    @OneToMany(mappedBy = "user", targetEntity = UserCollection.class ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserCollection> userCollections;

    private static final int encryptFactorLength = 2048;

    public User(){}

    public User(int id, String firstName, String lastName, String email, String username, String password, boolean isAdmin, boolean disabled){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.disabled = disabled;
    }


    public int getId(){
        return this.id;
    }
    public void setId(int newID){
        this.id = newID;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String newFirstName){
        this.firstName = newFirstName;
    }

    public String getLastName(){
        return this.lastName;
    }
    public void setLastName(String newLastName){
        this.lastName = newLastName;
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

    public boolean getIsAdmin(){
        return this.isAdmin;
    }
    public void setIsAdmin(boolean newIsAdmin){
        this.isAdmin = newIsAdmin;
    }

    public boolean getDisabled(){
        return this.disabled;
    }
    public void setDisabled(boolean newDisabled){
        this.disabled = newDisabled;
    }
}
