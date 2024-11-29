
// Les types de collections principales :
val list = listOf("Pomme", "Banane", "Cerise")
val mutableList = mutableListOf(1,2,3)
val set = setOf(1,2,3,4,4)

fun main (){
 println(list)
 println(mutableList)
println(set)
}

// Filtrer et transformer les données :
val nombres = listOf(1, 2, 3, 4, 5)
val pairs = nombres.filter { it % 2 == 0 }  // [2, 4]
val carrés = nombres.map { it * it }    

fun main (){
println(pairs)    
println(carrés)

// nombres.forEach{println(it)}
}

// 🧩 Les génériques :
// Créer une fonction générique :
fun <T> afficherElement(element: T) {
    println(element)
}

afficherElement(42)         // Fonctionne avec un Int
afficherElement("Bonjour")  // Fonctionne avec un String

// Classe générique :
class Boite<T>(val contenu: T) {
    fun afficherContenu() {
        println("Contenu : $contenu")
    }
}

fun main (){
val boiteInt = Boite(123)
val boiteString = Boite("Hello")
boiteInt.afficherContenu()     // Contenu : 123
boiteString.afficherContenu()  // Contenu : Hello
}

// 🚀 Extensions :
// Les extensions ajoutent des fonctionnalités aux classes existantes sans modifier leur code source.

// Exemple d’extension pour String :
fun String.inverser(): String {
    return this.reversed()
}

println("Kotlin".inverser())  // Résultat : "niltok"

// 🔧 Fonctions d’ordre supérieur :
fun appliquerOperation(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

// Utilisation
val somme = appliquerOperation(5, 3) { x, y -> x + y }   // 8
val produit = appliquerOperation(5, 3) { x, y -> x * y } // 15

fun main (){
println(somme)  
println(produit)

}

// 🛠️ Exercices pratiques :
// Manipulation des collections :
// Créez une liste de nombres et filtrez ceux qui sont supérieurs à 10.

val nombre= listOf(11,2,33,4)
val superieurs = nombre.filter{it>10}

fun main (){
println(superieurs)  
// println(produit)
}

// Transformez une liste de chaînes en majuscules.
val nombre= ("numbers")

fun main (){
println(nombre.toUpperCase())  
}

// Fonctions génériques :
// Créez une fonction générique qui retourne le dernier élément d’une liste.
fun <T> lastElement(list: List<T>): T?{
    return list.lastOrNull()
} 

fun main() {
   val mylist = listOf("soli", "code", "2024")
   val lastElement = lastElement(mylist)
   println(lastElement)
}