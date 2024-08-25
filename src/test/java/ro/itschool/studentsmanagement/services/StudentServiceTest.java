package ro.itschool.studentsmanagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equals;
import ro.itschool.studentsmanagement.exceptions.StudentNotFoundException;
import ro.itschool.studentsmanagement.persistence.entity.Student;
import ro.itschool.studentsmanagement.persistence.repositories.StudentRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    private StudentRepository mockedStudentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        mockedStudentRepository = mock(StudentRepository.class);
        studentService = new StudentService(mockedStudentRepository);
    }

    @Test
    void getAllStudents() {
        //Given

        Student student = new Student();
        student.setId(1L);
        student.setName("Cristi");
        student.setPhone("555-2245");
        student.setAge(51);
        student.setEmail("number@gmail.com");
        student.setAddress("Turda,str 123");

        List<Student> students = List.of(student);


        //When

        when(mockedStudentRepository.findAll()).thenReturn(students);

        //Then

        List<Student> allStudents = studentService.getAllStudents();
        assertNotNull(allStudents, "The List must have one element");
        Student retrievedStudent = allStudents.getFirst(); // Accesează primul element

        assertEquals(1L, retrievedStudent.getId(), "The id must be 1.");
        assertEquals("Cristi", retrievedStudent.getName(), "The name must be Cristi");
        assertEquals("555-2245", retrievedStudent.getPhone(), "The phone must be 555-2245");
        assertEquals(51, retrievedStudent.getAge(), "The age must be 51");
        assertEquals("number@gmail.com", retrievedStudent.getEmail(), "The email must be number@gmail.com");
        assertEquals("Turda,str 123", retrievedStudent.getAddress(), "The address must be Turda, str 123");


    }

    @Test
    void getStudentById() {


        Student student = new Student();
        student.setId(1L);
        student.setName("Cristi");
        student.setPhone("555-2245");
        student.setAge(51);
        student.setEmail("number@gmail.com");
        student.setAddress("Turda,str 123");

        //When
        when(mockedStudentRepository.findById(1L)).thenReturn(Optional.of(student));

        //Then

        Student resultedStudent = studentService.getStudentById(1L);
        assertNotNull(resultedStudent, "The List must have one element");
        assertEquals("Cristi", resultedStudent.getName(), "The name must be Cristi");
        assertEquals("555-2245", resultedStudent.getPhone(), "The phone must be 555-2245");
        assertEquals(51, resultedStudent.getAge(), "The age must be 51");
        assertEquals("number@gmail.com", resultedStudent.getEmail(), "The email must be number@gmail.com");
        assertEquals("Turda,str 123", resultedStudent.getAddress(), "The address must be Turda, str 123");


    }

    @Test
    void getStudentById_andStudentNotFound() {
        //Given


        //When
        when(mockedStudentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //Then

        assertThrows(StudentNotFoundException.class,
                () -> studentService.getStudentById(1L), "The student must not be found");

    }

    @Test
    void DeleteStudentTest() {
        //Given
        Long studentId = 1L;
        Student student = new Student();
        student.setName("Cristi");
        student.setId(studentId);

        //Simulate if the student exist before deleting
        when(mockedStudentRepository.findById(1L)).thenReturn(Optional.of(student));
        //When
        studentService.deleteStudent(studentId);

        //Then
        verify(mockedStudentRepository).deleteById(studentId);


        //Verfying

        when(mockedStudentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class,
                () -> studentService.getStudentById(studentId), "Student shoul not be found");

    }

    @Test
    void addStudentTest() {

        //Given
        Student student = new Student();
        student.setId(1L);
        student.setName("Cristi");
        student.setPhone("555-2245");
        student.setAge(51);
        student.setEmail("number@gmail.com");
        student.setAddress("Turda,str 123");

        //When

        when(mockedStudentRepository.save(student)).thenReturn(student);

        Student savedStudent = studentService.addStudent(student);

        //Then
        verify(mockedStudentRepository).save(student);

        assertNotNull(savedStudent);
        assertEquals(student.getName(), savedStudent.getName());
        assertEquals(student.getId(), savedStudent.getId());
        assertEquals(student.getAge(), savedStudent.getAge());
        assertEquals(student.getPhone(), savedStudent.getPhone());
        assertEquals(student.getAddress(), savedStudent.getAddress());
        assertEquals(student.getEmail(), savedStudent.getEmail());
    }

    @Test
    void updateStudentTest() {

        //Given
        Student existingstudent = new Student();
        existingstudent.setId(1L);
        existingstudent.setName("Cristi");
        existingstudent.setPhone("555-2245");
        existingstudent.setAge(51);
        existingstudent.setEmail("number@gmail.com");
        existingstudent.setAddress("Turda,str 123");

        Student updateStudent = new Student();
        updateStudent.setName("David");
        updateStudent.setPhone("555-22245");
        updateStudent.setAge(52);
        updateStudent.setEmail("snumber@gmail.com");
        updateStudent.setAddress("sTurda,str 123");


        //When
        when(mockedStudentRepository.findById(1L)).thenReturn(Optional.of(existingstudent));
        when(mockedStudentRepository.save(existingstudent)).thenReturn(existingstudent);

        Student result = studentService.updateStudent(1L, updateStudent);

        //Then
        verify(mockedStudentRepository).findById(1L);
        verify(mockedStudentRepository).save(existingstudent);

        assertEquals("David", result.getName());
        assertEquals("555-22245", result.getPhone());
        assertEquals("snumber@gmail.com", result.getEmail());
        assertEquals(52, result.getAge());
        assertEquals("sTurda,str 123", result.getAddress());


    }

}
