package dev.yash.keymanager.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.yash.keymanager.ui.gpg.GpgFragment
import dev.yash.keymanager.ui.ssh.SshFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SshFragment()
            else -> GpgFragment()
        }
    }
}
