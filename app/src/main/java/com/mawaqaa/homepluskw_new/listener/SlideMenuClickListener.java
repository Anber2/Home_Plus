package com.mawaqaa.homepluskw_new.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.fragments.AboutUsFragment;
import com.mawaqaa.homepluskw_new.fragments.ContactusFragment;
import com.mawaqaa.homepluskw_new.fragments.LoginFragment;
import com.mawaqaa.homepluskw_new.fragments.MediaCenterPhotoGalleryFragment;
import com.mawaqaa.homepluskw_new.fragments.MyAccountFragment;
import com.mawaqaa.homepluskw_new.interfaces.DrawerPopUpListener;
import com.mawaqaa.homepluskw_new.interfaces.LanguageSwitcherListener;
import com.mawaqaa.homepluskw_new.utility.DrawerUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;


public class SlideMenuClickListener implements ListView.OnItemClickListener {
    Context context;
    DrawerLayout mDrawerLayout;
    ImageButton imgMore;
    ListView drawrList;

    int[] popUpContents;
    View popupView;
    PopupWindow popupWindow;
    View locationInDrawer;

    DrawerPopUpListener mPopUpListener;
    LanguageSwitcherListener mLanguageSwitcher;

    //String isLoggedUser;

    public SlideMenuClickListener(Context context, DrawerLayout mDrawerLayout, ImageButton imgMore, DrawerPopUpListener mPopUpListener, LanguageSwitcherListener mLanguageSwitcher, ListView drawrList) {
        this.context = context;
        this.mDrawerLayout = mDrawerLayout;
        this.imgMore = imgMore;
        this.mPopUpListener = mPopUpListener;
        this.mLanguageSwitcher = mLanguageSwitcher;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        switch (position) {
            case 0:
                DrawerUtils.closeDrawerVeiw(context, mDrawerLayout, imgMore);
                //((HomePlusMainActivity) HomePlusBaseActivity.getHpBaseActivity())).setLanguageSwitcher();
            /*Fragment aboutus = new AboutUsFragment();
			TabDealBaseActivity.getTabDealBaseActivity().pushFragments(aboutus,
					false, true, aboutus.getClass().getName());*/
                break;
            case 1:
                DrawerUtils.closeDrawerVeiw(context, mDrawerLayout, imgMore);
                try {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomePlusBaseActivity.getHpBaseActivity());
                    // set title
                    alertDialogBuilder.setTitle(R.string.language);

                    // set dialog message
                    alertDialogBuilder
                            .setMessage(R.string.language_flip)
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            mLanguageSwitcher.doLanguageSwitch();
                                            // do whatever you want to do when user
                                            // clicks ok
										/*PreferenceUtil.setKeepMeSignedIn(context, false);
										Toast.makeText(HomePlusBaseActivity.getHpBaseActivity(), context.getString(R.string.alert_log_out),
												Toast.LENGTH_SHORT).show();
										HomePlusBaseActivity.getHpBaseActivity().clearAllBackStackEntries();

										Fragment loginFragment = new LoginFragment();
										HomePlusBaseActivity.getHpBaseActivity().pushFragments(loginFragment, false, true);*/
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //mLanguageSwitcher.doLanguageSwitch();
                break;
            case 2:
                DrawerUtils.closeDrawerVeiw(context, mDrawerLayout, imgMore);
                HomePlusBaseActivity.getHpBaseActivity().pushFragments(new AboutUsFragment(), false, true);
                break;
            case 3:
                DrawerUtils.closeDrawerVeiw(context, mDrawerLayout, imgMore);
                Log.e("Slide menu", "" + PreferenceUtil.isUserSignedIn(context));
                //if(PreferenceUtil.isUserSignedIn(context))
                if (PreferenceUtil.isKeepMeSignedIn(context) || PreferenceUtil.isUserSignedIn(context)) {
                    HomePlusBaseActivity.getHpBaseActivity().pushFragments(new MyAccountFragment(), false, true);

                } else
                    HomePlusBaseActivity.getHpBaseActivity().pushFragments(LoginFragment.newInstance(false), false, true);
                break;
            case 4:
                DrawerUtils.closeDrawerVeiw(context, mDrawerLayout, imgMore);
                HomePlusBaseActivity.getHpBaseActivity().pushFragments(new MediaCenterPhotoGalleryFragment(), false, true);
                break;
            case 5:
                locationInDrawer = view;

                mPopUpListener.onDrawerItemClick(view, position);
                //showPopup();
                break;
            case 6:
                DrawerUtils.closeDrawerVeiw(context, mDrawerLayout, imgMore);
                HomePlusBaseActivity.getHpBaseActivity().pushFragments(new ContactusFragment(), false, true);
                break;
            case 7:
                DrawerUtils.closeDrawerVeiw(context, mDrawerLayout, imgMore);
                try {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomePlusBaseActivity.getHpBaseActivity());
                    // set title
                    alertDialogBuilder.setTitle(R.string.logout);

                    // set dialog message
                    alertDialogBuilder
                            .setMessage(R.string.alert_confirm_logout)
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {

                                            // do whatever you want to do when user
                                            // clicks ok
                                            PreferenceUtil.setKeepMeSignedIn(context, false);
                                            Toast.makeText(HomePlusBaseActivity.getHpBaseActivity(), context.getString(R.string.alert_log_out),
                                                    Toast.LENGTH_SHORT).show();
                                            HomePlusBaseActivity.getHpBaseActivity().clearAllBackStackEntries();

                                            Fragment loginFragment = new LoginFragment();
                                            HomePlusBaseActivity.getHpBaseActivity().pushFragments(loginFragment, false, true);
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

	/*public void showPopup(){
		popupView = LayoutInflater.from(context).inflate(R.layout.layout_pop_up, null);
		popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

		mDrawerLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});


		RecyclerView recyclerView = (RecyclerView) popupView.findViewById(R.id.recyViewPopUp);
		recyclerView.setLayoutManager(new GridLayoutManager(context, 2));

		int [] popUpContents = new int[]{R.drawable.menu_1, R.drawable.menu_2, R.drawable.menu_3, R.drawable.menu_4};
		SocialMediaListAdapter adapter = new SocialMediaListAdapter(context,popUpContents);
		recyclerView.setAdapter(adapter);

		popupWindow.showAtLocation(mDrawerLayout,Gravity.RIGHT, 100, 100);

	}*/

}
