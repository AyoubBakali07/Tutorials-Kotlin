fun test() {
    println("Happy Birthday, Rover!")
    println("You are now 5 years old!")
}

fun birthdayGreetin(name: String = "Rover", age: Int = 5): String {
    return "Happy Birthday, $name! You are now $age years old!"
}

fun main() {
    // Call test function
    test()

    // Call birthdayGreeting function with default arguments
    println(birthdayGreetin())

    // Call birthdayGreeting function with custom arguments
    println(birthdayGreetin("Ayoub", 25))

    // Call gift function
    gift()
}

fun gift() {
    println("You have 2 unread messages")
    println("You have 3 unread messages")
}
