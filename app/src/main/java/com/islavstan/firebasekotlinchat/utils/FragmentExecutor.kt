@file:JvmName("FragmentExecutor")

package com.islavstan.firebasekotlinchat.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction


 fun addFragment(fragmentManager: FragmentManager, containerId:Int, fragment: Fragment, tag:String) {
     fragmentManager.beginTransaction().replace(containerId, fragment, tag).commit()
 }