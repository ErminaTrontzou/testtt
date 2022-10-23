package gr.thegoodsideofe1.tourguide.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tags")
public class Tag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;


    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Image> images = new HashSet<>();

    public Set<Image> getImages(){
        return images;
    }
    public void setImages(Set<Image> images) {
        this.images = images;
    }



    public Tag(){

    }


    public Tag(int id, String name){
        this.id=id;
        this.name=name;
    }


    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }




}
