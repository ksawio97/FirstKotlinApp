package com.example.myfirstapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.databinding.FragmentGambleBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GambleFragment : Fragment() {

    private var _binding: FragmentGambleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGambleBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //load score
        val scoreViewModel = ViewModelProvider(requireActivity())[ScoreViewModel::class.java]
        val score = scoreViewModel.getScore().value ?: 0

        //get info text based on score
        val infoText = getString(R.string.info, score)
        view.findViewById<TextView>(R.id.info).text = infoText

        //gamble score
        val random = java.util.Random()
        var randomNumber = 0
        if (score > 0) {
            randomNumber = random.nextInt((1.5 * score).toInt())
        }

        //show new score and assign it
        view.findViewById<TextView>(R.id.random_number).text = randomNumber.toString()
        scoreViewModel.setScore(randomNumber)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}