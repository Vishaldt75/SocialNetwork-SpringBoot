package com.social.Network.Repositories;


import org.springframework.data.repository.CrudRepository;

import com.social.Network.Entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role getRoleByName(String name);
}
