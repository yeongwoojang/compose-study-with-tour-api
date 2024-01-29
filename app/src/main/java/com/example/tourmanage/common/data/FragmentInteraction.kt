package com.example.tourmanage.common.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tourmanage.common.value.Config

data class FragmentInteraction(
    var fragmentManager: FragmentManager,
    var type: Config.FRAGMENT_CHANGE_TYPE,
    var fragment: Fragment,
    var container: Int
)
