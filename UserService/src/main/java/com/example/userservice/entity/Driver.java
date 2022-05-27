package com.example.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Entity
@Table(name = "driver")
public class Driver  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name",length = 255)
	private String name;

	@Column(name = "truename",length = 255)
	private String truename;

	@Column(name = "passwd")
	private String passwd;

	@Column(name = "salt")
	private String salt;

	@Column(name = "identitynum")
	private String identitynum;

	@Column(name = "phone")
	private String phone;

	@Column(name = "numberplate")
	private String numberplate;

	@Column(name = "car")
	private String car;

	@Column(name = "personalprofile")
	private String personalprofile;

	@Column(name = "state")
	private Long state;

	@Column(name = "creditworthiness")
	private Long creditworthiness;

}
