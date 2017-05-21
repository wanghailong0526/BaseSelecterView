package com.baseselecterview.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baseselecterview.R;
import com.baseselecterview.utils.UIUtils;

import java.util.List;


/**
 * Created by Administrator on 2017/3/14/0014.
 * 用于顾问平台即时建议，交易所品种筛选
 */

public class BaseSelectorView extends LinearLayout {
    private List<String> titles;

    private List<List<String>> total;
    private HorizontalScrollView hs_activity_tabbar;
    private RadioGroup myRadioGroup;
    private LinearLayout ll_activity_tabbar_content;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private String channel;
    private Button btn_pop;
    private PopupWindow pop;
    private int oldPosition, currentPosition = 0;
    private ExchangeAndVariety mExchangeAndVariety;
    private Context context;
    private ViewGroup mViewGroup;
    private View view;


    public interface ExchangeAndVariety {
        void getExchangeAndVariety(int exchange, int variety);
    }

    public void setExchangeAndVariety(ExchangeAndVariety mExchangeAndVariety) {
        this.mExchangeAndVariety = mExchangeAndVariety;
    }

    public BaseSelectorView(Context context) {
        this(context, null);

    }

    public BaseSelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContext(context);
    }


    private void initContext(Context context) {
        this.context = context;
    }


    private void init(List<String> titles) {
        view = inflate(context, R.layout.horizontal_view, mViewGroup);
        initView();
        initPop(titles);
    }


    //TODO 如果有数据就加载布局，如果没有数据布局显示空白
    public void setData(List<String> titles, List<List<String>> total) {
        this.titles = titles;
        this.total = total;
        if (this.titles == null) {
            return;
        }
        if (this.total == null) {
            return;
        }
        if (this.titles.size() == 0) {
            return;
        }
        if (this.total.size() == 0) {
            return;
        }

        init(titles);

    }

    public void setRootView(ViewGroup root) {
        this.mViewGroup = root;

    }


    private void initPop(List<String> titles) {
        ListView lv = new ListView(getContext());
        lv.setDividerHeight(1);
        lv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        lv.setVerticalScrollBarEnabled(false);
        lv.setCacheColorHint(0x00000000);
        lv.setBackgroundColor(getResources().getColor(R.color.white));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_view, R.id.tv_item, titles);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                if (btn_pop != null) {
                    btn_pop.setText(BaseSelectorView.this.titles.get(position));
                }
                if (oldPosition != currentPosition) {
                    initGroup(position);
                    hs_activity_tabbar.smoothScrollTo(0, 0);
                }
                if (pop != null && pop.isShowing()) {
                    pop.dismiss();
                }
            }
        });

        pop = new PopupWindow(UIUtils.dip2px(70), ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setContentView(lv);

        pop.setTouchable(true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        initGroup(0);
    }

    private void initView() {
        hs_activity_tabbar = (HorizontalScrollView) view.findViewById(R.id.hs_activity_tabbar);
        ll_activity_tabbar_content = (LinearLayout) view.findViewById(R.id.ll_activity_tabbar_content);

        btn_pop = (Button) view.findViewById(R.id.btn_pop);
        btn_pop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop != null) {
                    pop.setFocusable(true);
                }
                if (!pop.isShowing()) {
                    pop.showAsDropDown(v, 0, 10);
                }
            }
        });
        //选项卡布局
        myRadioGroup = new RadioGroup(getContext());
        myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        ll_activity_tabbar_content.addView(myRadioGroup);
    }


    private void initGroup(final int position) {
        oldPosition = position;
        myRadioGroup.removeAllViews();
        for (int i = 0; i < total.get(position).size(); i++) {
            String channel = total.get(position).get(i);
            RadioButton radio = new RadioButton(getContext());
            radio.setButtonDrawable(android.R.color.transparent);
            radio.setBackgroundResource(R.drawable.btn_txt_live_question_mid_selector);
            ColorStateList csl = getResources().getColorStateList(R.color.act_main_tab_color_selector);
            radio.setTextColor(csl);
            LayoutParams l = new LayoutParams(UIUtils.dip2px(45), ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            radio.setCompoundDrawablePadding(UIUtils.dip2px(12));
            radio.setPadding(0, 0, 0, UIUtils.dip2px(10));
            radio.setGravity(Gravity.CENTER);
            radio.setText(channel);
            radio.setTag(channel);
            myRadioGroup.addView(radio);
        }


        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) group.findViewById(radioButtonId);
                channel = (String) rb.getTag();
                mCurrentCheckedRadioLeft = rb.getLeft();//更新当前按钮距离左边的距离
                int width = UIUtils.dip2px(140);
                hs_activity_tabbar.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);
//                System.out.println("aaaaaaaaaaaaaaaa*****************************" + channel + "宽" + width);
//                Toast.makeText(getContext(), channel, Toast.LENGTH_SHORT).show();
                if (mExchangeAndVariety != null) {
                    mExchangeAndVariety.getExchangeAndVariety(position, total.get(position).indexOf(channel));
                }
            }
        });
        //设定默认被选中的选项卡为第一项
        if (!total.get(position).isEmpty()) {
            ((RadioButton) (myRadioGroup.getChildAt(0))).setChecked(true);

        }

    }

}
