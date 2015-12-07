package com.sisa.tabata.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareButton;
import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.changelog.provider.ChangeLogDialogProvider;
import com.sisa.tabata.media.service.MediaPlayerService;
import com.sisa.tabata.observer.VolumeChangeContentObserver;
import com.sisa.tabata.preferences.PreferenceKeys;
import com.sisa.tabata.preferences.PreferencesSource;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;
import com.sisa.tabata.socialshare.facebook.callback.FacebookAnalyticsCallback;
import com.sisa.tabata.socialshare.facebook.provider.FacebookShareLinkContentProvider;
import com.sisa.tabata.ui.adapter.SpinnerArrayAdapterFactory;
import com.sisa.tabata.ui.listener.workout.AboutButtonClickListener;
import com.sisa.tabata.ui.listener.workout.BackButtonClickCountListener;
import com.sisa.tabata.ui.listener.workout.PlayButtonClickListener;
import com.sisa.tabata.ui.listener.workout.ResetButtonClickListener;
import com.sisa.tabata.ui.listener.workout.ResetButtonLongClickListener;
import com.sisa.tabata.ui.listener.workout.TimerLayoutListener;
import com.sisa.tabata.ui.listener.workout.TweetButtonClickListener;
import com.sisa.tabata.ui.listener.workout.VolumeButtonClickListener;
import com.sisa.tabata.ui.provider.VolumeButtonImageResourceProvider;
import com.sisa.tabata.ui.timer.WorkoutCountDownTimerManager;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * Workout activity.
 *
 * @author Laszlo Sisa
 */
public class WorkoutActivity extends RoboFragmentActivity {

    @InjectView(R.id.timerLayout)
    private RelativeLayout timerLayout;
    @InjectView(R.id.playButton)
    private ImageButton playButton;
    @InjectView(R.id.resetButton)
    private ImageButton resetButton;
    @InjectView(R.id.facebookShareButton)
    private ShareButton facebookShareButton;
    @InjectView(R.id.twitterTweetButton)
    private Button twitterTweetButton;

    @InjectView(R.id.mainMenuSpinner)
    private Spinner mainMenu;
    @InjectView(R.id.volumeButton)
    private ImageButton volumeButton;
    @InjectView(R.id.iconImage)
    private ImageView iconImage;

    @Inject
    private PlayButtonClickListener playButtonClickListener;
    @Inject
    private TimerLayoutListener timerLayoutListener;
    @Inject
    private ResetButtonClickListener resetButtonClickListener;
    @Inject
    private ResetButtonLongClickListener resetButtonLongClickListener;
    @Inject
    private SpinnerArrayAdapterFactory spinnerArrayAdapterFactory;
    @Inject
    private VolumeButtonClickListener volumeButtonClickListener;
    @Inject
    private BackButtonClickCountListener backButtonClickCountListener;
    @Inject
    private AboutButtonClickListener aboutButtonClickListener;
    @Inject
    private MediaPlayerService mediaPlayerService;
    @Inject
    private ParseAnalyticsAdapter parseAnalyticsAdapter;
    @Inject
    private WorkoutCountDownTimerManager workoutCountDownTimerManager;
    @Inject
    private PreferencesSource preferencesSource;
    @Inject
    private FacebookShareLinkContentProvider facebookShareLinkContentProvider;
    @Inject
    private TweetButtonClickListener tweetButtonClickListener;
    @Inject
    private FacebookAnalyticsCallback facebookAnalyticsCallback;
    @Inject
    private VolumeChangeContentObserver volumeChangeContentObserver;
    @Inject
    private VolumeButtonImageResourceProvider volumeButtonImageResourceProvider;
    @Inject
    private ChangeLogDialogProvider changeLogDialogProvider;
    private CallbackManager callbackManager;
    private boolean shouldResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setUpMainMenu();
        setUpListeners();
        initProgressBars();
        mediaPlayerService.reset();
        parseAnalyticsAdapter.trackAppOpenedEvent();
        setUpSocialShare();
        setUpCallListener();
        initVolumeButton();
        changeLogDialogProvider.checkShowChangeLog(this);
    }

    @Override
    public void onBackPressed() {
        backButtonClickCountListener.onClick(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldResume) {
            playButtonClickListener.resumeTimer();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpMainMenu() {
        mainMenu.setAdapter(spinnerArrayAdapterFactory.create(this));
        mainMenu.setSelection(0);
    }

    private void setUpListeners() {
        timerLayout.getViewTreeObserver().addOnGlobalLayoutListener(timerLayoutListener);
        playButton.setOnClickListener(playButtonClickListener);
        resetButton.setOnClickListener(resetButtonClickListener);
        resetButton.setOnLongClickListener(resetButtonLongClickListener);
        volumeButton.setOnClickListener(volumeButtonClickListener);
        iconImage.setOnClickListener(aboutButtonClickListener);
    }

    private void initProgressBars() {
        playButtonClickListener.resetWorkout();
    }

    private void setUpSocialShare() {
        callbackManager = CallbackManager.Factory.create();
        facebookShareButton.setShareContent(facebookShareLinkContentProvider.getShareLinkContent());
        facebookShareButton.registerCallback(callbackManager, facebookAnalyticsCallback);
        twitterTweetButton.setOnClickListener(tweetButtonClickListener);
    }

    private void setUpCallListener() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new WorkoutPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void initVolumeButton() {
        volumeButtonImageResourceProvider.setVolumeImageResource(volumeButton);
        volumeChangeContentObserver.setVolumeButton(volumeButton);
    }

    public PlayButtonClickListener getPlayButtonClickListener() {
        return playButtonClickListener;
    }

    private class WorkoutPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(final int state, final String incomingNumber) {
            if (isIncomingCall(state) && shouldPauseWorkout()) {
                playButtonClickListener.pauseTimer();
                shouldResume = true;
            }
        }

        private boolean isIncomingCall(final int state) {
            return TelephonyManager.CALL_STATE_RINGING == state;
        }

        private boolean shouldPauseWorkout() {
            return isAutoPauseAllowed() && isWorkoutInProgress() && isNotPaused();
        }

        private boolean isAutoPauseAllowed() {
            return preferencesSource.is(PreferenceKeys.AUTO_PAUSE_ON_CALL);
        }

        private boolean isNotPaused() {
            return !workoutCountDownTimerManager.isPaused();
        }

        private boolean isWorkoutInProgress() {
            return workoutCountDownTimerManager.isWorkoutInProgress();
        }
    }

}
