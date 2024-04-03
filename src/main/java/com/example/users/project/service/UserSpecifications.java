package com.example.users.project.service;


import com.example.users.project.domain.Address;
import com.example.users.project.domain.Department;
import com.example.users.project.domain.District;
import com.example.users.project.domain.Region;
import com.example.users.project.domain.Role;
import com.example.users.project.domain.Users;
import com.example.users.project.dto.SearchRequestDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserSpecifications<T> {


    //users tablitsadagi column va valuesi berilsa search qiladi
    public Specification<T> getSearchSpecification(SearchRequestDto searchRequestDto) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
            }
        };
    }

    public Specification<Users> findByFirstName(String firstName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("firstName"), firstName);
    }

    public static Specification<Users> findByLastName(String lastName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("lastName"), lastName);
    }

    public static Specification<Users> findByDepartment(Long departmentId) {
        return (root, query, criteriaBuilder) -> {
            Join<Users, Department> departmentJoin = root.join("department");
            return criteriaBuilder.equal(departmentJoin.get("id"), departmentId);
        };
    }

    public static Specification<Users> findByParentDepartment(Long parentDepartmentId) {
        return (root, query, criteriaBuilder) -> {
            Join<Users, Department> departmentJoin = root.join("department");
            return criteriaBuilder.equal(departmentJoin.get("parentDepartment").get("id"), parentDepartmentId);
        };
    }

    public static Specification<Users> findByRoleName(String roleName) {
        return (root, query, criteriaBuilder) -> {
            Join<Users, Role> roleJoin = root.join("roles");
            return criteriaBuilder.equal(roleJoin.get("name"), roleName);
        };
    }

    public static Specification<Users> findByAddress(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            Join<Users, Address> addressJoin = root.join("address");
            Join<Address, Region> regionJoin = addressJoin.join("region");
            Join<Region, District> districtJoin = regionJoin.join("district");

            Predicate addressPredicate = criteriaBuilder.like(addressJoin.get("address"), "%" + searchTerm + "%");
            Predicate regionPredicate = criteriaBuilder.like(regionJoin.get("name"), "%" + searchTerm + "%");
            Predicate districtPredicate = criteriaBuilder.like(districtJoin.get("name"), "%" + searchTerm + "%");

            return criteriaBuilder.or(addressPredicate, regionPredicate, districtPredicate);
        };
    }
}
