package com.fliggy.bodymachine.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.model.SerialEvent;
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
    private MediaPlayer mediaPlayer;

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
            cancelTimer();
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override public void run() {
                    //LoadUserActivity mLoadUserActivity = (LoadUserActivity) getActivity();
                    //mLoadUserActivity.ShowMeasureUI();
                }
            }, 10000);
            PlayAudio(R.raw.standup);
        }
    }
    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    protected void PlayAudio(int resid) {
        try {
            if (null == mediaPlayer) mediaPlayer = MediaPlayer.create(getActivity(), resid);//重新设置要播放的音频
            mediaPlayer.start();//开始播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
        }
    }
}
