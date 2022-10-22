package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_collections")
public class UserCollection {
    private int id;
    private String name;
    private String description;
    private boolean is_public;
    private int user_id;

    public UserCollection(int id, String name, String description, boolean isPublic, int userID){
        this.id = id;
        this.name = name;
        this.description = description;
        this.is_public = isPublic;
        this.user_id = userID;
    }

    public UserCollection() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return this.id;
    }
    public void setId(int newID){
        this.id = newID;
    }

    @Column(name = "name")
    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }

    @Column(name = "description")
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    @Column(name = "public")
    public boolean getIs_public(){
        return this.is_public;
    }
    public void setIs_public(boolean newIsPublic){
        this.is_public = newIsPublic;
    }

    @Column(name = "user_id")
    public int getUser_id() {
        return this.user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
