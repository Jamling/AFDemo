/*
 * Copyright 2014-2015 ieclipse.cn.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ieclipse.af.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import cn.ieclipse.af.R;
import cn.ieclipse.af.util.AppUtils;
import cn.ieclipse.af.view.TitleBar;

/**
 * Top Fragment of QuickAF, typically it's a BaseFragment in App module to extend this fragment to customize the app
 * Basic UI/Interaction.
 *
 * @author Jamling
 */
public abstract class AfFragment extends Fragment implements View.OnClickListener {

    protected AfActivity mActivity;
    private boolean overlay = false;
    private boolean showTitleBar = false;
    private int windowBgColor = 0;
    private RelativeLayout mRootView;
    private LayoutInflater mLayoutInflater;
    protected TitleBar mTitleBar;
    private FrameLayout mContentView;
    protected FrameLayout mBottomBar;
    
    protected abstract int getContentLayout();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // init argument
        Bundle bundle = getArguments();
        if (savedInstanceState != null) {
            bundle = savedInstanceState;
        }
        if (bundle != null) {
            initIntent(bundle);
        }
        // init initial data
        initInitData();
        // init root view
        initRootView();

        if (isShowTitleBar()) {
            initHeaderView();
        }

        initContentView(mContentView);
        initData();
        return mRootView;
    }
    
    @Override
    public void onClick(View v) {

    }
    
    protected void setOnClickListener(View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setOnClickListener(this);
                }
            }
        }
    }
    
    protected void initIntent(Bundle bundle) {

    }

    protected void initInitData() {

    }

    protected void initHeaderView(){

    }
    
    protected void initContentView(View view) {

    }
    
    protected void initData() {

    }

//    protected AfActivity getAfActivity() {
//        Activity context = getActivity();
//        if (context == null) {
//            if (getView() != null && getView().getContext() instanceof Activity) {
//                context = (Activity) getView().getContext();
//            }
//        }
//        if (context instanceof AfActivity) {
//            return (AfActivity) context;
//        }
//        return null;
//    }

    public boolean isOverlay() {
        return overlay;
    }

    /**
     * Set should overlay main content with title bar (if show)
     *
     * @param overlay whether overlay or not
     */
    public void setOverlay(boolean overlay) {
        if (this.overlay != overlay) {
            if (mContentView != null) {
                RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) mContentView
                    .getLayoutParams();
                setContentViewLayoutParams(lp, overlay, isShowTitleBar());
            }
        }
        this.overlay = overlay;
    }

    public boolean isShowTitleBar() {
        return showTitleBar || (mTitleBar != null && mTitleBar.getVisibility() != View.GONE);
    }

    public void setShowTitleBar(boolean showTitleBar) {
        if (this.showTitleBar != showTitleBar) {
            this.showTitleBar = showTitleBar;
            if (mTitleBar != null) {
                mTitleBar.setVisibility(showTitleBar ? View.VISIBLE : View.GONE);
            }
            if (mContentView != null) {
                RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) mContentView
                    .getLayoutParams();
                setContentViewLayoutParams(lp, isOverlay(), isShowTitleBar());
            }
        }
    }

    public void setWindowBackground(int colorId) {
        if (colorId > 0) {
            this.windowBgColor = AppUtils.getColor(getActivity(), colorId);
        }
    }

    private void initRootView() {
        Context context = getActivity();
        mLayoutInflater = LayoutInflater.from(context);
        mRootView = new RelativeLayout(context) {
//            @Override
//            protected boolean fitSystemWindows(Rect insets) {
//                return super.fitSystemWindows(insets);
//            }
//
//            @TargetApi(20)
//            @Override
//            public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
//                mRootView.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets
//                    .getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
//                return super.dispatchApplyWindowInsets(insets);
//            }
        };
        mRootView.setFitsSystemWindows(true);
        mRootView.setBackgroundColor(windowBgColor);
        mTitleBar = new TitleBar(context);
        mTitleBar.setId(R.id.titleBar);

        mContentView = new FrameLayout(context);
        mBottomBar = new FrameLayout(context);
        mBottomBar.setId(R.id.bottomBar);

        RelativeLayout.LayoutParams lpTitle = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        lpTitle.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mTitleBar.setLayoutParams(lpTitle);
        // init set title bar visibility, can't use isShowTitleBar()
        mTitleBar.setVisibility(showTitleBar ? View.VISIBLE : View.GONE);

        RelativeLayout.LayoutParams lpBottom = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpBottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lpBottom.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mBottomBar.setLayoutParams(lpBottom);
        mRootView.addView(mBottomBar);

        RelativeLayout.LayoutParams lpContent = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpContent.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        lpContent.addRule(RelativeLayout.ABOVE, mBottomBar.getId());
        setContentViewLayoutParams(lpContent, isOverlay(), isShowTitleBar());

        mRootView.addView(mContentView);
        mRootView.addView(mTitleBar);

        int rootLayoutId = getContentLayout();
        if (rootLayoutId > 0) {
            mLayoutInflater.inflate(rootLayoutId, mContentView, true);
        }

        mRootView.setLayoutParams(
            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void setContentViewLayoutParams(RelativeLayout.LayoutParams lp, boolean overlay, boolean showTitleBar) {
        if (lp != null) {
            if (overlay || !showTitleBar) {
                lp.getRules()[RelativeLayout.BELOW] = 0;
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            }
            else {
                lp.getRules()[RelativeLayout.ALIGN_PARENT_TOP] = 0;
                lp.addRule(RelativeLayout.BELOW, mTitleBar.getId());
            }
            mContentView.setLayoutParams(lp);
        }
    }
    //---------------->

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // mActivity = (AfActivity) activity;
    }
}
