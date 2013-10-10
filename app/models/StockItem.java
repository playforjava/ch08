package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class StockItem extends Model{

  @Id
  public Long id;

  @ManyToOne
  public Warehouse warehouse;

  @ManyToOne
  public Product product;
  public Long quantity;

  public static Finder<Long, StockItem> find = 
      new Finder<Long, StockItem>(Long.class, StockItem.class);

  @Override
  public String toString() {
    return String.format("%d %s", quantity, product);
  }
}
