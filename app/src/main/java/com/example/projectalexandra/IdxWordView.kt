package com.example.projectalexandra

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class IdxWordView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
    ) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyle){

    var timestamp : Int = 0
    var duration : Int = 0

    init{
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                R.styleable.IdxWordView, 0, 0)
            val timestamp = resources.getInteger(typedArray
                .getResourceId(R.styleable
                    .IdxWordView_timestamp,0))

            this.timestamp = timestamp

            typedArray.recycle()
        }

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                R.styleable.IdxWordView, 0, 0)
            val timestamp = resources.getInteger(typedArray
                .getResourceId(R.styleable
                    .IdxWordView_duration,0))

            this.duration = duration

            typedArray.recycle()
        }
    }


}