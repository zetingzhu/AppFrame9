package com.zzt.aaa

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzt.zt_rv_hsc.R
import com.zzt.zt_rv_hsc.requestData
import kotlinx.coroutines.delay

class RecycleViewNestHSv @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    val TAG = "RVNestHSV"

    init {
        layoutManager = LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
    }

    val scrollViews by lazy { ArrayList<RVNestHorizontalScrollView>() }

    var maxScrollX: Int = 0

//    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
//        val rightScroll = child?.findViewById<View>(R.id.right_scroll)
//        if (rightScroll == null || rightScroll.parent is RVNestHorizontalScrollView) {
//            super.addView(child, index, params)
//        } else {
//            val group = rightScroll.parent as ViewGroup
//            val tempParams =
//                ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
//            val scrollView = RVNestHorizontalScrollView(
//                this,
//                context
//            )
//            group.removeView(rightScroll)
//            scrollView.tag = child
//            scrollView.addView(rightScroll, tempParams)
//            group.addView(scrollView, tempParams)
//            super.addView(child, index, params)
//            requestData {
//                do {
//                    scrollView.scrollTo(maxScrollX, 0)
//                    delay(20)
//                } while (scrollView.scrollX < maxScrollX)
//            }
//        }
//    }

}


