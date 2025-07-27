package com.eduai.schoolmanagement.repository;

import com.eduai.schoolmanagement.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {

    Optional<Teacher> findByEmployeeId(String employeeId);

    @Query("{'user.email': ?0}")
    Optional<Teacher> findByUserEmail(String email);

    List<Teacher> findByDepartment(String department);

    List<Teacher> findByEmploymentType(String employmentType);

    @Query("{'subjects': {$in: [?0]}}")
    List<Teacher> findBySubjectsContaining(String subject);

    @Query("{'user.firstName': {$regex: ?0, $options: 'i'}}")
    Page<Teacher> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

    @Query("{'performanceScore': {$gte: ?0}}")
    List<Teacher> findByPerformanceScoreGreaterThanEqual(double score);

    long countByDepartment(String department);

    long countByEmploymentType(String employmentType);

    // Auto-ID generation support methods
    boolean existsByEmployeeId(String employeeId);
}
