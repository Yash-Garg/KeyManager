package dev.yash.keymanager.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.R
import dev.yash.keymanager.adapters.ViewPagerAdapter
import dev.yash.keymanager.databinding.HomeFragmentBinding
import dev.yash.keymanager.ui.dialogs.GpgNewKeyDialogFragment
import dev.yash.keymanager.ui.dialogs.SshNewKeyDialogFragment
import dev.yash.keymanager.utils.viewBinding

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private val binding by viewBinding(HomeFragmentBinding::bind)

    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var mediator: TabLayoutMediator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerAdapter = ViewPagerAdapter(this)
        binding.pager.adapter = viewPagerAdapter

        binding.addKey.setOnClickListener {
            if (binding.pager.currentItem == 1) {
                GpgNewKeyDialogFragment.newInstance().show(childFragmentManager, null)
            } else SshNewKeyDialogFragment.newInstance().show(childFragmentManager, null)
        }

        mediator =
            TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
                if (position == 0) {
                    tab.text = "SSH KEYS"
                } else tab.text = "GPG KEYS"
            }
        mediator?.attach()
    }

    override fun onStop() {
        super.onStop()
        mediator?.detach()
        mediator = null
        binding.pager.adapter = null
        viewPagerAdapter = null
    }
}
