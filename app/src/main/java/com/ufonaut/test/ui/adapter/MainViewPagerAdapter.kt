package com.ufonaut.test.ui.adapter

import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ufonaut.test.ui.fragment.CustomMapFragment
import com.ufonaut.test.ui.fragment.ListFragment

class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount(): Int = pages.size

    override fun getItem(position: Int): Fragment {
        return pages[position].fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return pages[position].title
    }

    companion object {
        private const val MAP_TITLE = "Map"
        private const val LIST_TITLE = "List"

        val pages = listOf(
                Page(MAP_TITLE, CustomMapFragment()),
                Page(LIST_TITLE, ListFragment())
        )
    }
}

data class Page(val title: String, val fragment: Fragment)