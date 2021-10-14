package entities;

import java.util.Date;

public abstract class Event {
    private Date date;
    private Date reminderDeadline;
    private Person person;

    /**
     * Create a new event with a reminder time.
     * @param person the person associated with this event
     * @param date the date this event will take place
     * @param reminderDeadline when this event should be reminded to the user.
     */
    public Event(Person person, Date date, Date reminderDeadline) {
        this.date = date;
        this.reminderDeadline = reminderDeadline;
        this.person = person;
    }

    /**
     * Create an event without a reminder time.
     * @param person the person associated with this event
     * @param date the date this event will take place
     */
    public Event(Person person, Date date) {
        this(person, date, null);
    }

    public Person getPerson() {
        return person;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Check whether the input date matches a reminderDeadline of this object
     * @param date The date to test for a deadline
     * @return A boolean value if the input matches a reminder deadline
     */
    public boolean isReminderDeadline(Date date) {
        return date.equals(this.reminderDeadline);
    }
}
