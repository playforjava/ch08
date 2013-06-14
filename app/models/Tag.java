package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Tag extends Model {

  public static Finder<Long, Tag> find() {
    return new Finder<>(Long.class, Tag.class);
  }

  public static Tag findById(Long id) {
    return find().byId(id);
  }

  @Id
  public Long id;
  public String name;

  @ManyToMany(mappedBy = "tags")
  public List<Product> products;


  public Tag(){
    // Left empty
  }

  public Tag(Long id, String name, Collection<Product> products) {
    this.id = id;
    this.name = name;
    this.products = new LinkedList<>(products);
    for (Product product : products) {
      product.tags.add(this);
    }
  }
}
