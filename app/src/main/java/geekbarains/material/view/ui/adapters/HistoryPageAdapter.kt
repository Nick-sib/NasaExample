package geekbarains.material.view.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import geekbarains.material.view.ui.fragmetns.ImageFragment

class HistoryPageAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles = arrayOf("Позавчера", "Вчера", "Сегодня")

    override fun getCount() =titles.size

    override fun getPageTitle(position: Int) = titles[position]

    override fun getItem(position: Int): Fragment =
        ImageFragment.instance(position - 2)
}