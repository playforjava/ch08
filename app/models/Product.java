package models;


import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.F;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import javax.persistence.*;
import java.util.*;

@Entity
public class Product extends Model {

  public static class EanValidator extends Constraints.Validator<String> {

    @Override
    public boolean isValid(String value) {
      String pattern = "^[0-9]{13}$";
      return value != null && value.matches(pattern);
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
      return new F.Tuple<String, Object[]>("error.invalid.ean",
          new String[]{});
    }
  }

  @Id
  public Long id;
  @Constraints.Required
  @Constraints.ValidateWith(EanValidator.class)
  public String ean;
  @Constraints.Required
  public String name;
  public String description;
  public byte[] picture;

  @ManyToMany
  public List<Tag> tags = new LinkedList<>();

  @OneToMany(mappedBy = "product")
  public List<StockItem> stockItems;

  public Product() {
  }

  public Product(String ean, String name, String description) {
    this.ean = ean;
    this.name = name;
    this.description = description;
  }

  public String toString() {
    return String.format("%s - %s", ean, name);
  }

  public static Finder<Long, Product> find() {
    return new Finder<>(Long.class, Product.class);
  }
}
