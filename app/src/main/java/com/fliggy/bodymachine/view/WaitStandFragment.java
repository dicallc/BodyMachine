package com.fliggy.bodymachine.view;

import android.os.Bundle;
import android.serialport.utils.SimpleSerialPortUtil;
import android.serialport.utils.Utils;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.model.SerialEvent;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.socks.library.KLog;

import java.util.Timer;
import java.util.TimerTask;

import me.drakeet.materialdialog.MaterialDialog;
import me.yokeyword.fragmentation.SupportFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WaitStandFragment extends SupportFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;
    private String mHeight;
    private String mAge;
    private String mSex;
    private String erromsg;
    private Timer mTimer;

    public WaitStandFragment() {
    }

    public static WaitStandFragment newInstance(String param1, String param2) {
        WaitStandFragment fragment = new WaitStandFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_stand, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

            mTimer = new Timer();
            mTimer.schedule(task, 3000);

        }
    }

    private void startMesure() {
        String str = Utils.sendStartCmd();
        SimpleSerialPortUtil.getInstance().sendCmds(str);
    }

    private boolean isYes = true;
    TimerTask task = new TimerTask() {
        public void run() {

            startMesure();
        }
    };

    private MaterialDialog initDialog(String msg) {
        final MaterialDialog mMaterialDialog = new MaterialDialog(getActivity()).setTitle("友情提示")
                .setMessage(msg);
        return mMaterialDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SerialEvent messageEvent) {
        switch (messageEvent.type) {
            case SerialEvent.HEIGHT:
                mHeight = messageEvent.content + "";
                break;
            case SerialEvent.WEIGHT:
                break;
            case SerialEvent.AGE:
                mAge = messageEvent.content + "";
                break;
            case SerialEvent.SEX:
                mSex = messageEvent.content + "";
                break;
            case SerialEvent.LOAD_USER_DATA:
                KLog.e("LOAD_USER_DATA");
                LoadUserActivity mLoadUserActivity = (LoadUserActivity) getActivity();
                mLoadUserActivity.ShowMeasureUI();
                break;
            case SerialEvent.LOAD_USER_DATA_ERRO:
                isYes = false;
                erromsg = messageEvent.content;
//        if (isYes){
//          LoadUserActivity mLoadUserActivity= (LoadUserActivity) getActivity();
//          mLoadUserActivity.ShowMeasureUI();
//        }else{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //erromsg="姿势可能错误了";
                        final MaterialDialog mMaterialDialog = initDialog(erromsg);
                        mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                mTimer.schedule(task, 3000);
                                startMesure();
                            }
                        });
                        mMaterialDialog.show();
                    }
                });

//        }
                break;
        }
    }
}
