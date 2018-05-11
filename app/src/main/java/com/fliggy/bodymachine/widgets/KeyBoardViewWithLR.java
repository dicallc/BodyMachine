package com.fliggy.bodymachine.widgets;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.adapter.KeyBoardAdapter;
import java.util.ArrayList;

/**
 * Created by dicallc on 2018/4/13.
 */

public class KeyBoardViewWithLR extends LinearLayout {
  private  LinearLayout mHeight_ly;
  private  TextView mShow_text;
  private ImageView mImg_del;
  private String mStringPassword; //输入的密码
  private TextView[] mTextViewPsw;
  private RecyclerView mGridView; //支付键盘布局
  private ArrayList<String> valueList;
  private int currentIndex = -1;// 用于记录当前输入密码格位置
  private View mView;
  private TextView mTips;
  private ImageView mImg_pre;
  private ImageView mImg_next;
  private RelativeLayout mRl_bg;
  private TextView mTxt_wei;
  private TextView mTextView;

  public KeyBoardViewWithLR(Context context) {
    this(context, null);
  }

  public KeyBoardViewWithLR(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public void setTips(String tips) {
    mTips.setText(tips);
  }

  public TextView getTxt_wei() {
    return mTxt_wei;
  }

  public KeyBoardViewWithLR(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    LayoutInflater.from(context).inflate(R.layout.view_keyboard_lr, this);
    valueList = new ArrayList<>();
    mTextViewPsw = new TextView[3];
    mTextViewPsw[0] = (TextView) findViewById(R.id.height_p_one);
    mTextViewPsw[1] = (TextView) findViewById(R.id.height_p_two);
    mTextView = (TextView) findViewById(R.id.height_p_three);
    mTextViewPsw[2] = mTextView;
    mTips = (TextView) findViewById(R.id.txt_show_tips);
    mImg_del = (ImageView) findViewById(R.id.img_del);
    mGridView = (RecyclerView) findViewById(R.id.rl_keyboard);
    mImg_pre = (ImageView) findViewById(R.id.img_pre);
    mImg_next = (ImageView) findViewById(R.id.img_next);
    mRl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
    mTxt_wei = (TextView) findViewById(R.id.txt_wei);
    mHeight_ly = (LinearLayout) findViewById(R.id.height_ly);
    mShow_text = (TextView) findViewById(R.id.show_text);
    setView();
  }

  public void setWei() {
    mHeight_ly.setVisibility(GONE);
    mTextViewPsw[2] = null;
  }

  public String getValue() {
    StringBuffer value=new StringBuffer();
    for (int i = 0; i <mTextViewPsw.length&&mTextViewPsw[i]!=null ; i++) {
      value.append(mTextViewPsw[i].getText().toString().trim());
    }
    return value.toString();
  }

  public RelativeLayout getRl_bg() {
    return mRl_bg;
  }


  public ImageView getImg_pre() {
    return mImg_pre;
  }

  public ImageView getImg_next() {
    return mImg_next;
  }

  // 初始化按钮上应该显示的数字
  private void setView() {
    mShow_text.setVisibility(GONE);
    GridLayoutManager mManager = new GridLayoutManager(getContext(), 5);
    //LinearLayoutManager mManager = new LinearLayoutManager(getContext());
    //mManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    for (int i = 1; i < 10; i++) {
      valueList.add(i + "");
    }
    valueList.add(0 + "");
    KeyBoardAdapter mAdapter = new KeyBoardAdapter(valueList);
    mGridView.setLayoutManager(mManager);
    mGridView.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(currentIndex==mTextViewPsw.length-1)return;
        mTextViewPsw[++currentIndex].setText(valueList.get(position));
      }
    });
    mImg_del.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View mView) {
        mTextViewPsw[currentIndex--].setText("");
      }
    });
  }

  /**
   * 设置监听方法，在第6位输入完后触发
   */
  public void setOnFinishInput() {
    mTextViewPsw[2].addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override public void afterTextChanged(Editable s) {
        if (s.toString().length() == 1) {
          mStringPassword = ""; //每次触发都要将mStringPassword置空再重新获取，避免由于输入删除再输入造成混乱
          for (int i = 0; i < 6; i++) {
            mStringPassword += mTextViewPsw[i].getText().toString().trim();
          }
          //TODO 接口中要实现的方法，完成密码输入完成后的响应逻辑
        }
      }
    });
  }
}
