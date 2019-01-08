package com.mawaqaa.homepluskw_new.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by JijoCJ on 1/9/2017.
 */
public class FontUtils {
    public static Typeface mTypeFace;

    public final static void setHomeplusRegularFont(Context context, View view) {
        mTypeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/SEGOEUI.TTF");
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(mTypeFace);
        } else if (view instanceof EditText) {
            ((EditText) view).setTypeface(mTypeFace);
        } else if (view instanceof CheckBox) {
            ((CheckBox) view).setTypeface(mTypeFace);
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(mTypeFace);
        }
        else if (view instanceof RadioButton) {
            ((RadioButton) view).setTypeface(mTypeFace);
        }
    }

    public final static void setHomeplusBoldFont(Context context, View view) {
        mTypeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/SEGOEUIB.TTF");
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(mTypeFace);
        } else if (view instanceof EditText) {
            ((EditText) view).setTypeface(mTypeFace);
        } else if (view instanceof CheckBox) {
            ((CheckBox) view).setTypeface(mTypeFace);
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(mTypeFace);
        }
        else if (view instanceof RadioButton) {
            ((RadioButton) view).setTypeface(mTypeFace);
        }
    }
    public final static void setHomeplusItalicsFont(Context context, View view) {
        mTypeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/SEGOEUII.TTF");
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(mTypeFace);
        } else if (view instanceof EditText) {
            ((EditText) view).setTypeface(mTypeFace);
        } else if (view instanceof CheckBox) {
            ((CheckBox) view).setTypeface(mTypeFace);
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(mTypeFace);
        }
        else if (view instanceof RadioButton) {
            ((RadioButton) view).setTypeface(mTypeFace);
        }
    }
    public final static void setHomeplusArabicRegularFont(Context context, View view) {
        /*mTypeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/FLOW.TTF");*/
             mTypeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/NEOSANSARABIC.TTF");
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(mTypeFace);
        } else if (view instanceof EditText) {
            ((EditText) view).setTypeface(mTypeFace);
        } else if (view instanceof CheckBox) {
            ((CheckBox) view).setTypeface(mTypeFace);
        }
        else if (view instanceof Button) {
            ((Button) view).setTypeface(mTypeFace);
        }
        else if (view instanceof RadioButton) {
            ((RadioButton) view).setTypeface(mTypeFace);
        }
    }
    public static String getHtmlDatawithEnglishFont(Context context, String data) {

        String head = "<head><style type=\"text/css\">@font-face {font-family: 'SEGOEUI';src: url(\"file:///android_asset/fonts/SEGOEUI.TTF\")}body {font-family: 'SEGOEUI';font-size: medium;text-align: justify;}</style></head>";

        String htmlData = "<html>" + head + "<body>" + data + "</body></html>";
        return htmlData;
    }
    public static String getHtmlDatawithArabicFont(Context context, String data) {

        String head = "<head><style type=\"text/css\">@font-face {font-family: 'NeoSansArabic';src: url(\"file:///android_asset/fonts/NEOSANSARABIC.TTF\")}body {font-family: 'NeoSansArabic';font-size: medium;text-align: justify;}</style></head>";

        String htmlData = "<html>" + head + "<body>" + data + "</body></html>";
        return htmlData;
    }

}
