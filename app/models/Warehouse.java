package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Warehouse extends Model {

  @Id
  public Long id;

  public String name;

  @OneToMany(mappedBy = "warehouse")
  public List<StockItem> stock = new ArrayList<StockItem>();

  @OneToOne
  public Address address;

  @Override
  public String toString() {
    return name;
  }
}
