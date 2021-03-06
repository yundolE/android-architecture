package com.example.android.architecture.blueprints.todoapp.screen.addedittask

import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.naver.android.svc.core.SvcCT

/**
 * @author bs.nam@navercorp.com
 */
class AddEditTaskCT(screen: AddEditTaskActivity, views: AddEditTaskViews, private val tasksRepository: TasksDataSource) : SvcCT<AddEditTaskActivity, AddEditTaskViews>(screen, views), AddEditTaskUseCase, TasksDataSource.GetTaskCallback {

    var isDataMissing: Boolean = false
    var taskId: String? = null

    override fun onCreated() {
        if (taskId != null && isDataMissing) {
            populateTask()
        }
    }


    fun saveTask(title: String, description: String) {
        if (taskId == null) {
            createTask(title, description)
        } else {
            updateTask(title, description)
        }
    }

    fun populateTask() {
        val taskId = taskId ?: throw RuntimeException("populateTask() was called but task is new.")
        tasksRepository.getTask(taskId, this)
    }

    override fun onTaskLoaded(task: Task) {
        // The view may not be able to handle UI updates anymore
        if (views.isActive) {
            views.setTitle(task.title)
            views.setDescription(task.description)
        }
        isDataMissing = false
    }

    override fun onDataNotAvailable() {
        // The view may not be able to handle UI updates anymore
        if (views.isActive) {
            views.showEmptyTaskError()
        }
    }

    private fun createTask(title: String, description: String) {
        val newTask = Task(title, description)
        if (newTask.isEmpty) {
            views.showEmptyTaskError()
        } else {
            tasksRepository.saveTask(newTask)
            screen.showTasksList()
        }
    }

    private fun updateTask(title: String, description: String) {
        val taskId = taskId ?: throw RuntimeException("updateTask() was called but task is new.")
        tasksRepository.saveTask(Task(title, description, taskId))
        screen.showTasksList() // After an edit, go back to the list.
    }

    override fun onClickFabEditTaskDone(title: String, description: String) {
        saveTask(title, description)
    }
}