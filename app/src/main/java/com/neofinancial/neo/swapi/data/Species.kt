package com.neofinancial.neo.swapi.data

data class Species(
    val id: String? = null,
    val name: String? = null,
    val language: String? = null,
    val classification: String? = null
)

fun GetPersonQuery.Species?.toSpecies() = Species(
    id = this?.id,
    name = this?.name,
    language = this?.language,
    classification = this?.classification
)

fun GetPeopleQuery.Species.toSpecies() = Species(
    name = this.name
)

val Species.isDroid: Boolean
    get() {
        return name.contentEquals("Droid", ignoreCase = true)
    }
