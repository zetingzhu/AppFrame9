package com.zzt.aaa

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.OverScroller
import com.zzt.zt_rv_hsc.ScreenUtils
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * @author: zeting
 * @date: 2023/4/20
 * recycleview 嵌套的横向滚动
 */
public class RVNestHorizontalScrollView : HorizontalScrollView {
    val TAG = "RVNestHSV"

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        frRv: RecycleViewNestHSv,
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        this.recycleView = frRv
    }

    /**
     * 需要为每一条 item 绑定 RecycleViewNestHSv
     */
    var recycleView: RecycleViewNestHSv? = null

    private var moveFlag = false
    private var scrollerField: Field? = null
    private var findMethod: Method? = null
    private var downX: Float = 0f


    init {
        // 滑到边界后继续滑动也不会出现弧形光晕
        overScrollMode = View.OVER_SCROLL_NEVER
        scrollerField =
            javaClass.superclass?.getDeclaredField("mScroller")?.apply { isAccessible = true }
        initMethod()
    }

    private fun initMethod() {
        javaClass.superclass?.declaredMethods?.forEach {
            if (TextUtils.equals("findFocusableViewInMyBounds", it.name)) {
                findMethod = it
                findMethod?.isAccessible = true
                return@forEach
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (recycleView?.scrollViews?.contains(this) != true) {
            recycleView?.scrollViews?.add(this)
        }
        recycleView?.maxScrollX?.let {
            scrollTo(it, 0)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> moveFlag = true
            MotionEvent.ACTION_MOVE -> moveFlag = true
            else -> moveFlag = false
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        recycleView?.maxScrollX = scrollX
        Log.w(TAG, "scrollViews size:" + recycleView?.scrollViews?.size)
        recycleView?.scrollViews?.forEach {
            if (it != this@RVNestHorizontalScrollView && moveFlag)
                recycleView?.maxScrollX?.let { it1 ->
                    it.scrollTo(it1, 0)
                    Log.d(TAG, "左右滚动距离：$it1  tag:" + it.tag)
                }
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            (tag as? View)?.onTouchEvent(ev)
            downX = ev.x
            var mScroller: OverScroller?
            recycleView?.scrollViews?.forEach {
                mScroller = scrollerField?.get(it) as? OverScroller
                if (mScroller != null && !mScroller!!.isFinished) {
                    mScroller?.abortAnimation()
                }
            }
            recycleView?.maxScrollX = scrollX
        } else if (ev?.action == MotionEvent.ACTION_MOVE) {
            (tag as? View)?.onTouchEvent(ev)
            if (scrollX == 0 && (ev.x > downX))
                return false
            else if (scrollX == (getChildAt(0).width - width) && (ev.x < downX))
                return false
        } else if (ev?.action == MotionEvent.ACTION_UP) {
            if (kotlin.math.abs(ev.x - downX) < ScreenUtils.dip2px(10f, context))
                (tag as? View)?.onTouchEvent(ev)
        } else {
            (tag as? View)?.onTouchEvent(ev)
        }
        return super.onTouchEvent(ev)
    }

    override fun fling(velocityX: Int) {
        recycleView?.scrollViews?.forEach {
            if (it.childCount > 0) {
                val width: Int = it.width - it.paddingRight - it.paddingLeft
                val right = it.getChildAt(0).width
                val mScroller = scrollerField?.get(it) as? OverScroller
                if (mScroller != null) {
                    mScroller.fling(
                        it.scrollX, it.scrollY, velocityX, 0, 0,
                        Math.max(0, right - width), 0, 0, width / 2, 0
                    )
                    val movingRight = velocityX > 0
                    val currentFocused = it.findFocus()
                    var newFocused = findMethod?.invoke(
                        it,
                        movingRight,
                        mScroller.finalX,
                        currentFocused
                    ) as? View
                    if (newFocused == null) {
                        newFocused = it
                    }
                    if (newFocused !== currentFocused) {
                        newFocused.requestFocus(if (movingRight) View.FOCUS_RIGHT else View.FOCUS_LEFT)
                    }
                    it.postInvalidateOnAnimation()
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        recycleView?.scrollViews?.remove(this)
    }
}