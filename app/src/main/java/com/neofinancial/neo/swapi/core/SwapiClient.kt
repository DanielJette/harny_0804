package com.neofinancial.neo.swapi.core

import GetPeopleQuery
import GetPersonQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation.Data
import com.apollographql.apollo.api.Operation.Variables
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.neofinancial.neo.swapi.data.Person
import com.neofinancial.neo.swapi.data.toPerson

object SwapiClient {

    private val apolloClient: ApolloClient by lazy {
        ApolloClient.builder()
            .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index")
            .build()
    }

    private suspend fun <D : Data, T, V : Variables> query(query: Query<D, T, V>): T? {
        val response = try {
            apolloClient.query(query).await()
        } catch (e: ApolloException) {
            // handle protocol errors
            return null
        }
        if (response.hasErrors()) {
            // handle application errors
            return null
        }
        return response.data
    }


    suspend fun getPerson(id: String): Person? {
        val person = query(GetPersonQuery(id = id))?.person
        return person?.toPerson()
    }

    suspend fun getPeople(): List<Person> {
        val people = query(GetPeopleQuery())?.allPeople?.people
        return people?.filterNotNull()?.map {
            it.toPerson()
        }?.sortedBy { it.name } ?: emptyList()
    }

}
