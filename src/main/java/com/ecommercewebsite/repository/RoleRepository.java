package com.ecommercewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercewebsite.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
