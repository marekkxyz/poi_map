package com.mkaszycki.poimap.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkaszycki.poimap.R
import kotlinx.android.synthetic.main.bottom_sheet_route_suggestions_item.view.*

class RouteSuggestionsListAdapter(private val suggestions: List<String>) :
    RecyclerView.Adapter<RouteSuggestionsListAdapter.RouteSuggestionViewHolder>() {

    class RouteSuggestionViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteSuggestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bottom_sheet_route_suggestions_item, parent, false) as View
        return RouteSuggestionViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = suggestions.size

    override fun onBindViewHolder(holder: RouteSuggestionViewHolder, position: Int) {
        holder.view.suggestionTextView.text = suggestions[position]
    }
}