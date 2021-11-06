package com.trackr.trackr_app.entities

import java.util.HashSet

/**
 * A Person who is the target for an Event.
 * The Person class is responsible for storing the first and last name of the person,
 * along with a description and tags associated with this person.
 */
class Person(
    /**
     * Set the first name of this Person to firstName
     * @param firstName the new firstName of this Person
     */
    var firstName: String,
    /**
     * Set the last name of this Person to lastName
     * @param lastName the new lastName of this Person
     */
    var lastName: String = "",
    /**
     * Set the description of this Person to description
     * @param description the new description of this Person
     */
    var description: String = "",
    private val tags: HashSet<String> = HashSet()
) {
    /**
     * Return the lastName of this Person
     * @return the lastName of this Person
     */
    /**
     * Return a description of this person
     * @return the description of this person
     */
    /**
     * Return the tags that this Person has
     * @return the tags associated with this Person
     */
    fun getTags(): Set<String> {
        return tags
    }

    /**
     * Return whether the person has the given tag
     * @param tag the tag to test for
     * @return a boolean representing whether the person has the given tag or not
     */
    fun hasTag(tag: String): Boolean {
        return tags.contains(tag)
    }

    /**
     * Add a tag to this person.
     * Return true if the tag was successfully added and false if this person already has this tag.
     * @param tag the tag to add to this person
     * @return false if the person already has this tag, true otherwise
     */
    fun addTag(tag: String): Boolean {
        return tags.add(tag)
    }

    /**
     * Remove the tag from this person.
     * Return true if successfully removed, return false if this person doesn't have that tag
     * @param tag the tag to remove from this person
     * @return false if the Person does not have the tag
     * otherwise return remove the tag and return true
     */
    fun removeTag(tag: String): Boolean {
        return tags.remove(tag)
    }
}