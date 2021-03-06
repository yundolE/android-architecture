package com.example.android.architecture.blueprints.todoapp.screen

import android.os.Bundle
import android.view.MenuItem
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.screen.base.BaseFragment
import com.example.android.architecture.blueprints.todoapp.screen.statistics.StatisticsFragment
import com.example.android.architecture.blueprints.todoapp.screen.tasks.TasksFragment
import com.example.android.architecture.blueprints.todoapp.util.clearAllFragments
import com.example.android.architecture.blueprints.todoapp.util.replaceFragmentInActivity
import com.example.android.architecture.blueprints.todoapp.util.replaceRootFragmentInActivity
import com.naver.android.svc.core.screen.SvcActivity

/**
 * @author bs.nam@navercorp.com
 */
class MainActivity : SvcActivity<MainViews, MainCT>() {
    val contentFragment: BaseFragment<*, *>
        get() = supportFragmentManager.findFragmentById(R.id.contentFrame) as BaseFragment<*, *>

    override fun createControlTower() = MainCT(this, views)
    override fun createViews() = MainViews(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.findFragmentById(R.id.contentFrame)
                as TasksFragment? ?: TasksFragment().also {
            replaceRootFragmentInActivity(it, R.id.contentFrame)
        }

        views.setTitle(R.string.list_title)

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = contentFragment
            views.setTitle(currentFragment.fragmentTitleResId)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Open the navigation drawer when the home icon is selected from the toolbar.
            views.openDrawer()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val currentFragment = contentFragment
        if (currentFragment !is TasksFragment) {
            showTaskList()
        } else {
            return super.onBackPressed()
        }
    }

    fun showStatisticFragment() {
        val currentFragment = contentFragment
        if (currentFragment !is StatisticsFragment) {
            views.hideFab()
            val newFragment = StatisticsFragment()
            views.setTitle(newFragment.fragmentTitleResId)
            replaceFragmentInActivity(newFragment, R.id.contentFrame)
        }
    }

    fun showTaskList() {
        val currentFragment = contentFragment
        if (currentFragment !is TasksFragment) {
            views.showFab()
            views.setTitle(R.string.list_title)
            clearAllFragments()
        }
    }
}