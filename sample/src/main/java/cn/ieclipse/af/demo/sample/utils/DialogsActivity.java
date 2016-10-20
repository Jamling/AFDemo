/*
 * Copyright (C) 2015-2016 QuickAF
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
package cn.ieclipse.af.demo.sample.utils;

import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import cn.ieclipse.af.app.AfDialogFragment;
import cn.ieclipse.af.app.AlertDialogFragment.AlertInterceptor;
import cn.ieclipse.af.demo.R;
import cn.ieclipse.af.demo.sample.SampleBaseActivity;
import cn.ieclipse.af.util.DialogUtils;

/**
 * 类/接口描述
 * 
 * @author Jamling
 *       
 */
public class DialogsActivity extends SampleBaseActivity {

    @Override
    protected void initHeaderView() {
        super.initHeaderView();
        setTitle("DialogUtils demo");
    }

    @Override
    protected int getContentLayout() {
        return R.layout.sample_activity_dialogs;
    }
    
    public void showToast(View view) {
        DialogUtils.showToast(this, "DialogUtils.showToast(context, msg)");
    }
    
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
    
    public void showSimplestAlert(View view) {
        DialogUtils.showAlert(this, android.R.drawable.ic_dialog_alert, null, "我是最简单的警告对话框, 没有图标，没有标题, 没有Button");
    }
    
    public void showCommonAlertWithListener(View view) {
        DialogUtils.showAlert(this, android.R.drawable.ic_dialog_alert, getString(android.R.string.dialog_alert_title),
            "我是常用的警告对话框, 带两个Button", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DialogUtils.showToast(DialogsActivity.this, "我点击的是确定");
                }
            }, DialogUtils.defaultOnClick());
    }

    public void showCustomAlert(View view) {
        DialogUtils.showAlert(this, new AlertInterceptor() {

            @Override
            public void onCreated(Builder builder) {
                TextView view = new TextView(DialogsActivity.this);
                view.setText(Html.fromHtml(
                    "I am use <font color='blue'>cn.ieclipse.af.util.DialogUtils.showAlert(Activity context, "
                        + "AlertInterceptor interceptor)</font> to show an alert Dialog, you can access all method of"
                        + " AlertDialog in <font color='#ff0000'>AlertInterceptor.onDialogCreated()</font>"));
                builder.setView(view);
                builder.setTitle(getString(android.R.string.dialog_alert_title));
                builder.setPositiveButton("我可以更改Button名字哦", DialogUtils.defaultOnClick());
            }
        });
    }

    public void showStyleAlert(View view) {
        DialogUtils.showAlert(this, new AlertInterceptor() {

            @Override
            public void onCreated(Builder builder) {
                TextView view = new TextView(DialogsActivity.this);
                view.setText(Html.fromHtml(
                    "I am use <font color='blue'>cn.ieclipse.af.util.DialogUtils.showAlert(Activity context, "
                        + "AlertInterceptor interceptor)</font> to show an alert Dialog, you can access all method of"
                        + " AlertDialog in <font color='#ff0000'>AlertInterceptor.onDialogCreated()</font>"));
                builder.setView(view);
                builder.setTitle(getString(android.R.string.dialog_alert_title));
                builder.setPositiveButton("我可以更改Button名字哦", DialogUtils.defaultOnClick());
            }
        });
    }

    public void showAlertWithStyle(View view) {
        int style = android.R.style.Theme_Holo_Dialog;
        DialogUtils.showAlert(this, style, 0, "title", "message", null, DialogUtils.defaultOnClick(),
            DialogUtils.defaultOnClick());
    }

    public void showDialog1(View view) {
        AlertFragment fragment = new AlertFragment();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        fragment.setAlert(chk1.isChecked());
        fragment.show(getFragmentManager(), chk2.isChecked());
    }

    public static class AlertFragment extends AfDialogFragment<Void> {

        private TextView tvTitle;

        @Override
        protected int getContentLayout() {
            return R.layout.sample_dialog_alert;
        }

        @Override
        protected void initContentView(View view) {
            super.initContentView(view);
            tvTitle = (TextView) view.findViewById(android.R.id.title);
            TextView tvMessage = (TextView) view.findViewById(android.R.id.message);
            tvTitle.setOnClickListener(this);
        }

        @Override
        protected void onCreateAlertDialog(Builder builder) {
            super.onCreateAlertDialog(builder);
            builder.setPositiveButton(android.R.string.ok, this);
        }

        @Override
        protected void onOkPressed() {
            super.onOkPressed();
            DialogUtils.showToast(getActivity(), "Ok pressed");
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            super.onClick(dialog, which);
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            if (view == tvTitle) {
                DialogUtils.showToast(getActivity(), "title pressed");
            }
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            DialogUtils.showToast(getActivity(), "dismiss");
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            DialogUtils.showToast(getActivity(), "cancel");
        }


    }
}
