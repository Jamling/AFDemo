/*
 * Copyright (C) 2015-2016 QuickAF2
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
package cn.ieclipse.af.demo.sample;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import cn.ieclipse.af.demo.R;
import cn.ieclipse.af.demo.common.ui.BaseFragment;
import cn.ieclipse.af.view.FlowLayout;

/**
 * Description
 *
 * @author Jamling
 */
public abstract class SampleBaseFragment extends BaseFragment implements AdapterView.OnItemSelectedListener,
    TextWatcher, CompoundButton.OnCheckedChangeListener{
    protected Spinner spn1;
    protected Spinner spn2;
    protected Spinner spn3;
    protected Spinner spn4;
    protected Spinner spn5;
    protected Spinner spn6;

    protected FlowLayout fl1;
    protected FlowLayout fl2;
    protected FlowLayout fl3;

    protected EditText et1;
    protected EditText et2;
    protected EditText et3;
    protected EditText et4;

    protected TextView tv1;
    protected TextView tv2;
    protected TextView tv3;
    protected TextView tv4;

    protected Button btn1;
    protected Button btn2;
    protected Button btn3;
    protected Button btn4;
    protected Button btn5;
    protected Button btn6;

    protected CompoundButton chk1;
    protected CompoundButton chk2;
    protected CompoundButton chk3;
    protected CompoundButton chk4;
    protected CompoundButton chk5;
    protected CompoundButton chk6;


    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        spn1 = (Spinner) view.findViewById(R.id.spn1);
        spn2 = (Spinner) view.findViewById(R.id.spn2);
        spn3 = (Spinner) view.findViewById(R.id.spn3);
        spn4 = (Spinner) view.findViewById(R.id.spn4);
        spn5 = (Spinner) view.findViewById(R.id.spn5);
        spn6 = (Spinner) view.findViewById(R.id.spn6);
        setOnItemSelectedListener(spn1, spn2, spn3, spn4, spn5, spn6);

        fl1 = (FlowLayout) view.findViewById(R.id.fl1);
        fl2 = (FlowLayout) view.findViewById(R.id.fl2);
        fl3 = (FlowLayout) view.findViewById(R.id.fl3);

        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn3 = (Button) view.findViewById(R.id.btn3);
        btn4 = (Button) view.findViewById(R.id.btn4);
        btn5 = (Button) view.findViewById(R.id.btn5);
        btn6 = (Button) view.findViewById(R.id.btn6);
        setOnClickListener(btn1, btn2, btn3, btn4, btn5, btn6);

        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv4 = (TextView) view.findViewById(R.id.tv4);

        et1 = (EditText) view.findViewById(R.id.et1);
        et2 = (EditText) view.findViewById(R.id.et2);
        et3 = (EditText) view.findViewById(R.id.et3);
        et4 = (EditText) view.findViewById(R.id.et4);
        addTextChangedListener(et1, et2, et3, et4);

        chk1 = (CompoundButton) view.findViewById(R.id.chk1);
        chk2 = (CompoundButton) view.findViewById(R.id.chk2);
        chk3 = (CompoundButton) view.findViewById(R.id.chk3);
        chk4 = (CompoundButton) view.findViewById(R.id.chk4);
        chk5 = (CompoundButton) view.findViewById(R.id.chk5);
        chk6 = (CompoundButton) view.findViewById(R.id.chk6);
        setOnCheckedChangeListener(chk1, chk2, chk3, chk4, chk5, chk6);
    }

    protected void setOnItemSelectedListener(Spinner... spinners) {
        if (spinners != null) {
            for (Spinner spinner : spinners) {
                if (spinner != null) {
                    spinner.setOnItemSelectedListener(this);
                }
            }
        }
    }

    protected void addTextChangedListener(EditText... edits) {
        if (edits != null) {
            for (EditText edit : edits) {
                if (edit != null) {
                    edit.addTextChangedListener(this);
                }
            }
        }
    }

    protected void setOnCheckedChangeListener(CompoundButton... checks) {
        if (checks != null) {
            for (CompoundButton chk : checks) {
                if (chk != null) {
                    chk.setOnCheckedChangeListener(this);
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
