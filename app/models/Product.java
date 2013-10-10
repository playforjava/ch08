package models;


import play.data.validation.Constraints;
import play.libs.F;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;

import play.db.ebean.Model;

import javax.persistence.*;

import java.util.*;
import utils.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.validation.*;
import javax.validation.metadata.*;
import com.avaje.ebean.*;


@Entity
public class Product extends Model implements PathBindable<Product>,
    QueryStringBindable<Product> {

  private static List<Product> products;

  @Target({FIELD})
  @Retention(RUNTIME)
  @Constraint(validatedBy = EanValidator.class)
  @play.data.Form.Display(name="constraint.ean", attributes={"value"})
  public static @interface EAN {
      String message() default EanValidator.message;
      Class<?>[] groups() default {};
      Class<? extends Payload>[] payload() default {};
  }

  public static class EanValidator extends Constraints.Validator<String> implements ConstraintValidator<EAN, String> {
    final static public String message = "error.invalid.ean";
        
    public EanValidator() {}

    @Override
    public void initialize(EAN constraintAnnotation) {}
    
    @Override
    public boolean isValid(String value) {
      String pattern = "^[0-9]{13}$";
      return value != null && value.matches(pattern);
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
      return new F.Tuple<String, Object[]>(message,
          new Object[]{});
    }
  }


  @Id
  public Long id;
  @Constraints.Required
  @EAN
  public String ean;
  @Constraints.Required
  public String name;
  public String description;
  @Constraints.Required
  public Date date = new Date();
  @Constraints.Required
  @DateFormat("yyyy-MM-dd")
  public Date peremptionDate = new Date();
  @Lob
  public byte[] picture;

  @ManyToMany(cascade = CascadeType.ALL)
  public List<Tag> tags = new LinkedList<Tag>();

  @OneToMany(mappedBy = "product")
  public List<StockItem> stockItems;

  public static Finder<Long, Product> find = new Finder<Long, Product>(Long.class, Product.class);

  public Product() {
    // Left empty
  }

  public Product(String ean, String name, String description) {
    this.ean = ean;
    this.name = name;
    this.description = description;
  }

  public String toString() {
    return String.format("%s - %s", ean, name);
  }

  // public static List<Product> findAll() {
  //   return find.all();
  // }

  public static Page<Product> find(int page) {
    return 
            find.where()
                .orderBy("id asc")
                .findPagingList(10)
                .setFetchAhead(false)
                .getPage(page);
  }

  public static Product findByEan(String ean) {
    return find.where().eq("ean", ean).findUnique();
  }

  @Override
  public Product bind(String key, String value) {
    return findByEan(value);
  }

  @Override
  public F.Option<Product> bind(String key, Map<String, String[]> data) {
    return F.Option.Some(findByEan(data.get("ean")[0]));
  }

  @Override
  public String unbind(String s) {
    return this.ean;
  }

  @Override
  public String javascriptUnbind() {
    return this.ean;
  }
}
