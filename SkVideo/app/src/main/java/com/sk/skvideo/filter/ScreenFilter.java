package com.sk.skvideo.filter;

import android.content.Context;

import com.sk.skvideo.R;

/**
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class ScreenFilter extends BaseFilter {
    /**
     * 构造器
     *
     * @param context 上下文
     */
    public ScreenFilter(Context context) {
        super(context, R.raw.base_vert, R.raw.base_frag);
    }
}
