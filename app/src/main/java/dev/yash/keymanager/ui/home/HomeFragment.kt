package dev.yash.keymanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.yash.keymanager.databinding.HomeFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dev.yash.keymanager.adapters.ViewPagerAdapter
import dev.yash.keymanager.ui.dialogs.GpgNewKeyDialogFragment
import dev.yash.keymanager.ui.dialogs.SshNewKeyDialogFragment

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding
        get() = _binding!!

    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var mediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
        mediator = null
        binding.pager.adapter = null
        viewPagerAdapter = null
        _binding = null
    }
}
