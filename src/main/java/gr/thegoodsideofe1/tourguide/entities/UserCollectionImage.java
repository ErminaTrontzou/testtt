package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_collections_images")
public class UserCollectionImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "image_id")
    private int imageId;
    @Column(name = "user_collection_id")
    private int userCollectionId;

    public void UserCollection(int id, int imageId, int userCollectionId){
        this.id = id;
        this.imageId = imageId;
        this.userCollectionId = userCollectionId;
    }

    public UserCollectionImage() {

    }

    public int getId(){
        return this.id;
    }
    public void setId(int newID){
        this.id = newID;
    }

    public int getImageId(){
        return this.imageId;
    }
    public void setImageId(int imageId){
        this.imageId = imageId;
    }

    public int getUserCollectionId(){
        return this.userCollectionId;
    }
    public void setUserCollectionId(int userCollectionID){
        this.userCollectionId = userCollectionID;
    }
}
