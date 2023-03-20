package com.example.githubapp.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.ViewModelFactory
import com.example.githubapp.adapter.ListUserAdapter
import com.example.githubapp.data.remote.response.UserList
import com.example.githubapp.databinding.FragmentFollowingBinding
import com.example.githubapp.utils.Resource


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.followingRv.setHasFixedSize(false)
        binding.followingRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListUserAdapter(mutableListOf())
        binding.followingRv.adapter = adapter


        val user = requireActivity().intent.getStringExtra("login")

        val followingViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val followingViewModel = ViewModelProvider(this, followingViewModelFactory).get(
            FollowingViewModel::class.java
        )

        followingViewModel.getFollowingList(user.toString())

        followingViewModel.followingList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }

                is Resource.Success -> {
                    setLoading(false)
                    setFollowingList(it.data)
                }
            }
        }

    }


    fun setFollowingList(data: List<UserList>) {
        adapter.addData(data)
    }

    fun setLoading(bool: Boolean) {
        binding.followingProgressBar.visibility = if (bool) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}