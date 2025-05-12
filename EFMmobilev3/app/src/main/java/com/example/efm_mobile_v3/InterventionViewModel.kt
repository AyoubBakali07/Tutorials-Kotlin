package com.example.efm_mobile_v3

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.efm_mobile_v3.models.Intervention

class InterventionViewModel : ViewModel() {
    private val _interventions = mutableStateListOf<Intervention>()
    val interventions: List<Intervention> = _interventions

    fun add(intervention: Intervention) {
        _interventions.add(intervention)
    }

    fun update(updated: Intervention) {
        val index = _interventions.indexOfFirst { it.id == updated.id }
        if (index >= 0) _interventions[index] = updated
    }

    fun remove(intervention: Intervention) {
        _interventions.remove(intervention)
    }
}
