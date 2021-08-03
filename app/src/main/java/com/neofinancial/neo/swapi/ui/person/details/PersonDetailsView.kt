package com.neofinancial.neo.swapi.ui.person.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.neofinancial.neo.swapi.databinding.ViewPersonDetailsBinding
import com.squareup.picasso.Picasso

class PersonDetailsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private var binding: ViewPersonDetailsBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = ViewPersonDetailsBinding.inflate(layoutInflater, this, true)
    }

    fun render(state: PersonDetailsViewState) {
        if (state.avatar.isNotEmpty()) {
            Picasso.get().load(state.avatar).into(binding.profileImage)
        }

        renderInfo(state)
        renderSpecies(state?.species)
    }

    private fun renderInfo(state: PersonDetailsViewState) {
        binding.birthYearRow.isVisible = state.birthYear != null
        binding.birthYear.text = state.birthYear

        binding.skinColorRow.isVisible = state.skinColor != null
        binding.skinColor.text = state.skinColor

        binding.hairColorRow.isVisible = state.hairColor != null
        binding.hairColor.text = state.hairColor

        binding.eyeColorRow.isVisible = state.eyeColor != null
        binding.eyeColor.text = state.eyeColor

        binding.heightRow.isVisible = state.height != null
        binding.height.text = state.height

        binding.weightRow.isVisible = state.weight != null
        binding.weight.text = state.weight
    }

    private fun renderSpecies(species: PersonDetailsSpeciesViewState?) {
        binding.speciesCard.isGone =
            (species == null) || (species.name == null && species.language == null && species.classification == null)

        binding.speciesNameRow.isVisible = species?.name != null
        binding.speciesName.text = species?.name

        binding.languageRow.isVisible = species?.language != null
        binding.language.text = species?.language

        binding.classificationRow.isVisible = species?.classification != null
        binding.classification.text = species?.classification
    }
}
