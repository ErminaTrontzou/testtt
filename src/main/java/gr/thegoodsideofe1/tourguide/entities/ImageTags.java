package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;

@Entity
@Table(name="image_tags")
public class ImageTags {
    private int id;
    private int tag_id;
    private int image_id;

    public ImageTags(){

    }

    public ImageTags(int id,int tag_id,int image_id){
        this.id=id;
        this.tag_id=tag_id;
        this.image_id=image_id;
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
    public int getTag_id(){
        return this.tag_id;
    }
    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    @Column(name="image_id")
    public int getImage_id(){
        return this.image_id;
    }
    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}

