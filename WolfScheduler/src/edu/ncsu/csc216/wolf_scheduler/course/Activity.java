package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Activity class is responsible for managing and storing the information for an Activity object.
 * An Activity object knows its title, meetingDays, startTime, and endTime.
 * Activity is the super/parent class of Course and Event. Therefore, Activity works with the similar data shared by those two courses.
 * Activity handles general error checking and lets Course and Event deal with the more unique requirements for the other classes.
 * 
 * @author hmreese2
 *
 */
public abstract class Activity {

	/** Represents the upper hour bound for a course's meeting time */
	private static final int UPPER_HOUR = 24;
	/** Represents the upper minute bound for a course's meeting time */
	private static final int UPPER_MINUTE = 60;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	/**
	 * Constructs an activity (applicable as either an "event" or "course activity object.
	 * Activity knows its title, meeting days, and meeting start and end times.
	 * Constructor error checks and sets the values for the appropriate fields.
	 * @param title title of the activity
	 * @param meetingDays days that the activity meets
	 * @param startTime time that the activity starts
	 * @param endTime time that the activity ends
	 * @throws IllegalArgumentException if any values are invalid
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Gets title of course.
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title of course.
	 * @param title the title to set.
	 * @throws IllegalArgumentException with message "Invalid title." if title is null or empty string.
	 */
	public void setTitle(String title) {
		// check for null or empty string value.
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		// if valid, set field.
		this.title = title;
	}

	/**
	 * Gets meeting days for course.
	 * @return the meetingDays.
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Gets start time for course.
	 * @return the startTime.
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Gets end time for course.
	 * @return the endTime.
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Error checks activity meeting days and times and sets the appropriate value for the respective field.
	 * Unique cases for the Activity type (Course or Event) are error checked and handled in their respective class (Course or Event).
	 * @param meetingDays the days that a activity meets.
	 * @param startTime the time that a activity starts.
	 * @param endTime the time that a activity ends.
	 * @throws IllegalArgumentException with message "Invalid meeting days and times." if days are null, empty, or invalid chars, or if times are invalid 
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// check null or empty meeting days
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
			
		// check that times are valid (startTime should always be LESS that endTime)
		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		// break apart and compute start time and end time into hours and minutes (standard time)
		int startHour = startTime / 100;
		int startMin = startTime % 100;
		int endHour = endTime / 100;
		int endMin = endTime % 100;
		// check valid times
		if (startHour < 0 || startHour >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else if (startMin < 0 || startMin >= UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else if (endHour < 0 || endHour >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else if (endMin < 0 || endMin >= UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} 
		
		// if all values are valid, set fields.
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
		//}
	}

	/**
	 * Provides course meeting information (days and time) as a string, where the time is displayed as standard time.
	 * If meeting day is Arranged then return "Arranged".
	 * If meeting day us NOT Arranged then return the meeting days followed by start time (standard), a dash, then end time (standard).
	 * @return string representation of the meeting information for a course (where time is in standard time).
	 */
	public String getMeetingString() {
		// if meeting day is Arranged then string is simply "Arranged"
		if ("A".equals(meetingDays)) {
			return "Arranged";
		}
		
		// convert times
		String starting = getTimeString(startTime);
		String ending = getTimeString(endTime);
		
		return meetingDays + " " + starting + "-" + ending;
	}

	/**
	 * Generates a unique hashCode for an object used to help compare the object's equality to another object.
	 * @return hashCode integer that represents the comparable fields of an object, used to compare to another object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares one object to another object on all fields.
	 * @param obj the object that is being compared
	 * @return true if the objects are found to be completely equal, otherwise return false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * Private helper method for getMeetingString() that converts the given starting/ending course time from 24hr time to standard time.
	 * @param time the time that is being converted from 24hr time to standard time.
	 * @return converted time as a string of standard time.
	 */
	private String getTimeString(int time) {
		String timeString;
		// break into hours and minutes
		int hour = time / 100;
		int min = time % 100;
		
		if (hour >= 12) { // PM time
			// check if hour is 12, otherwise update hour to standard time
			if (hour > 12) {
				hour = hour - 12;
			}
			
			// check if minutes need a leading 0 (are less than 10)
			if (min < 10) {
				timeString = hour + ":0" + min + "PM";
			} else {
				timeString = hour + ":" + min + "PM";
			}
			
		} else { // AM time
			
			// check if hour is 0, if it is update it to standard time
			if (hour == 0) {
				hour = 12;
			}
			
			// check if minutes need a leading 0 (are less than 10)
			if (min < 10) {
				timeString = hour + ":0" + min + "AM";
			} else {
				timeString = hour + ":" + min + "AM";
			}
		}
		return timeString;
	}
	
	/** 
	 * Gets and displays a limited version of the details of an activity as an array.
	 * Used to populate rows of course catalog and student schedule.
	 * @return 1D String array of length 4 containing Course name, section, title, and meeting string
	 */
	public abstract String[] getShortDisplayArray();
	
	/** 
	 * Gets and displays a full-length version of the details of an activity as an array.
	 * Used to display user's final schedule.
	 * @return 1D String array of length 7 containing Course name, section, title, credits, instructorId, meeting string, empty string (for Course bc it's an Event-exclusive field)
	 */ 
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Checks if a Course or Event activity are duplicates of one another or each other.
	 * @param activity the activity that is being checked for duplicates
	 * @return true if the activity is a duplicate of another activity object, otherwise false.
	 */
	public abstract boolean isDuplicate(Activity activity);

}