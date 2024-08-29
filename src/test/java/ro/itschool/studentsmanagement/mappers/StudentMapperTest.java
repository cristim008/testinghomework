package ro.itschool.studentsmanagement.mappers;


import org.junit.jupiter.api.Test;
import ro.itschool.studentsmanagement.controllers.dtos.StudentDto;
import ro.itschool.studentsmanagement.persistence.entity.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    @Test
    void testToEntity() {
        // Given
        Long id = 1L;
        String name = "Cristi";
        int age = 51;
        String email = "cristi@gmail.com";
        String phone = "555-2245";
        String address = "Turda, str 123";

        StudentDto studentDto = new StudentDto(id, name, age, email, phone, address);


        // When
        Student student = StudentMapper.toEntity(studentDto);

        // Then
        assertNotNull(student, "The student object should not be null");
        assertEquals(id, student.getId(), "The ID should match");
        assertEquals(name, student.getName(), "The name should match");
        assertEquals(age, student.getAge(), "The age should match");
        assertEquals(email, student.getEmail(), "The email should match");
        assertEquals(phone, student.getPhone(), "The phone should match");
        assertEquals(address, student.getAddress(), "The address should match");
    }

    @Test
    void testToEntityWithNullValues() {
        // Given
        StudentDto studentDto = new StudentDto(null, null, null, null, null, null);


        // When
        Student student = StudentMapper.toEntity(studentDto);

        // Then
        assertNotNull(student, "The student object should not be null even when input is null");
        assertNull(student.getId(), "The ID should be null");
        assertNull(student.getName(), "The name should be null");
        assertNull(student.getAge(), "The age should be null");
        assertNull(student.getEmail(), "The email should be null");
        assertNull(student.getPhone(), "The phone should be null");
        assertNull(student.getAddress(), "The address should be null");
    }

    @Test
    void testToDto() {

        //Give

        Long id = 1L;
        String name = "Cristi";
        int age = 51;
        String email = "cristi@gmail.com";
        String phone = "555-2245";
        String address = "Turda, str 123";

        Student student = new Student(id, name, age, email, phone, address);

        //When

        StudentDto studentDto=StudentMapper.toDto(student);


        //Then

        assertNotNull(student, "The student object should not be null");
        assertEquals(id, student.getId(), "The ID should match");
        assertEquals(name, student.getName(), "The name should match");
        assertEquals(age, student.getAge(), "The age should match");
        assertEquals(email, student.getEmail(), "The email should match");
        assertEquals(phone, student.getPhone(), "The phone should match");
        assertEquals(address, student.getAddress(), "The address should match");
    }

    @Test
    void testToDtoAndNotNull(){

        //Given

      Student student = new Student(null, null, null, null, null, null);

        //When

        StudentDto studentDto1=StudentMapper.toDto(student);

        //Then

        assertNotNull(student, "The student object should not be null even when input is null");
        assertNull(student.getId(), "The ID should be null");
        assertNull(student.getName(), "The name should be null");
        assertNull(student.getAge(), "The age should be null");
        assertNull(student.getEmail(), "The email should be null");
        assertNull(student.getPhone(), "The phone should be null");
        assertNull(student.getAddress(), "The address should be null");
    }
}
