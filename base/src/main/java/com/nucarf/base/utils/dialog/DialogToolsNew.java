package com.nucarf.base.utils.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;

import com.nucarf.base.R;
import com.nucarf.base.utils.ScreenUtil;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * 就是自定义的Dialog,不可back或点击外部销毁
 */
public class DialogToolsNew extends DialogBase implements View.OnClickListener {

    private final Handler handler;

    @SuppressLint("InflateParams")
    protected DialogToolsNew(Builder builder) {
        super(builder.context, R.style.DialogStyle2);
        handler = new Handler();
        this.builder = builder;
        final LayoutInflater inflater = LayoutInflater.from(builder.context);
        view = (ViewGroup) inflater.inflate(R.layout.dialog_select_three_btn, null);
        init(this);
    }

    private void init(DialogToolsNew dialog) {
        DialogToolsNew.Builder builder = dialog.builder;

        // Set cancelable flag and dialog background color
        dialog.setCancelable(builder.cancelable);
        dialog.setCanceledOnTouchOutside(builder.canceledOnTouchOutside);
        if (builder.backgroundColor != 0) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(
                    builder.context.getResources().getDimension(R.dimen.md_bg_corner_radius));
            drawable.setColor(builder.backgroundColor);
            dialog.getWindow().setBackgroundDrawable(drawable);
        }
        if (builder.backgroundColor == 0) {
            builder.backgroundColor = builder.context.getResources().getColor(R.color.white);
        }
        if (builder.backgroundColor != 0) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(
                    builder.context.getResources().getDimension(R.dimen.md_bg_corner_radius));
            drawable.setColor(builder.backgroundColor);
            dialog.getWindow().setBackgroundDrawable(drawable);
        }

        // Retrieve color theme attributes
        if (!builder.positiveColorSet) {
            builder.positiveColor = DialogNewUtils.getActionTextStateList(builder.context, builder.context.getResources().getColor(R.color.color_333333));
        }
        if (!builder.neutralColorSet) {
            builder.neutralColor = DialogNewUtils.getActionTextStateList(builder.context, builder.context.getResources().getColor(R.color.color_333333));
            ;
        }
        if (!builder.negativeColorSet) {
            builder.negativeColor = DialogNewUtils.getActionTextStateList(builder.context, builder.context.getResources().getColor(R.color.color_333333));
            ;
        }

        // Retrieve default title/content colors
        if (!builder.titleColorSet) {
            final int titleColorFallback =
                    DialogNewUtils.resolveColor(dialog.getContext(), android.R.attr.textColorPrimary);
            builder.titleColor =
                    DialogNewUtils.resolveColor(builder.context, R.attr.md_title_color, titleColorFallback);
        }
        if (!builder.contentColorSet) {
            final int contentColorFallback =
                    DialogNewUtils.resolveColor(dialog.getContext(), android.R.attr.textColorSecondary);
            builder.contentColor =
                    DialogNewUtils.resolveColor(builder.context, R.attr.md_content_color, contentColorFallback);
        }
        // Retrieve references to views
        dialog.title = dialog.view.findViewById(R.id.dialog_title_tv);
        dialog.icon = dialog.view.findViewById(R.id.dialog_icon_iv);
        dialog.content = dialog.view.findViewById(R.id.content_tv);
//        dialog.recyclerView = dialog.view.findViewById(R.id.md_contentRecyclerView);

        // Button views initially used by checkIfStackingNeeded()
        dialog.positiveButton = dialog.view.findViewById(R.id.left_tv);
        dialog.neutralButton = dialog.view.findViewById(R.id.center_btn_tv);
        dialog.negativeButton = dialog.view.findViewById(R.id.right_tv);
        View view_line1 = dialog.view.findViewById(R.id.view_line1);
        View view_line2 = dialog.view.findViewById(R.id.view_line2);
        // Set up the initial visibility of action buttons based on whether or not text was set
        dialog.positiveButton.setVisibility(builder.positiveText != null ? View.VISIBLE : View.GONE);
        dialog.neutralButton.setVisibility(builder.neutralText != null ? View.VISIBLE : View.GONE);
        dialog.negativeButton.setVisibility(builder.negativeText != null ? View.VISIBLE : View.GONE);
// Setup title and title frame
        if (dialog.title != null) {
            dialog.title.setTextColor(builder.titleColor);
            dialog.title.setGravity(builder.titleGravity.getGravityInt());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //noinspection ResourceType
                dialog.title.setTextAlignment(builder.titleGravity.getTextAlignment());
            }

            if (builder.title == null) {
                dialog.title.setVisibility(View.GONE);
            } else {
                dialog.title.setText(builder.title);
                dialog.title.setVisibility(View.VISIBLE);
            }
        }
        // Setup content
        if (dialog.content != null) {
            dialog.content.setMovementMethod(new LinkMovementMethod());
            dialog.content.setLineSpacing(0f, builder.contentLineSpacingMultiplier);
            dialog.content.setTextColor(builder.contentColor);
            dialog.content.setGravity(builder.contentGravity.getGravityInt());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //noinspection ResourceType
                dialog.content.setTextAlignment(builder.contentGravity.getTextAlignment());
            }
            if (builder.content != null) {
                dialog.content.setText(builder.content);
                dialog.content.setVisibility(View.VISIBLE);
            } else {
                dialog.content.setVisibility(View.GONE);
            }
        }

        TextView positiveTextView = dialog.positiveButton;
        if (builder.positiveText != null) {
            positiveTextView.setText(builder.positiveText);
            positiveTextView.setTextColor(builder.positiveColor);
            if (builder.neutralText == null) {
                view_line1.setVisibility(View.GONE);
            }
        }
        dialog.positiveButton.setTag(DialogAction.POSITIVE);
        dialog.positiveButton.setOnClickListener(dialog);

        TextView negativeTextView = dialog.negativeButton;
        if (builder.negativeText != null) {
            negativeTextView.setText(builder.negativeText);
            negativeTextView.setTextColor(builder.negativeColor);
        }
        dialog.negativeButton.setTag(DialogAction.NEGATIVE);
        dialog.negativeButton.setOnClickListener(dialog);

        TextView neutralTextView = dialog.neutralButton;
        if (builder.neutralColor != null) {
            neutralTextView.setText(builder.neutralText);
            neutralTextView.setTextColor(builder.neutralColor);
            view_line2.setVisibility(View.VISIBLE);
        } else {
            view_line2.setVisibility(View.GONE);
        }
        dialog.neutralButton.setTag(DialogAction.NEUTRAL);
        dialog.neutralButton.setOnClickListener(dialog);

        // Setup user listeners
        if (builder.showListener != null) {
            dialog.setOnShowListener(builder.showListener);
        }
        if (builder.cancelListener != null) {
            dialog.setOnCancelListener(builder.cancelListener);
        }
        if (builder.dismissListener != null) {
            dialog.setOnDismissListener(builder.dismissListener);
        }
        if (builder.keyListener != null) {
            dialog.setOnKeyListener(builder.keyListener);
        }

        // Setup internal show listener
        dialog.setOnShowListenerInternal();

        // Other internal initialization
        dialog.setViewInternal(dialog.view);

        // Min height and max width calculations
        WindowManager wm = dialog.getWindow().getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int windowWidth = size.x;
        final int windowHeight = size.y;

        final int windowVerticalPadding =
                builder.context.getResources().getDimensionPixelSize(R.dimen.md_dialog_vertical_margin);
        final int windowHorizontalPadding =
                builder.context.getResources().getDimensionPixelSize(R.dimen.md_dialog_horizontal_margin);
        final int maxWidth =
                builder.context.getResources().getDimensionPixelSize(R.dimen.md_dialog_max_width);
        final int calculatedWidth = windowWidth - (windowHorizontalPadding * 2);

//        dialog.view.setMaxHeight(windowHeight - windowVerticalPadding * 2);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = Math.min(maxWidth, calculatedWidth);
        dialog.getWindow().setAttributes(lp);
    }

    public final Builder getBuilder() {
        return builder;
    }


    @Override
    public final void onClick(View v) {
        DialogAction tag = (DialogAction) v.getTag();
        switch (tag) {
            case POSITIVE: {
                if (builder.callback != null) {
                    builder.callback.onAny(this);
                    builder.callback.onPositive(this);
                }
                if (builder.onPositiveCallback != null) {
                    builder.onPositiveCallback.onClick(this, tag);
                }
//                if (!builder.alwaysCallSingleChoiceCallback) {
//                    sendSingleChoiceCallback(v);
//                }
//                if (!builder.alwaysCallMultiChoiceCallback) {
//                    sendMultiChoiceCallback();
//                }
                if (builder.autoDismiss) {
                    dismiss();
                }
                break;
            }
            case NEGATIVE: {
                if (builder.callback != null) {
                    builder.callback.onAny(this);
                    builder.callback.onNegative(this);
                }
                if (builder.onNegativeCallback != null) {
                    builder.onNegativeCallback.onClick(this, tag);
                }
                if (builder.autoDismiss) {
                    cancel();
                }
                break;
            }
            case NEUTRAL: {
                if (builder.callback != null) {
                    builder.callback.onAny(this);
                    builder.callback.onNeutral(this);
                }
                if (builder.onNeutralCallback != null) {
                    builder.onNeutralCallback.onClick(this, tag);
                }
                if (builder.autoDismiss) {
                    dismiss();
                }
                break;
            }
        }
        if (builder.onAnyCallback != null) {
            builder.onAnyCallback.onClick(this, tag);
        }
    }

    @Override
    @UiThread
    public void show() {
        try {
            super.show();
        } catch (WindowManager.BadTokenException e) {
            try {
                throw new Exception(
                        "Bad window token, you cannot show a dialog "
                                + "before an Activity is created or after it's hidden.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * An alternate way to define a single callback.
     */
    public interface SingleButtonCallback {

        void onClick(@NonNull DialogToolsNew dialog, @NonNull DialogAction which);
    }

    protected final Builder builder;
    protected ImageView icon;
    protected TextView title;
    protected TextView content;
    RecyclerView recyclerView;
    FrameLayout customViewFrame;
    TextView positiveButton;
    TextView neutralButton;
    TextView negativeButton;

    public static class Builder {

        protected final Context context;
        protected CharSequence title;
        protected GravityEnum titleGravity = GravityEnum.CENTER;
        protected GravityEnum contentGravity = GravityEnum.START;
        protected int titleColor = -1;
        protected int contentColor = -1;
        protected CharSequence content;
        protected ArrayList<CharSequence> items;
        protected CharSequence positiveText;
        protected CharSequence neutralText;
        protected CharSequence negativeText;
        protected View customView;
        protected ColorStateList positiveColor;
        protected ColorStateList negativeColor;
        protected ColorStateList neutralColor;
        protected ButtonCallback callback;
        protected SingleButtonCallback onPositiveCallback;
        protected SingleButtonCallback onNegativeCallback;
        protected SingleButtonCallback onNeutralCallback;
        protected SingleButtonCallback onAnyCallback;
        protected boolean alwaysCallMultiChoiceCallback = false;
        protected boolean alwaysCallSingleChoiceCallback = false;
        protected boolean cancelable = true;
        protected boolean canceledOnTouchOutside = true;
        protected float contentLineSpacingMultiplier = 1.2f;
        protected int selectedIndex = -1;
        protected boolean autoDismiss = true;
        protected Drawable icon;
        protected RecyclerView.Adapter<?> adapter;
        protected RecyclerView.LayoutManager layoutManager;
        protected DialogInterface.OnDismissListener dismissListener;
        protected DialogInterface.OnCancelListener cancelListener;
        protected DialogInterface.OnKeyListener keyListener;
        protected DialogInterface.OnShowListener showListener;
        protected int backgroundColor;
        protected int itemColor;
        protected boolean titleColorSet = false;
        protected boolean contentColorSet = false;
        protected boolean itemColorSet = false;
        protected boolean positiveColorSet = false;
        protected boolean neutralColorSet = false;
        protected boolean negativeColorSet = false;
        protected boolean dividerColorSet = false;

        protected Object tag;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder title(@StringRes int titleRes) {
            title(this.context.getText(titleRes));
            return this;
        }

        public Builder title(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder titleGravity(@NonNull GravityEnum gravity) {
            this.titleGravity = gravity;
            return this;
        }

        public Builder titleColor(@ColorInt int color) {
            this.titleColor = color;
            this.titleColorSet = true;
            return this;
        }

        public Builder titleColorRes(@ColorRes int colorRes) {
            return titleColor(DialogNewUtils.getColor(this.context, colorRes));
        }

        public Builder content(@StringRes int contentRes) {
            return content(contentRes, false);
        }

        public Builder content(@StringRes int contentRes, boolean html) {
            CharSequence text = this.context.getText(contentRes);
            if (html) {
                text = Html.fromHtml(text.toString().replace("\n", "<br/>"));
            }
            return content(text);
        }

        public Builder content(@NonNull CharSequence content) {
            if (this.customView != null) {
                throw new IllegalStateException(
                        "You cannot set content() " + "when you're using a custom view.");
            }
            this.content = content;
            return this;
        }

        public Builder content(@StringRes int contentRes, Object... formatArgs) {
            String str =
                    String.format(this.context.getString(contentRes), formatArgs).replace("\n", "<br/>");
            //noinspection deprecation
            return content(Html.fromHtml(str));
        }

        public Builder contentColor(@ColorInt int color) {
            this.contentColor = color;
            this.contentColorSet = true;
            return this;
        }

        public Builder contentColorRes(@ColorRes int colorRes) {
            contentColor(DialogNewUtils.getColor(this.context, colorRes));
            return this;
        }

        public Builder contentColorAttr(@AttrRes int colorAttr) {
            contentColor(DialogNewUtils.resolveColor(this.context, colorAttr));
            return this;
        }

        public Builder contentGravity(@NonNull GravityEnum gravity) {
            this.contentGravity = gravity;
            return this;
        }

        public Builder contentLineSpacing(float multiplier) {
            this.contentLineSpacingMultiplier = multiplier;
            return this;
        }

        public Builder positiveText(@StringRes int positiveRes) {
            if (positiveRes == 0) {
                return this;
            }
            positiveText(this.context.getText(positiveRes));
            return this;
        }

        public Builder positiveText(@NonNull CharSequence message) {
            this.positiveText = message;
            return this;
        }

        public Builder positiveColor(@ColorInt int color) {
            return positiveColor(DialogNewUtils.getActionTextStateList(context, color));
        }

        public Builder positiveColorRes(@ColorRes int colorRes) {
            return positiveColor(DialogNewUtils.getActionTextColorStateList(this.context, colorRes));
        }

        public Builder positiveColorAttr(@AttrRes int colorAttr) {
            return positiveColor(
                    DialogNewUtils.resolveActionTextColorStateList(this.context, colorAttr, null));
        }

        public Builder positiveColor(@NonNull ColorStateList colorStateList) {
            this.positiveColor = colorStateList;
            this.positiveColorSet = true;
            return this;
        }


        public Builder neutralText(@StringRes int neutralRes) {
            if (neutralRes == 0) {
                return this;
            }
            return neutralText(this.context.getText(neutralRes));
        }

        public Builder neutralText(@NonNull CharSequence message) {
            this.neutralText = message;
            return this;
        }

        public Builder negativeColor(@ColorInt int color) {
            return negativeColor(DialogNewUtils.getActionTextStateList(context, color));
        }

        public Builder negativeColorRes(@ColorRes int colorRes) {
            return negativeColor(DialogNewUtils.getActionTextColorStateList(this.context, colorRes));
        }

        public Builder negativeColorAttr(@AttrRes int colorAttr) {
            return negativeColor(
                    DialogNewUtils.resolveActionTextColorStateList(this.context, colorAttr, null));
        }

        public Builder negativeColor(@NonNull ColorStateList colorStateList) {
            this.negativeColor = colorStateList;
            this.negativeColorSet = true;
            return this;
        }

        public Builder negativeText(@StringRes int negativeRes) {
            if (negativeRes == 0) {
                return this;
            }
            return negativeText(this.context.getText(negativeRes));
        }

        public Builder negativeText(@NonNull CharSequence message) {
            this.negativeText = message;
            return this;
        }

        public Builder neutralColor(@ColorInt int color) {
            return neutralColor(DialogNewUtils.getActionTextStateList(context, color));
        }

        public Builder neutralColorRes(@ColorRes int colorRes) {
            return neutralColor(DialogNewUtils.getActionTextColorStateList(this.context, colorRes));
        }

        public Builder neutralColorAttr(@AttrRes int colorAttr) {
            return neutralColor(
                    DialogNewUtils.resolveActionTextColorStateList(this.context, colorAttr, null));
        }

        public Builder neutralColor(@NonNull ColorStateList colorStateList) {
            this.neutralColor = colorStateList;
            this.neutralColorSet = true;
            return this;
        }

        public Builder customView(@LayoutRes int layoutRes) {
            LayoutInflater li = LayoutInflater.from(this.context);
            return customView(li.inflate(layoutRes, null));
        }

        public Builder customView(@NonNull View view) {
            if (this.content != null) {
                throw new IllegalStateException("You cannot use customView() when you have content set.");
            } else if (this.items != null) {
                throw new IllegalStateException("You cannot use customView() when you have items set.");
            }
            if (view.getParent() != null && view.getParent() instanceof ViewGroup) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            this.customView = view;
            return this;
        }

        public Builder backgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            return this;
        }

        public Builder backgroundColorRes(@ColorRes int colorRes) {
            return backgroundColor(DialogNewUtils.getColor(this.context, colorRes));
        }

        public Builder backgroundColorAttr(@AttrRes int colorAttr) {
            return backgroundColor(DialogNewUtils.resolveColor(this.context, colorAttr));
        }

        public Builder callback(@NonNull ButtonCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder onPositive(@NonNull SingleButtonCallback callback) {
            this.onPositiveCallback = callback;
            return this;
        }

        public Builder onNegative(@NonNull SingleButtonCallback callback) {
            this.onNegativeCallback = callback;
            return this;
        }

        public Builder onNeutral(@NonNull SingleButtonCallback callback) {
            this.onNeutralCallback = callback;
            return this;
        }

        public Builder onAny(@NonNull SingleButtonCallback callback) {
            this.onAnyCallback = callback;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            this.canceledOnTouchOutside = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        /**
         * This defaults to true. If set to false, the dialog will not automatically be dismissed when
         * an action button is pressed, and not automatically dismissed when the user selects a list
         * item.
         *
         * @param dismiss Whether or not to dismiss the dialog automatically.
         * @return The Builder instance so you can chain calls to it.
         */
        public Builder autoDismiss(boolean dismiss) {
            this.autoDismiss = dismiss;
            return this;
        }

        public Builder showListener(@NonNull OnShowListener listener) {
            this.showListener = listener;
            return this;
        }

        public Builder dismissListener(@NonNull OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public Builder cancelListener(@NonNull OnCancelListener listener) {
            this.cancelListener = listener;
            return this;
        }

        public Builder keyListener(@NonNull OnKeyListener listener) {
            this.keyListener = listener;
            return this;
        }

        public Builder tag(@Nullable Object tag) {
            this.tag = tag;
            return this;
        }

        @UiThread
        public DialogToolsNew build() {
            return new DialogToolsNew(this);
        }

        @UiThread
        public DialogToolsNew show() {
            DialogToolsNew dialog = build();
            dialog.show();
            return dialog;
        }
    }

    /**
     * 自定义Dialog内容
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog showCustomBottomDialog(Context context, View view, boolean isoutside) {
        Dialog dialog = new Dialog(context, R.style.customMainDialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        //   设置dialog距屏幕的边距都为0
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        return dialog;
    }

    /**
     * 单个按钮弹框
     *
     * @param context
     * @param title
     * @param content
     * @param dialogClickListener
     * @return
     */
    public static Dialog showOneBtnDialog(Context context, String title, String content, String btnString, final DialogClickListener dialogClickListener) {
        return showDialog3Btn(context, true, title, content, btnString, "", "", dialogClickListener, false);
    }

    /**
     * 两个按钮弹框
     *
     * @param context
     * @param title
     * @param content
     * @param dialogClickListener
     * @return
     */
    public static Dialog showTwoBtnDialog(Context context, String title, String content, String leftBtnString, String rightBtnString, final DialogClickListener dialogClickListener) {
        return showDialog3Btn(context, true, title, content, leftBtnString, "", rightBtnString, dialogClickListener, false);
    }

    /**
     * 两个按钮弹框
     *
     * @param context
     * @param title
     * @param content
     * @param dialogClickListener
     * @return
     */
    public static Dialog showThreeBtnDialog(Context context, String title, String content, String leftBtnString, String centerBtnString, String rightBtnString, final DialogClickListener dialogClickListener) {
        return showDialog3Btn(context, true, title, content, leftBtnString, centerBtnString, rightBtnString, dialogClickListener, false);
    }

    //内容多选对话框(样式不同)
    public static Dialog showListDialogDefind(Context context, String title, ArrayList<String> items, int pMaxHeight, final DialogItemClickListener2 dialogItemClickListener) {
        return ShowListItemDialogDefind(context, title, items, pMaxHeight, dialogItemClickListener);
    }

    public static Dialog showDialog3Btn(Context context, boolean cancel, String title, String content, String pLeftBtnStr, String pcenterBtnStr, String pRightBtnStr, final DialogClickListener dialogClickListener, boolean isSystem) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle2);
        dialog.setCancelable(cancel);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_select_three_btn, null);
        dialog.setContentView(view);
        LinearLayout mSelectLayout = (LinearLayout) view.findViewById(R.id.select_layout);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title_tv);
        TextView mContent = (TextView) view.findViewById(R.id.content_tv);
        TextView mLeftBtn = (TextView) view.findViewById(R.id.left_tv);
        TextView mCenterBtn = (TextView) view.findViewById(R.id.center_btn_tv);
        TextView mRightBtn = (TextView) view.findViewById(R.id.right_tv);
        View view_line1 = view.findViewById(R.id.view_line1);
        View view_line2 = view.findViewById(R.id.view_line2);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        } else {
            mTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(pLeftBtnStr)) {
            mLeftBtn.setVisibility(View.VISIBLE);
            mLeftBtn.setText(pLeftBtnStr);
            if (TextUtils.isEmpty(pcenterBtnStr)) {
                view_line1.setVisibility(View.GONE);
            }
        } else {
            mLeftBtn.setVisibility(View.GONE);
            mLeftBtn.setText("");
        }
        if (!TextUtils.isEmpty(pRightBtnStr)) {
            mRightBtn.setVisibility(View.VISIBLE);
            mRightBtn.setText(pRightBtnStr);
        } else {
            mRightBtn.setVisibility(View.GONE);
            mRightBtn.setText("");
        }
        if (!TextUtils.isEmpty(pcenterBtnStr)) {
            mCenterBtn.setVisibility(View.VISIBLE);
            mCenterBtn.setText(pcenterBtnStr);
            view_line2.setVisibility(View.VISIBLE);
        } else {
            mCenterBtn.setVisibility(View.GONE);
            view_line2.setVisibility(View.GONE);
            mCenterBtn.setText("");
        }
        mContent.setText(content);
        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.cancel();
                    }
                }, 200);
            }
        });
        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.confirm();
                    }
                }, 200);
            }
        });
        mCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.centerBtn();
                    }
                }, 200);
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = ScreenUtil.getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = ScreenUtil.getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        if (isSystem) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
        return dialog;
    }


    private static Dialog ShowListItemDialogDefind(final Context context, String title, final ArrayList<String> items, int pMaxHeight, final DialogItemClickListener2 dialogClickListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle2);
        return dialog;
    }

    public interface DialogClickListener {
        public abstract void confirm();

        public abstract void cancel();

        public abstract void centerBtn();
    }

    public interface DialogItemClickListener {
        public abstract void confirm(String result);
    }

    public interface DialogItemClickListener2 {
        public abstract void confirm(String result, int position);
    }


    /**
     * Override these as needed, so no needing to sub empty methods from an interface
     *
     * @deprecated Use the individual onPositive, onNegative, onNeutral, or onAny Builder methods
     * instead.
     */
    @SuppressWarnings({"WeakerAccess", "UnusedParameters"})
    @Deprecated
    public abstract static class ButtonCallback {

        public ButtonCallback() {
            super();
        }

        @Deprecated
        public void onAny(DialogToolsNew dialog) {
        }

        @Deprecated
        public void onPositive(DialogToolsNew dialog) {
        }

        @Deprecated
        public void onNegative(DialogToolsNew dialog) {
        }

        // The overidden methods below prevent Android Studio from suggesting that they are overidden by developers

        @Deprecated
        public void onNeutral(DialogToolsNew dialog) {
        }

        @Override
        protected final Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public final boolean equals(Object o) {
            return super.equals(o);
        }

        @Override
        protected final void finalize() throws Throwable {
            super.finalize();
        }

        @Override
        public final int hashCode() {
            return super.hashCode();
        }

        @Override
        public final String toString() {
            return super.toString();
        }
    }
}