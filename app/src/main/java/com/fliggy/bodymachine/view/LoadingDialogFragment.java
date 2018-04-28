package com.fliggy.bodymachine.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fliggy.bodymachine.R;

public class LoadingDialogFragment extends DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, null);//需要用android.R.id.content这个view
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
            //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        //getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

}