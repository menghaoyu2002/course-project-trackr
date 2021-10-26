package interface_adapters

import database.DatabaseAccessFactory

/**
 * interface_adapters.BirthdayPresenter is an Interface Adapter class that receives inputs, decides what to do
 * with them, and sends them to the Use Case classes to be dealt with. It also receives
 * outputs from the Use Case classes and sends them to the Frameworks and Drivers to be
 * outputted.
 */
class BirthdayPresenter {
    private val em: EventInOut = EventManager(DatabaseAccessFactory.createDatabaseAccessInterface())

    /**
     * Interacts with user to get input and send outputs about depending on what the user wants
     * to do.
     *
     * @param out An instance of interface_adapters.OutputBoundary that will output the information from the Use Case
     * classes to the user.
     */
    fun run(`in`: InputBoundary, out: OutputBoundary) {
        var input: String = `in`.getInput()
        while (!input.equals("exit") && !input.equals("quit")) {
            val inputArray: Array<String> = input.split(" ")
            val command = inputArray[0]
            val args: Array<String> = Arrays.copyOfRange(inputArray, 1, inputArray.size)
            executeCommand(command, args, out)
            input = `in`.getInput()
        }
    }

    /**
     * Decides what (Use Case) to do based on the given user input.
     * If the input is not in a valid format then an error message
     * is outputted to the user.
     *
     * @param command The user given command.
     * @param args    The arguments/parameters for the given command.
     * @param out     The instance interface_adapters.OutputBoundary that output what is given.
     */
    fun executeCommand(command: String?, args: Array<String>, out: OutputBoundary) {
        when (command) {
            "add" -> {
                val eventType: EventTypes = getEventType(args[0])
                val name = getFirstLastName(args[2])
                val firstName = name[0]
                val lastName = name[1]
                val dateString: Array<String> = args[1].split("/")
                val date: LocalDate = LocalDate.of(
                    Integer.parseInt(dateString[0]),
                    Integer.parseInt(dateString[1]),
                    Integer.parseInt(dateString[2])
                )
                val remindDeadline: LocalDate = date.minusDays(Integer.parseInt(args[3]))
                val success: Boolean = em.add(eventType, firstName, lastName, date, remindDeadline)
                var successString = "unsuccessfully"
                if (success) {
                    successString = "successfully"
                }
                val outString = "You have " + successString + " added a " + args[0] +
                        " event on " + args[1] + " for " + firstName + " " + lastName + ". You will be reminded " +
                        args[3] + " days beforehand."
                out.sendOutput(outString)
            }
            "remove" -> {
                val eventType: EventTypes = getEventType(args[0])
                val name = getFirstLastName(args[1])
                val firstName = name[0]
                val lastName = name[1]
                val success: Boolean = em.remove(eventType, firstName, lastName)
                var successString = "unsuccessfully"
                if (success) {
                    successString = "successfully"
                }
                val outString = "You have " + successString + " removed a " + args[0] +
                        " event for " + firstName + " " + lastName + "."
                out.sendOutput(outString)
            }
            "view" -> {
                val eventType: EventTypes = getEventType(args[0])
                val name = getFirstLastName(args[1])
                val firstName = name[0]
                val lastName = name[1]
                val info: EventOutputData = em.view(eventType, firstName, lastName)
                if (info.getFirstName() == null) {
                    out.sendOutput("This event doesn't exist.")
                    break
                }
                val dateString: String = info.getDate().toString().replace('-', '/')
                val days = info.getRemindDeadline().until(info.getDate(), ChronoUnit.DAYS) as Int
                val outString = "You have a " + args[0] +
                        " event on " + dateString + " for " + firstName + " " + lastName + ". You will be reminded " +
                        days + " days beforehand."
                out.sendOutput(outString)
            }
            "help" -> {
                out.sendOutput("help\t\tshows help page\nquit\t\tquit Trackr")
                out.sendOutput("add <event_type> <yyyy/mm/dd> <firstname_lastname> <reminder_interval>")
                out.sendOutput("example: add Birthday 2020/04/05 Jeffry_Bezos 30")
                out.sendOutput("remove <event_type> <firstname_lastname>")
                out.sendOutput("view <event_type> <firstname_lastname>")
            }
            else -> {
                out.sendOutput("Not a valid command")
            }
        }
    }

    /**
     * Returns the eventType from the given input.
     *
     * @param input The given input from the user representing the event type.
     * @return      The event type in a form the use case classes can use.
     */
    private fun getEventType(input: String): EventTypes {
        return if (input.equalsIgnoreCase("BIRTHDAY")) {
            EventOutputData.EventTypes.BIRTHDAY
        } else {
            EventOutputData.EventTypes.ANNIVERSARY
        }
    }

    /**
     * Returns the first and last names of an individual split between their fist and last names
     * in the form [<firstName>,<lastName>].
     *
     * @param input The given input from the user representing the name.
     * @return      The first and last names.
    </lastName></firstName> */
    private fun getFirstLastName(input: String): Array<String> {
        val lastName: String
        val name: Array<String> = input.split("_")
        val firstName = name[0]
        lastName = if (name.size == 2) {
            name[1]
        } else {
            ""
        }
        return arrayOf(firstName, lastName)
    }
}