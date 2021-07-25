package com.ufonaut.test.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ufonaut.test.R
import com.ufonaut.test.model.dto.Place
import io.realm.RealmResults

class PlacesListAdapter(private val items: RealmResults<Place>) :
    RecyclerView.Adapter<PlacesListAdapter.PlaceViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_place, null)
        return PlaceViewHolder(view)
    }

    class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val tvName = view.findViewById<TextView>(R.id.tvName)
        internal val tvCoordinates = view.findViewById<TextView>(R.id.tvCoordinates)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.tvName.text = items[position]?.title ?: ""
        val lat = items[position]?.lat ?: 0.0
        val lng = items[position]?.lng ?: 0.0
        holder.tvCoordinates.text = "$lat,$lng"
    }

    fun geItem(position: Int): Place {
        val place = Place()
        place.title =  items[position]?.title
        place.lat =  items[position]?.lat
        place.lng =  items[position]?.lng
        return place
    }
}