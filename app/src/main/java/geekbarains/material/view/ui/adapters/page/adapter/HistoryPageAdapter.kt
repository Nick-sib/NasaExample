package geekbarains.material.view.ui.adapters.page.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import geekbarains.material.model.entity.picture.LoadedData
import geekbarains.material.view.ui.fragmetns.ImageFragment

class HistoryPageAdapter(
        private val data: LoadedData,
        fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount() = data.getCount()

    override fun getPageTitle(position: Int) = data.getTitle(position)

    override fun getItem(position: Int): Fragment =
        ImageFragment.instance(position - 2, position)
}