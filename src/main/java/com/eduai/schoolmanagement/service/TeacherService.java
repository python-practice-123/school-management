package com.eduai.schoolmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eduai.schoolmanagement.entity.Teacher;
import com.eduai.schoolmanagement.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(String id) {
        return teacherRepository.findById(id);
    }

    public Optional<Teacher> getTeacherByEmployeeId(String employeeId) {
        return teacherRepository.findByEmployeeId(employeeId);
    }

    public Optional<Teacher> getTeacherByEmail(String email) {
        return teacherRepository.findByUserEmail(email);
    }

    public List<Teacher> getTeachersByDepartment(String department) {
        return teacherRepository.findByDepartment(department);
    }

    public List<Teacher> getTeachersByEmploymentType(String employmentType) {
        return teacherRepository.findByEmploymentType(employmentType);
    }

    public List<Teacher> getTeachersBySubject(String subject) {
        return teacherRepository.findBySubjectsContaining(subject);
    }

    public Page<Teacher> searchTeachersByName(String name, Pageable pageable) {
        return teacherRepository.findByFirstNameContainingIgnoreCase(name, pageable);
    }

    public List<Teacher> getHighPerformingTeachers(double performanceThreshold) {
        return teacherRepository.findByPerformanceScoreGreaterThanEqual(performanceThreshold);
    }

    public Teacher saveTeacher(Teacher teacher) {
        log.info("Saving teacher: {}", teacher.getEmployeeId());
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(String id, Teacher teacher) {
        teacher.setId(id);
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(String id) {
        log.info("Deleting teacher with id: {}", id);
        teacherRepository.deleteById(id);
    }

    public long getTeacherCountByDepartment(String department) {
        return teacherRepository.countByDepartment(department);
    }

    public long getTeacherCountByEmploymentType(String employmentType) {
        return teacherRepository.countByEmploymentType(employmentType);
    }
}
