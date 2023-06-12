package com.zzt.zt_vp2_rv.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * @author: zeting
 * @date: 2023/4/7
 */

class NestedScrollableRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var scrolling: Boolean = false
    private var touchSlop = 0
    private var initialX = 0f
    private var initialY = 0f
    private val parentViewPager: ViewPager2?
        get() {
            var v: View? = parent as? View
            while (v != null && v !is ViewPager2) {
                v = v.parent as? View
            }
            return v as? ViewPager2
        }

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    private fun canSelfScroll(orientation: Int, delta: Float): Boolean {
        val direction = -delta.sign.toInt()
        return when (orientation) {
            0 -> canScrollHorizontally(direction)
            1 -> canScrollVertically(direction)
            else -> throw IllegalArgumentException()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)
        handleInterceptTouchEvent(ev)
        return dispatchTouchEvent

    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        //如果放在这 会导致recyclerView在down事件后 后续事件就不会在走这个方法了
        //handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    private fun handleInterceptTouchEvent(e: MotionEvent) {
        val orientation = parentViewPager?.orientation ?: return

        println("NestedScrollableRecyclerView handleInterceptTouchEvent--->${e.action}")
        // Early return if child can't scroll in same direction as parent
        if (!canSelfScroll(orientation, -1f) && !canSelfScroll(orientation, 1f)) {
            return
        }

        if (e.action == MotionEvent.ACTION_DOWN) {
            initialX = e.x
            initialY = e.y
            //重置scrolling标识
            scrolling = false
            parent.requestDisallowInterceptTouchEvent(true)
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            val dx = e.x - initialX
            val dy = e.y - initialY
            val isVpHorizontal = orientation == ORIENTATION_HORIZONTAL

            // assuming ViewPager2 touch-slop is 2x touch-slop of child
            val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
            val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f

            if (scaledDx > touchSlop || scaledDy > touchSlop) {
                if (isVpHorizontal == (scaledDy > scaledDx) && scrolling.not()) {
                    // Gesture is perpendicular, allow all parents to intercept
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    // Gesture is parallel, query child if movement in that direction is possible
                    if (scrolling.not() && canSelfScroll(orientation, if (isVpHorizontal) dx else dy)) {
                        // Child can scroll, disallow all parents to intercept
                        scrolling = true
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else if (scrolling.not()){
                        // Child cannot scroll, allow all parents to intercept
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
        }
    }
}