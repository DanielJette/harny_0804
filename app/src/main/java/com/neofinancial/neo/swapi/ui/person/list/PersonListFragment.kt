package com.neofinancial.neo.swapi.ui.person.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neofinancial.neo.swapi.R
import com.neofinancial.neo.swapi.core.SwapiClient
import com.neofinancial.neo.swapi.data.Person
import com.neofinancial.neo.swapi.databinding.FragmentPersonListBinding
import com.neofinancial.neo.swapi.ui.person.details.PersonDetailsFragment
import kotlinx.coroutines.launch

class PersonListFragment : Fragment() {

    private lateinit var binding: FragmentPersonListBinding
    private var adapter: PersonListRecyclerViewAdapter? = null

    private fun updateEmptyState() {
        val hasItems = binding.list.adapter?.itemCount ?: 0 > 0
        binding.list.isGone = !hasItems
        binding.emptyView.isGone = hasItems
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val people = SwapiClient.getPeople()
            adapter?.setValues(people)
        }
        activity?.title = getString(R.string.app_name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        adapter = PersonListRecyclerViewAdapter(::onItemClick).apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    updateEmptyState()
                }
            })
        }

        binding = FragmentPersonListBinding.inflate(inflater, container, false)
        binding.list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PersonListFragment.adapter
            updateEmptyState()
        }
        return binding.root
    }

    private fun onItemClick(item: Person) {
        val personFragment = PersonDetailsFragment.newInstance(item.id)

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment, personFragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}
