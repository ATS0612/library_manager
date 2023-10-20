package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @SequenceGenerator(name = "LOG_ID_GENERATOR", sequenceName = "LOG_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_ID_GENERATOR")
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "LIBRARY_ID")
    private Integer library_id;
    
    @Column(name = "USER_ID")
    private Integer user_id;
    
    @Column(name = "RENT_DATE")
    private LocalDateTime rent_date;
    
    @Column(name = "RETURN_DATE")
    private LocalDateTime return_date;
    
    @Column(name = "RETURN_DUE_DATE")
    private LocalDateTime return_due_date;
    

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    public Integer getLibraryId() {
      return this.library_id;
	  }
	  public void setLibraryId(Integer library_id) {
	      this.library_id = library_id;
	  }

    
    public Integer getUserId() {
      return this.user_id;
    }
    public void setUserId(Integer user_id) {
      this.user_id = user_id;
    }
    
    
    public LocalDateTime getRentDate() {
      return this.rent_date;
    }
    public void setRentDate(LocalDateTime rent_date) {
      this.rent_date = rent_date;
    }
    
    
    public LocalDateTime getReturnDate() {
      return this.return_date;
    }
    public void setReturnDate(LocalDateTime return_date) {
      this.return_date = return_date;
    }
    
    
    public LocalDateTime getReturnDueDate() {
      return this.return_due_date;
    }
    public void setReturnDueDate(LocalDateTime return_due_date) {
      this.return_due_date = return_due_date;
    }
}