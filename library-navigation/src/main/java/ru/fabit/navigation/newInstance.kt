package ru.fabit.navigation

import androidx.fragment.app.Fragment

inline fun <reified Params : ScreenParams> Fragment.newInstance(params: Params): Fragment {
    return apply {
        arguments = params.toArgumentsBundle()
    }
}