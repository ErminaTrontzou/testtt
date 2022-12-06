package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="locations")
public class Location implements Serializable{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column(name="latitude")
        private String latitude;
        @Column(name="longitude")
        private String longitude;
        @Column(name="image_count")
        private int imageCount;
        @Column(name = "name")
        private String name;

        @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinTable(name = "location_images",
                joinColumns = {
                        @JoinColumn(name = "location_id", referencedColumnName = "id",
                                nullable = false, updatable = false)},
                inverseJoinColumns = {
                        @JoinColumn(name = "image_id", referencedColumnName = "id",
                                nullable = false, updatable = false)})
        private Set<Image> images;

    public Set<Image> getImages(){
        return images;
    }
    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Location(){

    }

    public Location(int id, String latitude, String longitude, int imageCount, String name){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageCount= imageCount;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getImageCount() {
        return imageCount;
    }
    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

