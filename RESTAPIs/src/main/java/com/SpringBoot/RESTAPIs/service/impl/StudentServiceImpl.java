package com.SpringBoot.RESTAPIs.service.impl;

import com.SpringBoot.RESTAPIs.dto.AddStudentRequestDto;
import com.SpringBoot.RESTAPIs.dto.StudentDto;
import com.SpringBoot.RESTAPIs.entity.Student;
import com.SpringBoot.RESTAPIs.repository.StudentRepository;
import com.SpringBoot.RESTAPIs.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students= studentRepository.findAll();
        List<StudentDto>  studentDtoList= students.stream().map(student ->modelMapper.map(student,StudentDto.class) ).toList();

        return studentDtoList;
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student= studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Student not found with this id"));
        return modelMapper.map(student,StudentDto.class);
    }

    @Override
    public StudentDto createNewStudent(AddStudentRequestDto addStudentRequestDto) {
        Student newStudent=modelMapper.map(addStudentRequestDto,Student.class);
        Student student=studentRepository.save(newStudent);
        return modelMapper.map(student,StudentDto.class);
    }

    @Override
    public void deleteStudentById(Long id) {
        if(!studentRepository.existsById(id)){
            throw new IllegalArgumentException("student does not exist by id"+id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto updateStudent(Long id, AddStudentRequestDto addStudentRequestDto) {
        Student student= studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Student not found with this id"));
        modelMapper.map(addStudentRequestDto,student);  // update student with addStudentRequestDto
        Student savedstudent=studentRepository.save(student);

        return modelMapper.map(savedstudent,StudentDto.class);
    }

    @Override
    public StudentDto updatePartialStudent(Long id, Map<String, Object> updates) {
        Student student= studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Student not found with this id"));

        updates.forEach((field,value)->{
            switch (field){
                case "name" :student.setName((String) value);
                break;
                case "email" :student.setEmail((String) value);
                break;
                default:
                    throw new IllegalArgumentException("Field is not supported");
            }
        });
        Student savedstudent=studentRepository.save(student);
        return modelMapper.map(savedstudent,StudentDto.class);
    }


}
