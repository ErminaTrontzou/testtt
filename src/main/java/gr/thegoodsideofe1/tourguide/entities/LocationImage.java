package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;

@Entity
@Table(name="location_images")
public class LocationImage {
    private int id;
    private int locationId;
    private int imageId;

    public LocationImage() {

    }

    public LocationImage(int id, int locationId, int imageId){
        this.id = id;
        this.locationId = locationId;
        this.imageId = imageId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "location_id")
    public int getLocationId() {
        return locationId;
    }
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Column(name = "image_id")
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
