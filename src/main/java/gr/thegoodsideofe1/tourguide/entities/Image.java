package gr.thegoodsideofe1.tourguide.entities;


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
    private String fileName;
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
    private String ownerName;
    @Column(name="date_taken")
    private String dateTaken;


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

    public Image(int id,String fileName, String description, String title, String latitude, String longitude, int views,String ownerName, String dateTaken){
        this.id=id;
        this.fileName =fileName;
        this.description=description;
        this.title=title;
        this.latitude=latitude;
        this.longitude=longitude;
        this.views=views;
        this.ownerName =ownerName;
        this.dateTaken =dateTaken;
    }


    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }


    public String getFileName(){
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
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


    public String getOwnerName(){
        return this.ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public String getDateTaken(){
        return this.dateTaken;
    }
    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }





//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }


}
