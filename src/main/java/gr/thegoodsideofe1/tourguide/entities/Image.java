package gr.thegoodsideofe1.tourguide.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="images")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="file_name")
    private String file_name;
    @Column(name="description")
    private String description;
    @Column(name="title")
    private String title;
    @Column(name="latitude")
    private String latitude;
    @Column(name="longitude")
    private String longitude;
    @Column(name="views")
    private int views;
    @Column(name="owner_name")
    private String owner_name;
    @Column(name="date_taken")
    private String date_taken;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "image_tags",
            joinColumns = {
                    @JoinColumn(name = "image_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private  Set<Tag> tags;

    public Set<Tag> getTags(){
        return tags;
    }
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }



    public Image(){

    }

    public Image(int id,String file_name, String description, String title, String latitude, String longitude, int views,String owner_name, String date_taken){
        this.id=id;
        this.file_name=file_name;
        this.description=description;
        this.title=title;
        this.latitude=latitude;
        this.longitude=longitude;
        this.views=views;
        this.owner_name=owner_name;
        this.date_taken=date_taken;
    }


    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }


    public String getFile_name(){
        return this.file_name;
    }
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }


    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getLatitude(){
        return this.latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getLongitude(){
        return this.longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public int getViews(){
        return this.views;
    }
    public void setViews(int views) {
        this.views = views;
    }


    public String getOwner_name(){
        return this.owner_name;
    }
    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }


    public String getDate_taken(){
        return this.date_taken;
    }
    public void setDate_taken(String date_taken) {
        this.date_taken = date_taken;
    }





//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }


}
