package com.drew.model.bean;

import com.alibaba.fastjson.JSON;
import com.drew.model.Book;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public class BookBean {

    private java.lang.Integer id;

    private java.lang.String bookName;

    private java.lang.Long price;


	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setBookName(java.lang.String bookName) {
		this.bookName = bookName;
	}

	public java.lang.String getBookName() {
		return this.bookName;
	}

	public void setPrice(java.lang.Long price) {
		this.price = price;
	}

	public java.lang.Long getPrice() {
		return this.price;
	}


    // model ---> bean
	public void modelToBean(Book model){
        setId((java.lang.Integer) model.get("id"));
        setBookName((java.lang.String) model.get("bookName"));
        setPrice((java.lang.Long) model.get("price"));
	}

    public String toJson(){
        return JSON.toJSONString(this);
    }

}