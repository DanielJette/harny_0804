package com.neofinancial.neo.swapi.ui.person.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.neofinancial.neo.swapi.core.HtmlParser
import com.neofinancial.neo.swapi.core.SwapiClient
import com.neofinancial.neo.swapi.core.capitalize
import com.neofinancial.neo.swapi.data.Person
import com.neofinancial.neo.swapi.data.Species
import com.neofinancial.neo.swapi.databinding.FragmentPersonDetailsBinding
import kotlinx.coroutines.launch

class PersonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPersonDetailsBinding
    private lateinit var personId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            personId = it.getString(ARG_ID) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            SwapiClient.getPerson(personId)?.let { person ->
                activity?.title = person.name
                binding.viewRoot.render(person.toViewState())
            }
        }
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(personId: String) =
            PersonDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, personId)
                }
            }
    }

    private fun Person.toViewState(): PersonDetailsViewState {

        return PersonDetailsViewState(
            name = name ?: "",
            avatar = fetchAvatar(name ?: ""),
            birthYear = if (birthYear == "unknown") null else birthYear,
            hairColor = hairColor?.capitalize(),
            skinColor = skinColor?.capitalize(),
            eyeColor = eyeColor?.capitalize(),
            height = height?.let { "${height}cm" },
            weight = weight?.let { "${weight}kg" },
            species = species?.toViewState()
        )
    }

    private fun Species?.toViewState(): PersonDetailsSpeciesViewState {
        return PersonDetailsSpeciesViewState(
            name = this?.name?.capitalize(),
            language = this?.language?.capitalize(),
            classification = this?.classification?.capitalize()
        )
    }

    private fun fetchAvatar(name: String): String {
        val meta = HtmlParser()
            .get("https://starwars.fandom.com/wiki/${name.replace(' ', '_')}")
            .getMetaProperties()
        return meta["og:image"] ?: ""
    }
}
