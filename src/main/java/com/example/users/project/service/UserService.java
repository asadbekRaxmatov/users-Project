package com.example.users.project.service;

import com.example.users.project.domain.Users;
import com.example.users.project.repository.UsersRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users save(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public Boolean checkUserName(String userName) {
        return usersRepository.existsByUserName(userName);
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public List<Users> findAll(Specification specification) {
        return  usersRepository.findAll(specification);
    }

    public void delete(Long id) {
        Users users = usersRepository.getById(id);
        usersRepository.delete(users);
    }

    //redis
    @Cacheable(value = "user", key = "#id")
    public Users getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public List<Users> findByLastName(String lastName) {
        return usersRepository.findByLastname(lastName);
    }

    public List<Users> findByFirstName(String firstName) {
        return usersRepository.findByFirstName(firstName);
    }

    public List<Users> findByMiddleName(String middleName) {
        return usersRepository.findByMiddleName(middleName);
    }

    public Users findByUserName(String userName) {
        return usersRepository.findByUserName(userName);
    }

}
