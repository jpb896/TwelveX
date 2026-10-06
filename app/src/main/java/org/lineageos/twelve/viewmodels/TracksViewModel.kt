/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.twelve.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import org.lineageos.twelve.ext.AUDIOS_SORTING_REVERSE_KEY
import org.lineageos.twelve.ext.AUDIOS_SORTING_STRATEGY_KEY
import org.lineageos.twelve.ext.audiosSortingRule
import org.lineageos.twelve.ext.preferenceFlow
import org.lineageos.twelve.models.FlowResult
import org.lineageos.twelve.models.FlowResult.Companion.asFlowResult
import org.lineageos.twelve.models.SortingRule

class TracksViewModel(application: Application) : TwelveViewModel(application) {
    val sortingRule = sharedPreferences.preferenceFlow(
        AUDIOS_SORTING_STRATEGY_KEY,
        AUDIOS_SORTING_REVERSE_KEY,
        getter = SharedPreferences::audiosSortingRule,
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val audios = sortingRule
        .flatMapLatest { mediaRepository.audios(it) }
        .asFlowResult()
        .flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            FlowResult.Loading
        )

    fun setSortingRule(sortingRule: SortingRule) {
        sharedPreferences.audiosSortingRule = sortingRule
    }
}
