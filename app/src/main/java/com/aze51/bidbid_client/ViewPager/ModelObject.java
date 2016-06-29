package com.aze51.bidbid_client.ViewPager;

import com.aze51.bidbid_client.R;

/**
 * Created by jeon3029 on 16. 6. 28..
 *
 */
public enum ModelObject {
    CURRENT(R.string.current, R.layout.current_list),
    SCHEDULED(R.string.scheduled, R.layout.scheduled_list),
    APPROACHING(R.string.approaching, R.layout.approaching_list);
    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }
    public int getTitleResId() {
        return mTitleResId;
    }
    public int getLayoutResId() {
        return mLayoutResId;
    }

}
