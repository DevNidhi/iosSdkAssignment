package com.gamechange.assignment.ui.record


import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.gamechange.assignment.R
import com.gamechange.assignment.adapters.CommentsAdapter
import com.gamechange.assignment.common.Constants
import com.gamechange.assignment.dagger.component.DaggerMyComponent
import com.gamechange.assignment.dagger.component.MyComponent
import com.gamechange.assignment.ui.home.MainActivity
import com.gamechange.assignment.viewmodels.CommentsDataModelFactory
import com.gamechange.assignment.viewmodels.CommentsDataViewModel
import kotlinx.android.synthetic.main.fragment_issues_listing.*
import kotlinx.coroutines.launch
import learnkotlin.com.mvvmweatherforecast.ui.base.ScopedFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class CommentsListingFragment() : ScopedFragment(),KodeinAware {


    override val kodein by closestKodein()
    private lateinit var title: String

    private lateinit var commentViewModel: CommentsDataViewModel
    private val commentsDataModelFactory: CommentsDataModelFactory by instance()
    private var adapter = CommentsAdapter()

    companion object {
        const val EXTRA_NAME = "com.phable.assignment.EXTRA_TITLE"
    }

    var myComponent: MyComponent? = null
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issues_listing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        myComponent = DaggerMyComponent.builder().build();
        myComponent?.inject(activity as MainActivity);

        commentViewModel = ViewModelProviders.of(this,commentsDataModelFactory)
            .get(CommentsDataViewModel::class.java)

        title = Constants.TAG_COMMENTS
        if (arguments != null) {
            val noOfComments = arguments?.getString(EXTRA_NAME)?.toInt()
            if (noOfComments != null && noOfComments==0) {
                text_view_no_comments.visibility = View.VISIBLE
            }else{
                bindUI()
            }
        }
        (activity as AppCompatActivity).supportActionBar?.setTitle(title)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)

        adapter = CommentsAdapter()

        recycler_view.adapter = adapter

        (activity as? AppCompatActivity)?.title = title
    }

    private fun bindUI()= launch {
        progressBar1.visibility = View.VISIBLE
        val currentComment = commentViewModel.currentComments.await()
        currentComment.observe(this@CommentsListingFragment, Observer {
            //Log.v("Comments : ",it.toString())
             adapter.submitList(it)
            progressBar1.visibility = View.GONE
        })
    }








}
