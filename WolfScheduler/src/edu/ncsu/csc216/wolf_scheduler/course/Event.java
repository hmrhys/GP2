/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

//import edu.ncsu.csc216.wolf_scheduler.course.Activity;

/**
 * Manages and stores the information of an Event object.
 * An Event object knows its title, meetingDays, startTime, endTime, and eventDetails.
 * 
 * Inherits certain information from parent Activity such as meetingDays, startTime, and endTime. 
 * 
 * @author hmreese2
 *
 */
public class Event extends Activity {
	
	/** Represents the details of an event object */
	private String eventDetails;

	/**
	 * Constructs an event object.
	 * An Event knows its title, meetingDays, startTime, endTime, and eventDetails
	 * @param title title of the event
	 * @param meetingDays days that an event meets
	 * @param startTime time that an event starts
	 * @param endTime time that an event ends
	 * @param eventDetails the details of an event
	 * @throws IllegalArgumentException if any of the values are invalid
	 */
    public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
        super(title, meetingDays, startTime, endTime);
        setEventDetails(eventDetails);
    }
	
	/**
	 * Gets the details of an event
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Error checks and sets the details of an event
	 * @param eventDetails the eventDetails to set
	 * @throws IllegalArgumentException with message "Invalid event details." if eventDetails is null
	 */
	public void setEventDetails(String eventDetails) {
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		
		this.eventDetails = eventDetails;
	}
	
	/**
	 * Error checks event meeting days and uses call to super to set the appropriate values for the respective fields.
	 * Meeting days for Event can be any day of week ("MTWHFSU"), if anything else, including "A", the meetind day is invalid.
	 * @param meetingDays the days that an event meets.
	 * @param startTime the time that an event starts.
	 * @param endTime the time that an event ends.
	 * @throws IllegalArgumentException with message "Invalid meeting days and times." if days are null, empty, or invalid chars; 
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) { 
		// check null or empty meeting days
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		// variables that keep count of each week day Letter in meetingDays
		int m = 0;
		int t = 0;
		int w = 0;
		int h = 0;
		int f = 0;
		int s = 0;
		int u = 0;
		
		// iterate through meetingDays 
		for (int i = 0; i < meetingDays.length(); i++) {
			char day = meetingDays.charAt(i);
			
			// count up the amount of times a day was listed
			if (day == 'M') {
				m++;
			} else if (day == 'T') {
				t++;
			} else if (day == 'W') {
				w++;
			} else if (day == 'H') {
				h++;
			} else if (day == 'F') {
				f++;
			} else if (day == 'S') {
				s++;
			} else if (day == 'U') {
				u++;
		    } else { // if any other letters are listed, throw IAE
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		
		// check that the counts are valid (no day should be listed more than once)
		if (m > 1 || t > 1 || w > 1 || h > 1 || f > 1 || s > 1 || u > 1) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		
	}

	/**
	 * Gets and displays a limited version of the details of an activity as an array.
	 * Used to populate rows of course catalog and student schedule.
	 * @return 1D String array of length 4 containing two empty strings followed by title, and meeting string
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] arr = new String[4];
		arr[0] = "";
		arr[1] = "";
		arr[2] = getTitle();
		arr[3] = getMeetingString();
		
		return arr;
	}

	/**
	 * Gets and displays a full-length version of the details of an activity as an array.
	 * Used to display user's final schedule.
	 * @return 1D String array of length 7 containing two empty strings followed by title, empty string, empty string, meeting string, and eventDetails
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] arr = new String[7];
		arr[0] = "";
		arr[1] = "";
		arr[2] = getTitle();
		arr[3] = "";
		arr[4] = "";
		arr[5] = getMeetingString();
		arr[6] = eventDetails;
		
		return arr;
	}

	/**
	 * Produces a comma-separated string representation of the information stored in the Event object.
	 * @return string representation of 
	 */
	@Override
	public String toString() {
		return getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime() + "," + eventDetails;
	}

	/**
	 * Determines if an activity object is a duplicate to another event object (if they have the same title)
	 * @param activity the activity that is being check for duplicates
	 * @return true if activity shares a title with an already existing event, false if not
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		
		if (activity instanceof Event) {
			
			// cast activity to event
			Event other = (Event) activity;
			return other.getTitle().equals(getTitle());
			
		} else {
			return false;
		}
		
	}

}
