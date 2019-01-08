package com.mawaqaa.homepluskw_new.fragments;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TabWidget;


import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.SocialmediaData;
import com.mawaqaa.homepluskw_new.utility.DrawerUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SocialMediaFragment extends DialogFragment
        implements OnClickListener {
    WebView webViewLoadSocialMedia;
    private String TAG = "FashionWorld::SocialMediaFragment";
    // private View source;
    //private ArrayList<MoreItem> moreItems;
    // private MoreAdapter moreAdapter;
    private ImageView fb;
    private ImageView twiter;
    private ImageView instagram;
    private ImageView linkedIn;
    ArrayList<SocialmediaData> search_list;

    public HomePlusBaseActivity Activity;
    ArrayList<SocialmediaData> mediaData_array;

    public SocialMediaFragment() {
    }

	/*public SocialMediaFragment(View source) {
        // this.source = source;
	}*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setStyle(STYLE_NO_FRAME, 0);
        Activity = (HomePlusBaseActivity) this.getActivity();

    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.2f; // dim only a little bit
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Put your dialog layout in R.layout.view_confirm_box
        View view = inflater.inflate(R.layout.fragment_social_media, container,
                false);
        //setupUI(view);
        fb = (ImageView) view.findViewById(R.id.fb);
        fb.setOnClickListener(this);
        twiter = (ImageView) view.findViewById(R.id.twitter);
        twiter.setOnClickListener(this);
        instagram = (ImageView) view.findViewById(R.id.instagram);
        instagram.setOnClickListener(this);
        linkedIn = (ImageView) view.findViewById(R.id.linkedIn);
        linkedIn.setOnClickListener(this);
        webViewLoadSocialMedia = (WebView) view.findViewById(R.id.webViewLoadSocialMedia);

        getDialog().setCanceledOnTouchOutside(true);
        setDialogPosition();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        //getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //getDialog().getWindow().getAttributes().windowAnimations = R.style.socialMediaDailogueTheme;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        DrawerUtils.dismissSocialDialog();

    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Try to position this dialog next to "source" view
     */
    private void setDialogPosition() {
        if (((HomePlusMainActivity) getActivity()).getmDrawerLayout() == null) {
            return; // Leave the dialog in default position
        }
        Window window = getDialog().getWindow();
        WindowManager wm = window.getWindowManager();
        Display mdisp = wm.getDefaultDisplay();
        Point mdispSize = new Point();
        int maxX = 0;
        if (Build.VERSION.SDK_INT >= 13) {
            mdisp.getSize(mdispSize);
            maxX = mdispSize.x;
        } else {
            maxX = mdisp.getWidth(); // deprecated
        }

        // set "origin" to top left corner
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
            window.setGravity(Gravity.LEFT);
        else
            window.setGravity(Gravity.RIGHT);

        WindowManager.LayoutParams params = window.getAttributes();

        params.x = (int) calculateWidth(maxX);
        params.y = dpToPx(180);
        //params.y = (int) calucalateHeight(rowCount);

        window.setAttributes(params);
    }

    private float getTabHeight() {
        final TabWidget tv = (TabWidget) getActivity().findViewById(
                android.R.id.tabs);
        int[] location = new int[2];
        tv.getLocationOnScreen(location);
        int sourceY = location[1];
        return sourceY;
    }

    private float calculateWidth(int maxX) {
        float dailogueWidth = dpToPx(255); // assumes listviews width is 250 //250
        float thisFragWidth = dpToPx(95); // assumes this fragment width is 105 //65
        float sourceX = maxX - (dailogueWidth + thisFragWidth);
        Log.e(TAG, "width" + sourceX);
        return sourceX;
    }

    @SuppressWarnings("unused")
    private float calucalateHeight(int rowCount) {

        float dialogueHeight = rowCount * dpToPx(56);// assumes each more items
        // height is 56
        float tabHeight = getTabHeight();
        float sourceY = tabHeight - dialogueHeight;
        Log.e(TAG, "height" + sourceY);
        return sourceY;
    }

    public int dpToPx(float valueInDp) {
        DisplayMetrics metrics = getActivity().getResources()
                .getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                valueInDp, metrics);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb:
                Log.e("fbclick", "@@@@");
                callFb();
			/*Fragment fragmentFacebook = SocialMediaUrlFragment.newInstance("facebook");
			Activity.pushFragments(fragmentFacebook, false, true);*/
                dismissAll();
                break;
            case R.id.twitter:
                callTwitter();
			/*Log.e("twitterclick","@@@@");
			Fragment fragmentTwitter = SocialMediaUrlFragment.newInstance("twitter");
			Activity.pushFragments(fragmentTwitter, false, true);*/
                dismissAll();
                break;
            case R.id.instagram:
                Log.e("instagramclick", "@@@@");
			/*Fragment fragmentInstagram = SocialMediaUrlFragment.newInstance("instagram");
			Activity.pushFragments(fragmentInstagram, false, true);*/
                callInstagram();
                dismissAll();
                break;
            case R.id.linkedIn:
                callLinkedln();
			/*Fragment fragmentLinkedln = SocialMediaUrlFragment.newInstance("linkedin");
			Activity.pushFragments(fragmentLinkedln, false, true);*/
                dismissAll();
                break;
            default:
                break;
        }
    }

    private void callLinkedln() {
        try {
            search_list = new ArrayList<>();

            Uri uri = Uri.parse("https://www.linkedin.com/homepluskw/");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));


        } catch (Exception e) {
            e.printStackTrace();// or else open in browser instead
            //String facebookUrl = "https://www.facebook.com/pages/Fashion-world/272913489542454";
            //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
        }
    }

    private void callFb() {


        try {
            search_list = new ArrayList<>();

            Uri uri = Uri.parse("https://www.facebook.com/homepluskw/");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));


        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/homepluskw/")));
            //e.printStackTrace();// or else open in browser instead
            //String facebookUrl = "https://www.facebook.com/pages/Fashion-world/272913489542454";
            //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
        }
    }

    private void callInstagram() {
        Uri uri = Uri.parse("https://www.instagram.com/homepluskw/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/homepluskw/")));
        }
    }

    private void callTwitter() {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.twitter.com/homepluskw/"));
            startActivity(intent);

        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.twitter.com/homepluskw/")));
        }

    }

    private void dismissAll() {
        dismiss();
        //DrawerUtils.closeDrawerVeiw(Activity,((HomePlusMainActivity) getActivity()).getmDrawerLayout(), );
    }

}
