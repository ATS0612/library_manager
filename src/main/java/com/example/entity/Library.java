package com.example.entity;

import java.util.List; // リレーション設定

import javax.persistence.CascadeType; // リレーション設定
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType; // リレーション設定
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany; // リレーション設定
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "libraries")
public class Library {

    @Id
    @SequenceGenerator(name = "LIBRARY_ID_GENERATOR", sequenceName = "LIBRARY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIBRARY_ID_GENERATOR")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "USER_ID")
    private Integer userId;
    
//    @Column(name = "SAMPLE")
//    private String sample;
    
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.LAZY)// mappedByで@OneToManyと@ManyToOneで取得が被る情報参照を@ManyToOneがつけられたEntityクラスの情報だけで参照する？
    // mappedByがある方が読み取り専用
    // Log.java内のlibraryによってこっちは管理されている
    private List<Log> logHistory;
    
    public List<Log> getLogList() {
    	return this.logHistory;
    }
    

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getUserId() {
      return this.userId;
    }

    public void setUserId(Integer userId) {
      this.userId = userId;
    }
}