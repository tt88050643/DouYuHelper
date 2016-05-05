package com.example.zhaimeng.douyuhelper.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zhaimeng on 2016/4/26.
 */
public class MyItemView extends View {
    TextView tvName;
    TextView tvContent;

    public MyItemView(Context context) {
        this(context, null);
    }

    public MyItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }



}
