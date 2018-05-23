package com.example.android.architecture.blueprints.todoapp.screen

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.screen.tasks.TasksFragment
import com.example.android.architecture.blueprints.todoapp.util.replaceFragmentInActivity
import com.example.android.architecture.blueprints.todoapp.util.setupActionBar
import com.naver.android.svc.core.SvcBaseActivity

/**
 * @author bs.nam@navercorp.com
 */
class TasksActivity : SvcBaseActivity<TasksViews, TasksCT>() {

    private val CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY"

    override fun createControlTower() = TasksCT(this, views)
    override fun createViews() = TasksViews(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                as TasksFragment? ?: TasksFragment().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply {
            putSerializable(CURRENT_FILTERING_KEY, ct.currentFiltering)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Open the navigation drawer when the home icon is selected from the toolbar.
            views.openDrawer()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}