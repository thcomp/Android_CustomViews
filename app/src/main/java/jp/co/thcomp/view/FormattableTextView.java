package jp.co.thcomp.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * <FormattableTextView
 *      formatText="free word: %1$s and free number %2$d"
 *      text_param_001="@string/XXXX"
 *      int_param_002="@integer/YYYY"
 * />
 */
public class FormattableTextView extends TextView {
    private static final int AttributeIndexText = 0;
    private static final int AttributeIndexInteger = 1;
    private static final int AttributeIndexFloat = 2;
    private static final int AttributeIndexDimension = 3;
    private static final int AttributeIndexColor = 4;
    private static final int AttributeResourceArray[][] = {
            {R.styleable.FormattableTextView_Evl_TextParam001, R.styleable.FormattableTextView_Evl_IntParam001, R.styleable.FormattableTextView_Evl_FloatParam001, R.styleable.FormattableTextView_Evl_DimenParam001, R.styleable.FormattableTextView_Evl_ColorParam001},
            {R.styleable.FormattableTextView_Evl_TextParam002, R.styleable.FormattableTextView_Evl_IntParam002, R.styleable.FormattableTextView_Evl_FloatParam002, R.styleable.FormattableTextView_Evl_DimenParam002, R.styleable.FormattableTextView_Evl_ColorParam002},
            {R.styleable.FormattableTextView_Evl_TextParam003, R.styleable.FormattableTextView_Evl_IntParam003, R.styleable.FormattableTextView_Evl_FloatParam003, R.styleable.FormattableTextView_Evl_DimenParam003, R.styleable.FormattableTextView_Evl_ColorParam003},
            {R.styleable.FormattableTextView_Evl_TextParam004, R.styleable.FormattableTextView_Evl_IntParam004, R.styleable.FormattableTextView_Evl_FloatParam004, R.styleable.FormattableTextView_Evl_DimenParam004, R.styleable.FormattableTextView_Evl_ColorParam004},
            {R.styleable.FormattableTextView_Evl_TextParam005, R.styleable.FormattableTextView_Evl_IntParam005, R.styleable.FormattableTextView_Evl_FloatParam005, R.styleable.FormattableTextView_Evl_DimenParam005, R.styleable.FormattableTextView_Evl_ColorParam005},
            {R.styleable.FormattableTextView_Evl_TextParam006, R.styleable.FormattableTextView_Evl_IntParam006, R.styleable.FormattableTextView_Evl_FloatParam006, R.styleable.FormattableTextView_Evl_DimenParam006, R.styleable.FormattableTextView_Evl_ColorParam006},
            {R.styleable.FormattableTextView_Evl_TextParam007, R.styleable.FormattableTextView_Evl_IntParam007, R.styleable.FormattableTextView_Evl_FloatParam007, R.styleable.FormattableTextView_Evl_DimenParam007, R.styleable.FormattableTextView_Evl_ColorParam007},
            {R.styleable.FormattableTextView_Evl_TextParam008, R.styleable.FormattableTextView_Evl_IntParam008, R.styleable.FormattableTextView_Evl_FloatParam008, R.styleable.FormattableTextView_Evl_DimenParam008, R.styleable.FormattableTextView_Evl_ColorParam008},
            {R.styleable.FormattableTextView_Evl_TextParam009, R.styleable.FormattableTextView_Evl_IntParam009, R.styleable.FormattableTextView_Evl_FloatParam009, R.styleable.FormattableTextView_Evl_DimenParam009, R.styleable.FormattableTextView_Evl_ColorParam009},
            {R.styleable.FormattableTextView_Evl_TextParam010, R.styleable.FormattableTextView_Evl_IntParam010, R.styleable.FormattableTextView_Evl_FloatParam010, R.styleable.FormattableTextView_Evl_DimenParam010, R.styleable.FormattableTextView_Evl_ColorParam010},
            {R.styleable.FormattableTextView_Evl_TextParam011, R.styleable.FormattableTextView_Evl_IntParam011, R.styleable.FormattableTextView_Evl_FloatParam011, R.styleable.FormattableTextView_Evl_DimenParam011, R.styleable.FormattableTextView_Evl_ColorParam011},
            {R.styleable.FormattableTextView_Evl_TextParam012, R.styleable.FormattableTextView_Evl_IntParam012, R.styleable.FormattableTextView_Evl_FloatParam012, R.styleable.FormattableTextView_Evl_DimenParam012, R.styleable.FormattableTextView_Evl_ColorParam012},
            {R.styleable.FormattableTextView_Evl_TextParam013, R.styleable.FormattableTextView_Evl_IntParam013, R.styleable.FormattableTextView_Evl_FloatParam013, R.styleable.FormattableTextView_Evl_DimenParam013, R.styleable.FormattableTextView_Evl_ColorParam013},
            {R.styleable.FormattableTextView_Evl_TextParam014, R.styleable.FormattableTextView_Evl_IntParam014, R.styleable.FormattableTextView_Evl_FloatParam014, R.styleable.FormattableTextView_Evl_DimenParam014, R.styleable.FormattableTextView_Evl_ColorParam014},
            {R.styleable.FormattableTextView_Evl_TextParam015, R.styleable.FormattableTextView_Evl_IntParam015, R.styleable.FormattableTextView_Evl_FloatParam015, R.styleable.FormattableTextView_Evl_DimenParam015, R.styleable.FormattableTextView_Evl_ColorParam015},
            {R.styleable.FormattableTextView_Evl_TextParam016, R.styleable.FormattableTextView_Evl_IntParam016, R.styleable.FormattableTextView_Evl_FloatParam016, R.styleable.FormattableTextView_Evl_DimenParam016, R.styleable.FormattableTextView_Evl_ColorParam016},
            {R.styleable.FormattableTextView_Evl_TextParam017, R.styleable.FormattableTextView_Evl_IntParam017, R.styleable.FormattableTextView_Evl_FloatParam017, R.styleable.FormattableTextView_Evl_DimenParam017, R.styleable.FormattableTextView_Evl_ColorParam017},
            {R.styleable.FormattableTextView_Evl_TextParam018, R.styleable.FormattableTextView_Evl_IntParam018, R.styleable.FormattableTextView_Evl_FloatParam018, R.styleable.FormattableTextView_Evl_DimenParam018, R.styleable.FormattableTextView_Evl_ColorParam018},
            {R.styleable.FormattableTextView_Evl_TextParam019, R.styleable.FormattableTextView_Evl_IntParam019, R.styleable.FormattableTextView_Evl_FloatParam019, R.styleable.FormattableTextView_Evl_DimenParam019, R.styleable.FormattableTextView_Evl_ColorParam019},
            {R.styleable.FormattableTextView_Evl_TextParam020, R.styleable.FormattableTextView_Evl_IntParam020, R.styleable.FormattableTextView_Evl_FloatParam020, R.styleable.FormattableTextView_Evl_DimenParam020, R.styleable.FormattableTextView_Evl_ColorParam020},
    };

    public FormattableTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public FormattableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public FormattableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        Context context = getContext();
        Resources resources = context.getResources();
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.FormattableTextView, defStyle, 0);

        if(a.hasValue(R.styleable.FormattableTextView_Evl_FormatText)){
            String formatText = a.getString(R.styleable.FormattableTextView_Evl_FormatText);
            ArrayList<Object> paramList = new ArrayList<Object>();

            for(int i=0, sizeI=AttributeResourceArray.length; i<sizeI; i++){
                for(int j=0, sizeJ=AttributeResourceArray[i].length; j<sizeJ; j++){
                    if(a.hasValue(AttributeResourceArray[i][j])){
                        switch (j){
                            case AttributeIndexText:
                                paramList.add(a.getString(AttributeResourceArray[i][j]));
                                break;
                            case AttributeIndexInteger:
                                paramList.add(a.getInt(AttributeResourceArray[i][j], 0));
                                break;
                            case AttributeIndexFloat:
                                paramList.add(a.getFloat(AttributeResourceArray[i][j], 0f));
                                break;
                            case AttributeIndexDimension:
                                paramList.add(a.getDimension(AttributeResourceArray[i][j], 0));
                                break;
                            case AttributeIndexColor:
                                paramList.add(a.getColor(AttributeResourceArray[i][j], 0));
                                break;
                        }

                        break;
                    }

                }
            }

            this.setText(String.format(formatText, paramList.toArray(new Object[paramList.size()])));
        }

        a.recycle();
    }
}
