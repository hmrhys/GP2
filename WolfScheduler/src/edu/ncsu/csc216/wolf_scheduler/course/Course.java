package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Constructs a course object that will be used store a course's individual object information.
 * 
 * A Course object knows its name, title, section, credits, instructorId, meetingDays, and meeting startTime and endTime. There are certain requirements for each field that make it either valid or invalid,
 * and meeting days are either Arranged or a list of meeting days represented by a series of non-repeating days of the week.
 * 
 * Inherits certain information, such as meetingDays, startTime, and endTime, from Activity. Information is handled in Activity for value setting and general error-checking.
 * 
 * @author hmreese2
 *
 */
public class Course extends Activity {
	
 /** Represents minimum length a course name can have. */
 private static final int MIN_NAME_LENGTH = 5;
	/** Represents maximum length a course name can have */
	private static final int MAX_NAME_LENGTH = 8;
	/** Represents minimum letter count a course can have */
	private static final int MIN_LETTER_COUNT = 1;
	/** Represents maximum letter count a course can have */
	private static final int MAX_LETTER_COUNT = 4;
	/** Represents digit count required for a course section */
	private static final int DIGIT_COUNT = 3;
	/** Represents required section length for a course section */
	private static final int SECTION_LENGTH = 3;
	/** Represents maximum credit hours a course can have */
	private static final int MAX_CREDITS = 5;
	/** Represents minimum credit hours a course can have */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	
	/**
	 * Constructs course object using values of all fields.
	 * Calls to Super Abstract class Activity to error check and set the title, meetingDays, startTime, and EndTime fields.
	 * @param name name of course.
	 * @param title title of course.
	 * @param section section of course.
	 * @param credits credit hours for course.
	 * @param instructorId instructor's unity id for course.
	 * @param meetingDays meeting days that a course meets (represented as series of chars).
	 * @param startTime starting time for a course.
	 * @param endTime ending time for a course.
	 * @throws IllegalArgumentException if any parameters are invalid.
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
	    setCredits(credits);
	    setInstructorId(instructorId);
	}

	/**
	 * Creates course with only name, title, section, credits, instructorId, and meetingDays for arranged courses.
	 * @param name name of course.
	 * @param title title of course.
	 * @param section section of course.
	 * @param credits credit hours for course.
	 * @param instructorId instructor's unity id for course.
	 * @param meetingDays meeting days for a course (represented as series of chars).
	 * @throws IllegalArgumentException if any of the parameters are invalid.
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Gets name of course
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets name of course
	 * @param name the name to set
	 * @throws IllegalArgumentException with message "Invalid course name." if name parameter is null, empty string, or improperly formatted
	 */
	private void setName(String name) {
		// check for null or empty string names
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		// check length
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		// check properly formatted name pattern (L[LLL] NNN), i.e. correct number of letters/digits
		int letter = 0;
		int digit = 0;
		boolean hasSpace = false;
		for (int i = 0; i < name.length(); i++) {
			if (!hasSpace) {
				if (Character.isLetter(name.charAt(i))) {
					letter++;
				} else if (name.charAt(i) == ' ') {
					hasSpace = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else if (hasSpace) {
				if (Character.isDigit(name.charAt(i))) {
					digit++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}
		
		// check number of letters
		if (letter < MIN_LETTER_COUNT || letter > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		// check number of digits
		if (digit != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		// if valid, set field value
		this.name = name;
	}
	
	/**
	 * Gets section of course.
	 * @return the section.
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets section of course.
	 * @param section the section to set.
	 * @throws IllegalArgumentException with message "Invalid section." if section is null or not 3 characters.
	 */
	public void setSection(String section) {
		// check for null or invalid length
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		// check if any characters are not digits
		for (int i = 0; i < section.length(); i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		
		// if valid set value.
		this.section = section;
	}
	
	/**
	 * Gets credits for course.
	 * @return the credits.
	 */
	public int getCredits() {
		return credits;
	}
	
	/**
	 * Sets credits for course.
	 * @param credits the credits to set.
	 * @throws IllegalArgumentException with message "Invalid credits." if credits value is out of bounds.
	 */
	public void setCredits(int credits) {
		// check that value is within bounds.
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		
		// if value is valid set field.
		this.credits = credits;
	}
	
	/**
	 * Gets instructor id for course.
	 * @return the instructorId.
	 */
	public String getInstructorId() {
		return instructorId;
	}
	
	/**
	 * Sets instructor id for course.
	 * @param instructorId the instructorId to set.
	 * @throws IllegalArgumentException with message "Invalid instructor id." if parameter is null or empty string.
	 */
	public void setInstructorId(String instructorId) {
		// check null or empty string.
		if (instructorId == null || instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		
		// if valid set value.
		this.instructorId = instructorId;
	}
	
	/**
	 * Error checks course meeting days and times and sets the appropriate value for the respective field.
	 * If the meeting days for Course are Arranged ("A") then start and end times are set to 0.
	 * If the meeting days for Course are NOT ARRANGED ("MTWHF"), then start and times must exist and must be valid.
	 * @param meetingDays the days that a course meets.
	 * @param startTime the time that a course starts.
	 * @param endTime the time that a course ends.
	 * @throws IllegalArgumentException with message "Invalid meeting days and times." if days are null, empty, or invalid chars; 
	 * If an arranged class has non-zero start/end times; If start/end time are incorrect times; If end time is less than start time.
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// check null or empty meeting days
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		// check meeting days and times values
		if ("A".equals(meetingDays)) { // course is Arranged
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			// call super to set fields
			super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		} else { // course is NOT Arranged
			// variables that keep count of each week day Letter in meetingDays
			int m = 0;
			int t = 0;
			int w = 0;
			int h = 0;
			int f = 0;
			
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
				} else { // if any other letters are listed, throw IAE
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			
			// check that the counts are valid (no day should be listed more than once)
			if (m > 1 || t > 1 || w > 1 || h > 1 || f > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		}
	}
	
	/**
	 * Generates a code for an object that is used to represent the equality of that object compared to another object.
	 * @return integer representation of the hashCode generated of an object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares one object to another object on all fields present within that object.
	 * @param obj the object being compared
	 * @return true if the objects are exactly equal to each other or false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime(); 
	}

	/**
	 * Gets and displays a limited version of the details of an activity as an array.
	 * Used to populate rows of course catalog and student schedule.
	 * @return 1D String array of length 4 containing Course name, section, title, and meeting string
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] arr = new String[4];
		arr[0] = name;
		arr[1] = section;
		arr[2] = getTitle();
		arr[3] = getMeetingString();
		
		return arr;
	}

	/**
	 * Gets and displays a full-length version of the details of an activity as an array.
	 * Used to display user's final schedule.
	 * @return 1D String array of length 7 containing Course name, section, title, credits, instructorId, meeting string, empty string (for Course bc it's an Event-exclusive field)
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] arr = new String[7];
		arr[0] = name;
		arr[1] = section;
		arr[2] = getTitle();
		arr[3] = credits + "";
		arr[4] = instructorId;
		arr[5] = getMeetingString();
		arr[6] = "";
		
		return arr;
	}

	/**
	 * Checks if two Course activity objects are duplicates of each other (if they have the same name)
	 * @param activity the activity that is being compared for duplicates
	 * @return true if two course objects share the same name, false if not
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		// check if parameter is instance of Course and cast to check Course's fields
		if (activity instanceof Course) {
			Course other = (Course) activity;
			
			return other.getName().equals(name);
					
		} else {
			return false;
		}
		
	}
}
