package geekbarains.material.view.indicator

import android.annotation.SuppressLint
import android.content.Context
import android.database.DataSetObserver
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.core.view.children
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import geekbarains.material.R
import kotlin.math.min

class PageIndicatorView :
        View,
        ViewPager.OnPageChangeListener,
        ViewPager.OnAdapterChangeListener {

    private var dataSetObserver: DataSetObserver? = null

    private val pLeft = paddingLeft
    private val pRight = paddingRight
    private val pBottom = paddingBottom
    private var realWidth = 2f
    private var realHeight = 2f

    private var strokeWidth = 3f
    private var radius = 10f
    private var maxOffset = 20f

    private var leftOffset = pLeft.toFloat()
    private var bottomOffset = pLeft.toFloat()

    private var pageCount = 0
    private var step = 2f

    private var dot = Dot(this, leftOffset,
            bottomOffset,
            strokeWidth,
            step,
            radius,
            false,
            0)

    var viewPager: ViewPager? = null

    var isInteraction = false
        set(value) {
            field = value
            dot.isInteraction = value
        }
        get() = dot.isInteraction

    private var position = 0
        set(value) {
            field = value
            dot.position = value

        }
        get() = dot.position

    private val indicatorPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = strokeWidth
        isAntiAlias = true
        color = Color.GRAY
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs, 0) {

        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.PageIndicatorView,0, 0)
        try {
            findViewPager(parent)
            radius = styledAttributes.getDimension(R.styleable.PageIndicatorView_radius, 10f )
            strokeWidth = styledAttributes.getDimension(R.styleable.PageIndicatorView_penWidth, 1f)
            indicatorPaint.strokeWidth = strokeWidth
            maxOffset =  styledAttributes.getDimension(R.styleable.PageIndicatorView_maxOffset, radius * 4)
            isInteraction = styledAttributes.getInteger(R.styleable.PageIndicatorView_select, 0) == 0
            isInteraction = isInteraction && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        } finally {
            styledAttributes.recycle()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        realWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        realHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()

        step = min (maxOffset, (realWidth - pLeft - pRight - ((radius + strokeWidth) * 2)) / (pageCount - 1))
        leftOffset = (realWidth - step * (pageCount - 1)) / 2
        bottomOffset = realHeight - pBottom - radius - strokeWidth

        dot = Dot(
                this,
                leftOffset,
                bottomOffset,
                strokeWidth,
                step,
                radius,
                isInteraction,
                position
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (position in 0 until pageCount) {
            canvas.drawCircle(leftOffset + step * position, bottomOffset, radius, indicatorPaint)
        }
        dot.draw(canvas)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        findViewPager(parent)
    }

    override fun onDetachedFromWindow() {
        viewPager?.also { pager ->
            pager.removeOnPageChangeListener(this)
            dataSetObserver?.also { observer ->
                pager.adapter?.unregisterDataSetObserver(observer)
            }
        }
        super.onDetachedFromWindow()
    }

    private fun findViewPager(viewParent: ViewParent?) {
        if (!(viewParent != null && viewParent is ViewGroup && viewParent.childCount > 0)) {
            return
        }

        viewParent.children.forEach { view ->
            if (view is ViewPager) {
                viewPager = view
                pageCount = view.adapter?.count ?: 0
                position = view.currentItem
                view.addOnPageChangeListener(this)
                registerSetObserver()
                invalidate()
                return
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (isInteraction) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dot.play(position, positionOffset)
            } else {
                dot.position = position
            }
        }
    }

    override fun onPageSelected(position: Int) {
        dot.position = position
    }

    override fun onAdapterChanged(
            viewPager: ViewPager,
            oldAdapter: PagerAdapter?,
            newAdapter: PagerAdapter?
    ) {
        registerSetObserver()
    }

    private fun registerSetObserver() {
        if (dataSetObserver != null || viewPager?.adapter == null) {
            return
        }

        dataSetObserver = object : DataSetObserver() {
            override fun onChanged() {
                viewPager?.adapter?.also {
                    pageCount = it.count
                    requestLayout()
                }
            }
        }
        dataSetObserver?.run {
            viewPager?.adapter?.registerDataSetObserver(this)
        }

    }

}