package com.drew.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
import com.drew.model.bean.BlogBean;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Blog extends Model<Blog> implements IBean  {
	public static final Blog dao = new Blog().dao();

    // 数据 bean 对象转 Model
     public void beanToModel(BlogBean bean) {
            this.set("id",bean.getId());
            this.set("title",bean.getTitle());
            this.set("content",bean.getContent());
     }
}
