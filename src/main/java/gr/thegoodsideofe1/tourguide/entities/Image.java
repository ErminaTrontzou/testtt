package gr.thegoodsideofe1.tourguide.entities;
import gr.thegoodsideofe1.tourguide.entities.Tag;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="images")
public class Image {
    private int id;
    private String file_name;
    private String description;
    private String title;
    private String latitude;
    private String longtitude;
    private int views;
    private String owner_name;
    private String date_taken;

    public Image(){

    }

    public Image(int id,String file_name, String description, String title, String latitude, String longtitude, int views,String owner_name, String date_taken){
        this.id=id;
        this.file_name=file_name;
        this.description=description;
        this.title=title;
        this.latitude=latitude;
        this.longtitude=longtitude;
        this.views=views;
        this.owner_name=owner_name;
        this.date_taken=date_taken;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }

    @Column(name="file_name")
    public String getFile_name(){
        return this.file_name;
    }
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    @Column(name="description")
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="title")
    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="latitude")
    public String getLatitude(){
        return this.longtitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(name="longtitude")
    public String getLongtitude(){
        return this.longtitude;
    }
    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    @Column(name="views")
    public int getViews(){
        return this.views;
    }
    public void setViews(int views) {
        this.views = views;
    }

    @Column(name="owner_name")
    public String getOwner_name(){
        return this.owner_name;
    }
    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    @Column(name="date_taken")
    public String getDate_taken(){
        return this.date_taken;
    }
    public void setDate_taken(String date_taken) {
        this.date_taken = date_taken;
    }

    @ManyToMany
    @JoinTable(
            name = "ImageTags",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    Set<Tag> imageTags;

}
