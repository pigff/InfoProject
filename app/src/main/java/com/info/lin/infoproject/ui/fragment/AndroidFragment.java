package com.info.lin.infoproject.ui.fragment;

import com.info.lin.infoproject.ui.base.MainBaseFragment;
import com.info.lin.infoproject.utils.Constants;

/**
 * Created by lin on 2017/2/23.
 */

public class AndroidFragment extends MainBaseFragment {

    public AndroidFragment() {
    }

    @Override
    public String getFragmentType() {
        return Constants.TYPE_ANDROID;
    }

    @Override
    public boolean getCanLoadMore() {
        return true;
    }

    public static AndroidFragment newInstance() {
        return new AndroidFragment();
    }
}