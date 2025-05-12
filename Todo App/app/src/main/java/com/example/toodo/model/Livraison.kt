package com.example.toodo.model

/**
 * Statut possible d'une Livraison
 */
enum class Status(val label: String) {
    EN_COURS("En cours"),
    TERMINE("Terminé"),
    ANNULE("Annulé")
}

/**
 * Priorité possible d'une Livraison
 */
enum class Priority(val label: String) {
    HAUTE("Haute"),
    MOYENNE("Moyenne"),
    BASSE("Basse")
}

/**
 * Entité Livraison
 */
data class Livraison(
        val id: Int = 0,
        val nom: String,
        val statut: Status,
        val priorite: Priority
)
