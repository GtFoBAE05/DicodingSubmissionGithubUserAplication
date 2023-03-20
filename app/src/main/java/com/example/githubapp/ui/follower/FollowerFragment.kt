package com.example.githubapp.ui.follower


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
import com.example.githubapp.databinding.FragmentFollowerBinding
import com.example.githubapp.utils.Resource

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: ListUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.followerRv.setHasFixedSize(false)
        binding.followerRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListUserAdapter(mutableListOf())
        binding.followerRv.adapter = adapter

        val user = requireActivity().intent.getStringExtra("login")

        val followerViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val followerViewModel =
            ViewModelProvider(this, followerViewModelFactory).get(FollowerViewModel::class.java)

        followerViewModel.getFollowerList(user.toString())

        followerViewModel.followerList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }

                is Resource.Success -> {
                    setLoading(false)
                    setFollowerList(it.data)
                }
            }
        }

    }

    fun setFollowerList(data: List<UserList>) {
        adapter.addData(data)
    }

    fun setLoading(bool: Boolean) {
        binding.followerProgressBar.visibility = if (bool) View.VISIBLE else View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}