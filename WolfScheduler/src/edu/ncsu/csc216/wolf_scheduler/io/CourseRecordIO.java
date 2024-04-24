/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_scheduler.course.Course;

/**
 * CourseRecordIO takes in files as input and reads and processes them to construct course records with the given inputs.
 * Records can also be written to files.
 * 
 * @author hmreese2
 *
 */
public class CourseRecordIO {

	 /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     * @throws IllegalArgumentException if there is an error constructing course.
     */
	public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    ArrayList<Course> courses = new ArrayList<Course>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readCourse, and get the object
	            //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
	            Course course = readCourse(fileReader.nextLine()); 

	            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < courses.size(); i++) {
	                //Get the course at index i
	                Course current = courses.get(i);
	                //Check if the name and section are the same
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop, no need to continue searching
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                courses.add(course); //Add to the ArrayList!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	            //The line is invalid b/c we couldn't create a course, skip it!
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the ArrayList with all the courses we read!
	    return courses;
	}
    
    /**
     * Private helper method that processes information from each line of a file to collect values and construct a course 
     * from the data provided in the lines of the file
     * @param nextLine the next line that is being read and processed from the given file.
     * @return course object that is constructed using the values processed from the line of the file.
     * @throws IllegalArgumentException if there is an error constructing course.
     */
    private static Course readCourse(String nextLine) {
		Scanner line = new Scanner(nextLine);
		line.useDelimiter(",");
		
		try { 
			String name = line.next();
			String title = line.next();
			String section = line.next();
			int credits = line.nextInt();
			String instructorId = line.next();
			String meetingDays = line.next();
			
			if ("A".equals(meetingDays)) {
				if (line.hasNext()) {
					line.close();
					throw new IllegalArgumentException();
				} else {
					line.close();
					return new Course(name, title, section, credits, instructorId, meetingDays);
				}
			} else {
				int startTime = line.nextInt();
				int endTime = line.nextInt();
				
				if (line.hasNext()) {
					line.close();
					throw new IllegalArgumentException();
				} else {
					line.close();
					return new Course(name, title, section, credits, instructorId, meetingDays, startTime, endTime);
				}
			}
		} catch (Exception e) {
			line.close();
			throw new IllegalArgumentException();
		}			
	}

}
