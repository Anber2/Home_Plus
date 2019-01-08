package com.mawaqaa.homepluskw_new.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.constants.AppConstants;

public class VideoActivity extends YouTubeFailureRecoveryActivity {

	private String strVideoId;
	//private String videoTitleString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.fragment_play_youtube_video);
		//TextView videoTitle = (TextView) findViewById(R.id.video_title);
		//videoTitle.setSelected(true);
		YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
				.findFragmentById(R.id.youtubeVideoContainer);
		youTubePlayerFragment.initialize(AppConstants.GOOGLE_API, this);
		// extractYoutubeId(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null
				&& bundle.getString(AppConstants.VIDEO_URL) != null) {
			strVideoId = bundle.getString(AppConstants.VIDEO_URL);
			 //videoTitleString = bundle.getString("title");
			//videoTitle.setText(videoTitleString);
		} else {
			Toast.makeText(getApplicationContext(), "Not a valid url",
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			player.cueVideo(strVideoId);
		}
	}
	@Override
	protected YouTubePlayer.Provider getYouTubePlayerProvider() {
		return (YouTubePlayerFragment) getFragmentManager().findFragmentById(
				R.id.youtubeVideoContainer);
	}
}
