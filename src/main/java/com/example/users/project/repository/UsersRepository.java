package com.example.users.project.repository;



import com.example.users.project.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    List<Users> findByFirstName(String firstname);

    List<Users> findByLastname(String lastName);

    List<Users> findByMiddleName(String middleName);

    Users findByUserName(String userName);

    Boolean existsByUserName(String userName);
}
