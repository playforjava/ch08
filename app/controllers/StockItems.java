package controllers;

import models.StockItem;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class StockItems extends Controller {

  public static Result index() {
    List<StockItem> items = StockItem.find()
        .where()
        .ge("quantity", 300)
        .orderBy("quantity")
        .setMaxRows(10)
        .findList();
    return ok(items.toString());
  }
}
