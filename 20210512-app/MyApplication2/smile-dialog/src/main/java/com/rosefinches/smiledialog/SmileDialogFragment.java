package com.rosefinches.smiledialog;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;

import com.rosefinches.smiledialog.enums.SmileDialogType;
import com.rosefinches.smiledialog.enums.WidgetTypes;
import com.rosefinches.smiledialog.interfac.OnCancelClickListener;
import com.rosefinches.smiledialog.interfac.OnConformClickListener;


public class SmileDialogFragment extends DialogFragment {


    private OnFragmentCreatedListener mListener;
    private OnCancelClickListener mCancelClickListener;
    private OnConformClickListener mConformClickListener;


    ImageView mIcon;
    TextView mTitle, mContent, mOk, mCancel;


    public SmileDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE_NO_TITLE, R.style.SmileDialogStyle);

    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setWindowAnimations(R.style.dialog_anim_style);


        View view = inflater.inflate(R.layout.fragment_smile_dialog, container, false);
        mIcon = view.findViewById(R.id.iv_icon);
        mTitle = view.findViewById(R.id.tv_title);
        mContent = view.findViewById(R.id.tv_content);
        mOk = view.findViewById(R.id.btn_ok);
        mCancel = view.findViewById(R.id.btn_cancel);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConformClickListener != null) {
                    mConformClickListener.onConformClicked();
                }
                dismiss();

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelClickListener != null) {
                    mCancelClickListener.onCancelClickListener();
                }
                dismiss();

            }
        });

        return view;
    }

    /**
     * ?????????????????????
     *
     * @param type ??????
     */
    void setDialogType(@SmileDialogType int type) {
        if (type == SmileDialogType.WARNING) {
            mIcon.setImageResource(R.drawable.anim_warning);
        }

        if (type == SmileDialogType.SUCCESS) {
            mIcon.setImageResource(R.drawable.anim_success);
        }

        if (type == SmileDialogType.ERROR) {
            mIcon.setImageResource(R.drawable.anim_error);
        }

        Drawable drawable = mIcon.getDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param listener
     */
    void addOnFragmentCreatedListener(OnFragmentCreatedListener listener) {
        mListener = listener;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (mListener != null) {
            mListener.onFragmentCreated();
        }


        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * ??????????????????????????????
     *
     * @param listener ??????
     */
    void addOnConformClickListener(OnConformClickListener listener) {
        mConformClickListener = listener;
    }

    /**
     * ??????????????????????????????
     *
     * @param listener ??????
     */
    void addOnCancelClickListener(OnCancelClickListener listener) {
        mCancelClickListener = listener;
    }

    /**
     * ??????????????????????????????TextView
     *
     * @param widgets ????????????
     * @return ??????
     */
    private TextView getTextViewByWidgetType(@WidgetTypes int widgets) {

        if (widgets == WidgetTypes.TITLE) {
            return mTitle;
        }
        if (widgets == WidgetTypes.CONTENT) {
            return mContent;
        }
        if (widgets == WidgetTypes.CONFORM) {

            return mOk;
        }
        if (widgets == WidgetTypes.CANCEL) {
            return mCancel;
        }
        return null;
    }

    /**
     * ????????????
     *
     * @param widgets ????????????
     * @param text    ??????
     */
    void setText(@WidgetTypes int widgets, CharSequence text) {
        TextView mText = getTextViewByWidgetType(widgets);
        if (mText != null) {
            mText.setText(text);
        }
    }

    /**
     * ?????????????????????true ?????????false ??????
     *
     * @param isHided ????????????
     */
    public void hideCancelButton(boolean isHided) {
        if (isHided) {
            mCancel.setVisibility(View.GONE);
        } else {
            mCancel.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ??????title
     *
     * @param isHided ????????????
     */
    void hideTitle(boolean isHided) {
        if (isHided)
            mTitle.setVisibility(View.GONE);
        else mTitle.setVisibility(View.VISIBLE);
    }


    /**
     * ??????????????????
     *
     * @param widgets ??????
     * @param color   ??????
     */
    void setTextColor(@WidgetTypes int widgets, ColorStateList color) {
        TextView mText = getTextViewByWidgetType(widgets);
        if (mText != null)
            mText.setTextColor(color);

    }


    /**
     * ???????????????????????????
     *
     * @param widgets ??????
     * @param color   ??????
     */
    void setButtonBgColor(@WidgetTypes int widgets, ColorStateList color) {
        TextView button = getTextViewByWidgetType(widgets);
        if (button != null)
            setButtonColor(button, color);

    }

    /**
     * ??????Icon
     *
     * @param isHide ????????????
     */
    void hideIcon(boolean isHide) {
        if (isHide) {
            mIcon.setVisibility(View.GONE);
        } else mIcon.setVisibility(View.VISIBLE);
    }


    /**
     * ????????????????????????
     *
     * @param button         ??????
     * @param colorStateList ??????
     */
    private void setButtonColor(TextView button, ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setBackgroundTintList(colorStateList);
            button.setBackgroundTintMode(PorterDuff.Mode.SRC);
        } else {
            ViewCompat.setBackgroundTintList(button, colorStateList);
            ViewCompat.setBackgroundTintMode(button, PorterDuff.Mode.SRC);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * ??????Fragment ????????? onViewCreated?????????
     */
    interface OnFragmentCreatedListener {
        void onFragmentCreated();
    }
}
