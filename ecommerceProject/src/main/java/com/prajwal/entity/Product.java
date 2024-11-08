package com.prajwal.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // TO AUTO GENERATE THE ID ONLY 
	private Integer id;
	private String name;
	private String description;
	private String brand;
	private BigDecimal price;
	private String category;
	
	private Boolean productAvailable;
	private Integer stockQuantity;
	
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	private String imageName;
	private String imageType;
	
	@Lob
	private byte[] imageData;
	 
}
