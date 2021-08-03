package com.neofinancial.neo.swapi.ui.person.details

data class PersonDetailsSpeciesViewState(
    val name: String?,
    val language: String?,
    val classification: String?,
)

data class PersonDetailsViewState(
    val name: String,
    val avatar: String,
    val birthYear: String?,
    val hairColor: String?,
    val skinColor: String?,
    val eyeColor: String?,
    val height: String?,
    val weight: String?,
    val species: PersonDetailsSpeciesViewState?
)
