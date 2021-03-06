package com.example.android.architecture.blueprints.todoapp.screen

import com.naver.android.svc.core.views.UseCase

/**
 * @author bs.nam@navercorp.com
 */
interface MainUseCase : UseCase {
    fun onClickStatisticMenu()
    fun onClickFabTaskAdd()
    fun onClickTaskListMenu()

}