package com.ecommercewebsite.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Role {

	@Id
	private int roleId;
	
	@Column(nullable = false)
	private String roleName;
	
	@ManyToMany(mappedBy = "role")
	Set<User> user = new HashSet<>();
	
	public Role(int roleId, String roleName, Set<User> user) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.user = user;
	}
	

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	
}
