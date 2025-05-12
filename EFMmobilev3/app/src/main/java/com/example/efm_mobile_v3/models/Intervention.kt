package com.example.efm_mobile_v3.models

import java.util.UUID
data class Intervention(
    val id: UUID = UUID.randomUUID(),
    val nom: String,
    val statut: String,
    val priorite: String
)

