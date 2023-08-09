package com.example.myfirstapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myfirstapp.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //toast
        view.findViewById<Button>(R.id.toast_button).setOnClickListener {
            val myToast = Toast.makeText(context, "GAMBLE!!!!", Toast.LENGTH_SHORT)
            myToast.show()
        }

        val scoreViewModel = ViewModelProvider(requireActivity())[ScoreViewModel::class.java]
        val scoreLiveData = scoreViewModel.getScore()

        //count_button on click handle
        view.findViewById<Button>(R.id.count_button).setOnClickListener {
            scoreViewModel.addToScore(1)
        }

        //textview_first on change value update
        scoreLiveData.observe(requireActivity()) { newScore ->
            val shownCountText = view.findViewById<TextView>(R.id.textview_first)
            shownCountText.text = (newScore ?: 0).toString()
        }

        //random_button on click handle
        view.findViewById<Button>(R.id.random_button).setOnClickListener {
            val action = MainFragmentDirections.actionFirstFragmentToSecondFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("debug", "destroyFirstFragment")
        _binding = null
    }
}