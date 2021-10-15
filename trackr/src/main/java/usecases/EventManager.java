package usecases;

import database.DatabaseAccessInterface;
import entities.Anniversary;
import entities.Birthday;
import entities.Event;
import entities.Person;

import java.time.LocalDate;
import java.util.List;

/**
 * EventManager is a use case class that manages the creation and deletion of events
 */
public class EventManager {

    private DatabaseAccessInterface dataAccessor;

    public EventManager(DatabaseAccessInterface dataAccessorObj) {
        this.dataAccessor = dataAccessorObj;
    }

    public List<Event> getAllEvents() {
        return dataAccessor.getEventData();
    }

    /**
     * Add an event to the database
     * @param person - the key we are adding to the hashmap
     * @param eventTyoe - a string representing the type of event (either Birthday or Anniversary)
     * @param date - the date of the event
     * @param reminderDeadline - the date to innitiate a reminder
     * @return true if the key is not already in the hashmap and false otherwise
     */
    public boolean addEvent(Person person, String eventTyoe, LocalDate date, LocalDate reminderDeadline) {
        Event event;
        if (eventTyoe.equals("Birthday")) {
            event = new Birthday(person, date, reminderDeadline);
        } else {
            event = new Anniversary(person, date, reminderDeadline);
        }
        dataAccessor.addEvent(event);
        return true;
    }

    /**
     * Remove an event from the event hash map
     * @param firstName - the first name of the person whose events we are removing
     * @param lastName - the last name of the person whose events we are removing
     * @return false if the key is not in the hashmap and true if we
     * succesfully remove the key and corresponding value
     */
    public boolean removeEvent(String firstName, String lastName) {
        List<Event> whichEventsToRemove = dataAccessor.findEvent(firstName, lastName);
        for (Event e : whichEventsToRemove) {
            dataAccessor.removeEvent(e);
        }
        return false;
    }
}
