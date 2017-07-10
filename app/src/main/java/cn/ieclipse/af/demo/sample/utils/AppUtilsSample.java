package cn.ieclipse.af.demo.sample.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

import cn.ieclipse.af.demo.R;
import cn.ieclipse.af.demo.sample.SampleBaseFragment;
import cn.ieclipse.af.util.AppUtils;
import cn.ieclipse.af.util.FileUtils;

/**
 * Description
 *
 * @author Jamling
 */

public class AppUtilsSample extends SampleBaseFragment {
    @Override
    protected int getContentLayout() {
        return R.layout.sample_app_utils;
    }

    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        String sys = String.format("model:%s,sdk:%s", AppUtils.getModel(), AppUtils.getSDKVersion());

        String runtime = String.format("runtime/heap memory,max(app max memory)=%s,total(app use from os)=%s,free=%s",
            FileUtils.formatFileSize(Runtime.getRuntime().maxMemory()),
            FileUtils.formatFileSize(Runtime.getRuntime().totalMemory()),
            FileUtils.formatFileSize(Runtime.getRuntime().freeMemory()));
        ActivityManager.MemoryInfo memoryInfo = AppUtils.getMemory(getActivity());
        String amMem = String.format("os memory, total=%s,available=%s, used=%s%%",
            FileUtils.formatFileSize(memoryInfo.totalMem), FileUtils.formatFileSize(memoryInfo.availMem),
            (memoryInfo.totalMem - memoryInfo.availMem) * 100 / memoryInfo.totalMem);
        ActivityManager am = (ActivityManager) view.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        String memClass = String.format("memoryClass=%dM, largeMemoryClass=%dM", am.getMemoryClass(), am
            .getLargeMemoryClass());
        tv1.setText(String.format("%s\n%s\n%s\n%s", sys, runtime, amMem, memClass));

        DisplayMetrics dm = AppUtils.getDisplayMetrics(getActivity());
        String screen = String.format(
            "width=%d,height=%d,densityDpi=%d,status bar height=%d,softKey=%s, " + "softKeyHeight=%s", dm.widthPixels,
            dm.heightPixels, dm.densityDpi, AppUtils.getStatusBarHeight(getActivity()),
            AppUtils.hasVirtualSoftKey(getActivity()), AppUtils.getSoftKeyBarHeight(getActivity()));
        tv2.setText(screen);
    }
}
