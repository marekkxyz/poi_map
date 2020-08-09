package com.mkaszycki.poimap.ui.map

import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkaszycki.poimap.R
import com.mkaszycki.poimap.ui.glide.GlideApp
import com.mkaszycki.poimap.ui.glide.svg.SvgSoftwareLayerSetter
import kotlinx.android.synthetic.main.bottom_sheet_poi_details_image_list_item.view.*
import java.util.*

class PoiDetailsImageListAdapter(private val imagesUrl: List<String>) :
    RecyclerView.Adapter<PoiDetailsImageListAdapter.PoiDetailImageViewHolder>() {

    class PoiDetailImageViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiDetailImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bottom_sheet_poi_details_image_list_item, parent, false) as View
        return PoiDetailImageViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = imagesUrl.size

    override fun onBindViewHolder(holder: PoiDetailImageViewHolder, position: Int) {
        imagesUrl[position].also {
            if (isSvg(it)) {
                GlideApp.with(holder.view)
                    .`as`(PictureDrawable::class.java)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .listener(SvgSoftwareLayerSetter())
                    .load(it)
                    .into(holder.view.image)
            } else {
                GlideApp.with(holder.view)
                    .load(it)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .centerCrop()
                    .into(holder.view.image)
            }
        }
    }

    private fun isSvg(url: String): Boolean =
        url.substring(url.length - 3).toLowerCase(Locale.getDefault()) == "svg"
}