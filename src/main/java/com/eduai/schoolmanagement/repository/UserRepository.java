package com.eduai.schoolmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.eduai.schoolmanagement.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("{'roles': {$in: [?0]}}")
    List<User> findByRolesContaining(User.Role role);

    @Query("{'firstName': {$regex: ?0, $options: 'i'}}")
    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    @Query("{'lastName': {$regex: ?0, $options: 'i'}}")
    List<User> findByLastNameContainingIgnoreCase(String lastName);

    List<User> findByEmailVerified(boolean emailVerified);

    List<User> findByLocked(boolean locked);
}
