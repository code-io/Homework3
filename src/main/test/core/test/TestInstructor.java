package core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;
import core.api.impl.Instructor;

public class TestInstructor {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;
    
    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test // Instructor adds homework
    public void instructorSuccessfullyCreatesHomework() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        assertTrue(this.instructor.homeworkExists("Test", 2017, "HW1"));
    }
    
    @Test // Instructor cannot add hw to class not assigned to
    public void instructorCannotAddHWToClassNotAssigned() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor1", "Test", 2017, "HW1");
        assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
    }
    
    @Test // Instructor cannot add hw to wrong year
    public void instructorCannotAddHWToWrongYear() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2018, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        assertFalse(this.instructor.homeworkExists("Test", 2018, "HW1"));
    }
    
    @Test // Instructor grades homework (corner case)
    public void instructorAssignsGradeHundred() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.registerForClass("Mark","Test", 2017);
        this.student.submitHomework("Mark", "HW1", "Answer", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Mark", 100);
        assertEquals("100", this.instructor.getGrade("Test", 2017, "HW1", "Mark").toString());
    }
    
    @Test // Instructor grades homework (corner case)
    public void instructorAssignsGradeZero() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.registerForClass("Mark","Test", 2017);
        this.student.submitHomework("Mark", "HW1", "Answer", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Mark", 0);
        assertEquals("0", this.instructor.getGrade("Test", 2017, "HW1", "Mark").toString());
    }
    
    @Test // Instructor assigns incorrect homework grade (must be between 0 and 100)
    public void instructorAssignsIncorrectGrade() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.registerForClass("Mark","Test", 2017);
        this.student.submitHomework("Mark", "HW1", "Answer", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Mark", 110);
        assertEquals(null, this.instructor.getGrade("Test", 2017, "HW1", "Mark").toString());
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Mark", -10);
        assertEquals(null, this.instructor.getGrade("Test", 2017, "HW1", "Mark").toString());
    }   
}