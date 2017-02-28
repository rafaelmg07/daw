package com.daw.themadridnews.user_model;

import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Users")
public class UserEntity {
	
	@Id
    private String email;

    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String alias;
    
    //@OneToMany(cascade = CascadeType.ALL)
	//private List<Comment> comments = new ArrayList<>();
    
    char sex;
    private String city;
    private String country;
    private String phoneNumber;
    private String description;
    private String personalWeb;
    

    public UserEntity(){}


	public UserEntity(String name, String lastName, String alias, String email) {
		this.name = name;
		this.lastName = lastName;
		this.alias = alias;
		this.email = email;
	}	
	
	@Override
	public String toString() {
		return "User [name=" + name + ", lastName=" + lastName + ", alias=" + alias + "]";
	}


	//getters and setters	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public char getSex() {
		return sex;
	}


	public void setSex(char sex) {
		this.sex = sex;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPersonalWeb() {
		return personalWeb;
	}


	public void setPersonalWeb(String personalWeb) {
		this.personalWeb = personalWeb;
	}


}