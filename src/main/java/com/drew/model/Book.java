package com.drew.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
import com.drew.model.bean.BookBean;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Book extends Model<Book> implements IBean  {
	public static final Book dao = new Book().dao();

    // 数据 bean 对象转 Model
     public void beanToModel(BookBean bean) {
            this.set("id",bean.getId());
            this.set("bookName",bean.getBookName());
            this.set("price",bean.getPrice());
     }
}