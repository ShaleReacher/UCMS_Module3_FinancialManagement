package cz.mendelu;

import cz.mendelu.Student;
import cz.mendelu.StudentDAO;
import cz.mendelu.StudentService;
import java.util.List;

public class StudentServiceImpl implements StudentService{
    private StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public List<Student> getAllStudents(){
        return studentDAO.getStudents();
    }
}
