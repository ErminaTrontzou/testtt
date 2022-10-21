package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="tags")
public class Tag {
    private int id;
    private String name;

    public Tag(){

    }

    public Tag(int id, String name){
        this.id=id;
        this.name=name;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "imageTags")
    Set<Image> images;
}
