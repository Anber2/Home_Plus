package com.mawaqaa.homepluskw_new.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

import java.util.Locale;

/**
 * Created by JijoCJ on 10/26/2016.
 */
public class HomePlusInitialActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton imgBtnLanguageArabic, imgBtnLanguageEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_plus_initial);
        imgBtnLanguageEnglish = (ImageButton) findViewById(R.id.imgBtnLanguageEnglish);
        FontUtils.setHomeplusBoldFont(getApplicationContext(), imgBtnLanguageEnglish);
        imgBtnLanguageArabic = (ImageButton) findViewById(R.id.imgBtnLanguageArabic);
        FontUtils.setHomeplusBoldFont(getApplicationContext(), imgBtnLanguageArabic);

        imgBtnLanguageEnglish.setOnClickListener(this);
        imgBtnLanguageArabic.setOnClickListener(this);
    }

    private void setInitialLanguage(String language) {
        Locale locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnLanguageArabic:
                setInitialLanguage(AppConstants.HP_ARABIC);
                PreferenceUtil.setLanguage(this, AppConstants.HP_ARABIC);
                goToNext();
                break;
            case R.id.imgBtnLanguageEnglish:
                setInitialLanguage(AppConstants.HP_ENGLISH);
                PreferenceUtil.setLanguage(this, AppConstants.HP_ENGLISH);
                goToNext();
                break;
            default:
                break;
        }
    }

    private void goToNext() {
        Intent intent = new Intent(this, HomePlusMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
