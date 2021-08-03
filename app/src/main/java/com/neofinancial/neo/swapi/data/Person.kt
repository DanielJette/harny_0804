package com.neofinancial.neo.swapi.data

import GetPeopleQuery
import GetPersonQuery

data class Person(
    val id: String,
    val name: String? = null,
    val birthYear: String? = null,
    val hairColor: String? = null,
    val skinColor: String? = null,
    val eyeColor: String? = null,
    val height: Int? = null,
    val weight: Double? = null,
    val species: Species? = null
)

fun GetPersonQuery.Person.toPerson() = Person(
    id = this.id,
    name = this.name,
    birthYear = this.birthYear,
    hairColor = this.hairColor,
    skinColor = this.skinColor,
    eyeColor = this.eyeColor,
    height = this.height,
    weight = this.mass,
    species = this.species.toSpecies()
)

fun GetPeopleQuery.Person.toPerson() = Person(
    id = this.id,
    name = this.name,
    species = this.species?.toSpecies()
)
