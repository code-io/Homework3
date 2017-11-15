package core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;

public class TestAdmin {

    private IAdmin admin;
    private IStudent student;
    
    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }
    
    @Test // Creating a valid class (should succeed) 
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test // Creating a class in the past (expect fail)
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test // Assigning instructor to 2 valid courses (should succeed)
    public void instructorCourseLimitCornerCase() {
	    	this.admin.createClass("Test", 2017, "Instructor", 15);
	    	this.admin.createClass("Test2", 2017, "Instructor", 15);
	    	assertTrue(this.admin.classExists("Test2", 2017));
    }
    
    @Test // Assigning instructor to 3 valid courses (should fail)
    public void instructorCourseLimitViolated() {
	    	this.admin.createClass("Test", 2017, "Instructor", 15);
	    	this.admin.createClass("Test2", 2017, "Instructor", 15);
	    	this.admin.createClass("Test3", 2017, "Instructor", 15);
	    	assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    @Test // ClassName/Year must be unique (expect fail)
    public void testMakeUniqueClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test // ClassName/Year must be unique (expect fail)
    public void testMakeUniqueClass2() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2019, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
        assertTrue(this.admin.classExists("Test", 2018));
    }
    
    @Test // Assigning capacity to 0 (expect fail)
    public void testAssignZeroCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test // Assigning capacity to -1 (expect fail)
    public void testAssignNegativeCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test // change capacity to equal number of students enrolled (expect pass)
    public void changeCapacityToStudentsEnrolled() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Mark","Test", 2017);
        assertTrue(this.student.isRegisteredFor("Mark", "Test", 2017));
        this.admin.changeCapacity("Test", 2017, 1);
        assertEquals(1,this.admin.getClassCapacity("Test", 2017));
    }

    @Test // change capacity to less than number of students enrolled (expect fail)
    public void changeCapacityLowerThanStudentsEnrolled() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        
        // enroll 2 students
        this.student.registerForClass("Mark","Test", 2017);
        this.student.registerForClass("Tim","Test", 2017);
        assertTrue(this.student.isRegisteredFor("Mark", "Test", 2017));
        assertTrue(this.student.isRegisteredFor("Tim", "Test", 2017));
        this.admin.changeCapacity("Test", 2017, 1);
        
        // Capacity should not change
        assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test // Cannot create class with instructor name empty
    public void testMakeClassInstructorNil() {
        this.admin.createClass("Test", 2017, "", 15);
        assertFalse(this.admin.classExists("Test", 2017));
    }     
}