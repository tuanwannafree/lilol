package com.machado001.lilol.rotation

import com.machado001.lilol.common.ListChampionPair
import com.machado001.lilol.common.base.BasePresenter
import com.machado001.lilol.common.base.BaseView

interface Rotation {

    interface Presenter : BasePresenter {
        suspend fun fetchRotations()
        fun getImageByPath(version: String, path: String): String
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun goToChampionDetailsScreen(
            championId: String,
            championName: String,
            championVersion: String,
        )

        fun showFailureMessage()
        fun showSuccess(
            freeChampionsMap: ListChampionPair,
            freeChampionForNewPlayersMap: ListChampionPair,
            level: Int,
        )
    }
}

//typealias k = ListChampionPair