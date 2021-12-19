package com.example.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Entity
@Table(name = "client")
public class Client  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "passwd")
	private String passwd;

	@Column(name = "salt")
	private String salt;

	@Column(name = "phone")
	private String phone;

	@Column(name = "commonaddress1")
	private String commonaddress1;

	@Column(name = "commonaddress2")
	private String commonaddress2;

}
