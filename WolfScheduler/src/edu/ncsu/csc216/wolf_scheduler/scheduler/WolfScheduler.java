/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**
 * WolfScheduler class is the primary class responsible for managing and and allowing the user to manipulate data related
 * to different activities (Course and Events), as well as the schedules and catalogs that contain these activities.
 * 
 * WolfScheduler allows the user to edit the name of the schedule, reset the schedule, add courses from the course catalog and events to schedule,
 * and remove courses and events from the schedule. Additionally, WolfScheduler provides the functionality to view and display the activities and schedule information
 * in the form of a 2D String array.
 * 
 * @author hmreese2
 *
 */
public class WolfScheduler {
	
	/** Represents the title of the schedule in the WolfScheduler view */
	private String title;
	/** Represents an ArrayList of Courses available in a course catalog. */
	private ArrayList<Course> catalog;
	/** Represents an ArrayList of Activities currently in a schedule */
	private ArrayList<Activity> schedule;

	/**
	 * Constructs a WolfScheduler object with an empty schedule ArrayList, title set to "My Schedule", and catalog ArrayList full
	 * of the courses added from a given course record input file.
	 * @param fileName name of file that is being used to construct the course catalog of the WolfScheduler
	 * @throws IllegalArgumentException with message "Cannot find file." if there is an issue reading the course records and populating course catalog object.
	 */
	public WolfScheduler(String fileName) {
		// construct empty ArrayList for schedule field
		this.schedule = new ArrayList<Activity>();
		// initialize title to "My Schedule"
		this.title = "My Schedule";
		// add Course objects from file to catalog
		try {
			this.catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}

	/**
	 * Gets a 2D String array of the courses listed in the course catalog.
	 * Format: a row for each Course, 4 columns for course name, section, title, and meetingString.
	 * If no courses are in the catalog, then an empty String[][] is returned.
	 * @return 2D String array representation of the course catalog.
	 */
	public String[][] getCourseCatalog() {
		// check if any courses exist
		if (catalog.isEmpty()) {
			return new String[0][0];
		}
		
		// construct a String[][] for course catalog
		String[][] arr = new String[catalog.size()][3];
        for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            arr[i] = c.getShortDisplayArray();
        }
        return arr;
	}

	/**
	 * Gets 2D String array of the activities (course + event) currently in the schedule.
	 * Format: a row for each activity, 3 columns for activity name, section, and title.
	 * If no activity exists in schedule, an empty 2D String array is returned.
	 * @return 2D String array representation of the courses listed in the schedule.
	 */
	public String[][] getScheduledActivities() {
		// check if any activities exist
		if (schedule.isEmpty()) {
			return new String[0][0];
		}
		
		// construct a String[][] for activity schedule
		String[][] arr = new String[schedule.size()][3];
		for (int i = 0; i < schedule.size(); i++) {
			Activity a = schedule.get(i);
			arr[i] = a.getShortDisplayArray();
		}
		
		return arr;
	}

	/**
	 * Gets 2D String array of all of activity information from the schedule.
	 * Format: row for each activity, 7 columns for activity name, section, title, credits, instructorId, meetingDays string, and eventDetails (if applicable).
	 * If no activities are in the schedule, return an empty 2D String array.
	 * @return 2D String array representation of the full information from a activity schedule.
	 */
	public String[][] getFullScheduledActivities() {
		// check if any activities exist
		if (schedule.isEmpty()) {
			return new String[0][0];
		}
		
		// construct a String[][] for activity schedule
		String[][] arr = new String[schedule.size()][7];
		for (int i = 0; i < schedule.size(); i++) {
			Activity a = schedule.get(i);
			arr[i] = a.getLongDisplayArray();
		}
		
		return arr;
	}

	/**
	 * Finds a course within the course catalog using the course's name and section.
	 * If no course can be found with the given parameters, return null.
	 * @param name a course's name in a catalog
	 * @param section a course's section in a catalog
	 * @return course from course catalog using the given course name and section, or null if course DNE.
	 */
	public Course getCourseFromCatalog(String name, String section) {
		// iterate through catalog until a course is found
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		
		// else if no course can be found, return null
		return null;
	}

	/**
	 * Determines if a course already exists in course schedule using the given course name and section,
	 * if it does not then true is returned (because course can be added), otherwise false.
	 * @param name name of the course that is attempting to be added to schedule
	 * @param section section of the course that is attempting to be added to schedule
	 * @return true if course can be added to schedule (DNE), false if it cannot.
	 * @throws IllegalArgumentException with message "You are already enrolled in [course name]" if course name already 
	 * exists in schedule.
	 */
	public boolean addCourseToSchedule(String name, String section) {
		// see if course exists in catalog
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				
				// check if course name is already in schedule
				for (int j = 0; j < schedule.size(); j++) {
					
					if (catalog.get(i).isDuplicate(schedule.get(j))) {
						throw new IllegalArgumentException("You are already enrolled in " + name);
					}
				}
				
				// if course doesn't exist, add course to schedule and return true
				schedule.add(catalog.get(i));
				return true;
				
			}
		}
		
		// otherwise course can't be added, return false
		return false;
		
	}

	/**
	 * Checks if a activity is removable by verifying that it exists in schedule using given activity index in schedule.
	 * If activity is in schedule, activity is removed from schedule and method returns true, otherwise return false.
	 * @param idx index of activity in schedule that is to be removed
	 * @return true if activity exists in schedule and activity has been removed, otherwise return false
	 */
	public boolean removeActivityFromSchedule(int idx) {
		
		try {
			// get activity from schedule
			Activity removed = schedule.get(idx);
			
			for (int i = 0; i < schedule.size(); i++) {
				// find activity to remove in the schedule
				if (removed.equals(schedule.get(i))) {
					// if match is found, remove course and return true
					schedule.remove(idx);
					return true;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		// otherwise return false
		return false;
	}

	/**
	 * Creates a new empty ArrayList for the schedule
	 */
	public void resetSchedule() {
		this.schedule = new ArrayList<Activity>();
	}

	/**
	 * Gets title of schedule
	 * @return title of schedule
	 */
	public String getScheduleTitle() {
		return title;
	}

	/**
	 * Error checks and sets title of schedule
	 * @param title title of schedule
	 * @throws IllegalArgumentException with message "Title cannot be null." if title is null
	 */
	public void setScheduleTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		
		this.title = title;		
	}
	
	/**
	 * Saves schedule to a given file by working with ActivityRecordIO.writeActivityRecords()
	 * @param filename name of file that student's schedule will be saved to
	 * @throws IllegalArgumentException with message "The file cannot be saved." if ActivityRecordIO.writeActivityRecords()
	 * throws an IOException while attempting to export the schedule
	 */
	public void exportSchedule(String filename) {
		try {
			ActivityRecordIO.writeActivityRecords(filename, schedule);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}

	/**
	 * Checks that an event is not already existing in schedule and adds new event to schedule.
	 * @param eventTitle title of event
	 * @param eventMeetingDays days that an event meets
	 * @param eventStartTime time that an event starts
	 * @param eventEndTime time that an event ends
	 * @param eventDetails details of an event
	 * @throws IllegalArgumentException with message "You have already created an event called [event title]." if new Event is a duplicate of an existing Event in schedule
	 */
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime,
			String eventDetails) {
		Event e = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);
		// check that event doesn't already exist in schedule
		for (int i = 0; i < schedule.size(); i++) {
			if (e.isDuplicate(schedule.get(i))) {
				throw new IllegalArgumentException("You have already created an event called " + eventTitle);
			}
		}
		
		// add event to schedule
		schedule.add(e);
	}

}
