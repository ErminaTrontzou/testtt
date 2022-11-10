package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;

@Entity
@Table(name="image_tags")
public class ImageTags {
    private int id;
    private int tagId;
    private int imageId;

    public ImageTags(){

    }

    public ImageTags(int id, int tagId, int imageId){
        this.id=id;
        this.tagId = tagId;
        this.imageId = imageId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }



    @Column(name="tag_id")
    public int getTagId(){
        return this.tagId;
    }
    public void setTagId(int tag_id) {
        this.tagId = tag_id;
    }

    @Column(name="image_id")
    public int getImageId(){
        return this.imageId;
    }
    public void setImageId(int image_id) {
        this.imageId = image_id;
    }


}

