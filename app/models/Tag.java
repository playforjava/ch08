package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Tag extends Model {

  public static Tag findById(Long id) {
    return find.byId(id);
  }

  @Id
  public Long id;
  public String name;

  @ManyToMany(mappedBy = "tags")
  public List<Product> products;

  public static Finder<Long, Tag> find =
    new Finder<Long, Tag>(Long.class, Tag.class);
  

  public Tag(){
    // Left empty
  }

  public Tag(Long id, String name, Collection<Product> products) {
    this.id = id;
    this.name = name;
    this.products = new LinkedList<Product>(products);
    for (Product product : products) {
      product.tags.add(this);
    }
  }
}
