package com.example.HealthyCampus.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by wapchief on 2017/7/25.
 * 搜索好友历史
 */

@Entity
public class SearchBook {
    @Id(autoincrement = true)
    private Long id;
    private String bookName;


    @Generated(hash = 815880223)
    public SearchBook(Long id, String bookName) {
        this.id = id;
        this.bookName = bookName;
    }

    @Generated(hash = 2121991192)
    public SearchBook() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
