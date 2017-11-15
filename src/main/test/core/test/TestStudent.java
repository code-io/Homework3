package core.test;

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

public class TestStudent {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;
    
    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test // Student successfully registered in class 
    public void studentSuccesfullyRegisered() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Mark","Test", 2017);
        assertTrue(this.student.isRegisteredFor("Mark", "Test", 2017));
    }
    
    @Test // Student successfully registered for class with right year 
    public void studentSuccesfullyRegiseredWithRightYear() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2018, "Instructor", 15);
        this.student.registerForClass("Mark","Test", 2017);
        assertTrue(this.student.isRegisteredFor("Mark", "Test", 2017));
        assertFalse(this.student.isRegisteredFor("Mark", "Test", 2018));
    }
    
    @Test // Student successfully dropped class with right year 
    public void studentSuccesfullyDroppedWithRightYear() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2018, "Instructor", 15);
        this.student.registerForClass("Mark","Test", 2017);
        this.student.registerForClass("Mark","Test", 2018);
        this.student.dropClass("Mark", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Mark", "Test", 2017));
        assertTrue(this.student.isRegisteredFor("Mark", "Test", 2018));
    }
    
    @Test // Student cannot register for class with full capacity
    public void studentCannotRegisterWithFullCapacity() {
    		this.admin.createClass("Test", 2017, "Instructor", 1);
        this.student.registerForClass("Mark","Test", 2017);
        this.student.registerForClass("Tim","Test", 2017);
        assertFalse(this.student.isRegisteredFor("Mark", "Test", 2017));
    }
    
    @Test // Student successfully registered with one open spot
    public void studentSuccesfullyRegiseredOpenSpot() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.student.registerForClass("Mark","Test", 2017);
        assertTrue(this.student.isRegisteredFor("Mark", "Test", 2017));
    }
   
    @Test // Student successfully dropped class
    public void studentSuccesfullyDropped() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Mark","Test", 2017);
        this.student.dropClass("Mark", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Mark", "Test", 2017));
    }
    
    @Test // Student successfully submits homework
    public void studentSubmitsHomework() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.registerForClass("Mark","Test", 2017);
        this.student.submitHomework("Mark", "HW1", "Answer", "Test", 2017);
        assertTrue(this.student.hasSubmitted("Mark", "HW1", "Test", 2017));
    }
    
    @Test // Student must be registered to submit hw
    public void studentMustBeRegisteredToSubmitHW() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.submitHomework("Mark", "HW1", "Answer", "Test", 2017);
        assertFalse(this.student.hasSubmitted("Mark", "HW1", "Test", 2017));
    } 
}