package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_collections_images")
public class UserCollectionImage {
    private int id;
    private int image_id;
    private int user_collection_id;

    public void UserCollection(int id, int image_id, int user_collection_id){
        this.id = id;
        this.image_id = image_id;
        this.user_collection_id = user_collection_id;
    }

    public UserCollectionImage() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return this.id;
    }
    public void setId(int newID){
        this.id = newID;
    }

    @Column(name = "image_id")
    public int getImage_id(){
        return this.image_id;
    }
    public void setImage_id(int imageId){
        this.image_id = imageId;
    }

    @Column(name = "user_collection_id")
    public int getUser_collection_id(){
        return this.user_collection_id;
    }
    public void setUser_collection_id(int userCollectionID){
        this.user_collection_id = userCollectionID;
    }
}
