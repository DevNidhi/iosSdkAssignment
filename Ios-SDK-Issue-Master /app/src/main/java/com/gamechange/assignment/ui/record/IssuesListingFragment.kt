package com.gamechange.assignment.ui.record

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.gamechange.assignment.adapters.IssuesAdapter
import com.gamechange.assignment.ui.record.CommentsListingFragment.Companion.EXTRA_NAME
import kotlinx.android.synthetic.main.fragment_issues_listing.*
import com.gamechange.assignment.R
import com.gamechange.assignment.data.Issue
import com.gamechange.assignment.viewmodels.*
import kotlinx.coroutines.launch
import learnkotlin.com.mvvmweatherforecast.ui.base.ScopedFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RecordListingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RecordListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordListingFragment() : ScopedFragment(),KodeinAware {


    override val kodein by closestKodein()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var issueDataViewModel: IssueDataViewModel
    private val issueDataModelFactory :  IssueDataModelFactory by instance()
    private var adapter = IssuesAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issues_listing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)

         adapter = IssuesAdapter()
        recycler_view.adapter = adapter

        issueDataViewModel = ViewModelProviders.of(this,issueDataModelFactory)
            .get(IssueDataViewModel::class.java)

        bindUI()

        adapter.setOnItemClickListener(object : IssuesAdapter.OnItemClickListener {
            override fun onItemClick(selectedIssue: Issue) {
                navigateToComments(selectedIssue)
            }
        })

    }

    private fun bindUI()= launch {
        progressBar1.visibility = View.VISIBLE
        val currentIssues = issueDataViewModel.currentIssues.await()
        currentIssues.observe(this@RecordListingFragment, Observer {
         //    Log.v("Changes : ",it.toString())
            adapter.submitList(it)
            progressBar1.visibility = View.GONE
        })

    }


    fun navigateToComments(selectedIssue : Issue?) {
        var fragment = CommentsListingFragment()
        if(selectedIssue != null) {
            issueDataViewModel.commentUrl = selectedIssue.comments_url
            issueDataViewModel.setCommentAndIssueUrls(selectedIssue.comments_url,selectedIssue.url)
            var args = Bundle()
            args.putString(EXTRA_NAME, selectedIssue.noOfComments)
            fragment.arguments = args
        }
        fragmentManager?.beginTransaction()?.addToBackStack("AddEditFragment")?.add(
            R.id.root,fragment )?.commit()
    }


}
