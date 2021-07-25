package com.ufonaut.test.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.ufonaut.test.databinding.FragmentListBinding
import com.ufonaut.test.ui.adapter.PlacesListAdapter
import com.ufonaut.test.utils.DialogUtils
import com.ufonaut.test.utils.ItemClickSupport
import com.ufonaut.test.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment: Fragment(), ItemClickSupport.OnItemClickListener {
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        ItemClickSupport.addTo(view as RecyclerView).setOnItemClickListener(this)
        viewModel.observePlaces().observe(viewLifecycleOwner, Observer {
            (view as RecyclerView).layoutManager = LinearLayoutManager(context, VERTICAL, false)
            (view as RecyclerView).adapter = PlacesListAdapter(it)
        })
    }

    override fun onItemClicked(recyclerView: RecyclerView?, position: Int, v: View?) {
        val item = (recyclerView?.adapter as PlacesListAdapter).geItem(position)
        val title = item.title ?: "No title"
        val positionMessage = "${item.lat}, ${item.lng}"
        DialogUtils.showPlaceDetailsDialog(requireContext(), title, positionMessage)
    }
}