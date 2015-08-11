package mhyhre.lightrabbit.ads;

import android.app.Activity;
import android.os.Handler;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import mhyhre.lightrabbit.scenes.SceneRoot;
import mhyhre.lightrabbit.scenes.SceneStates;

public class ScreenAdvertisement {

    final Activity activity;
    final int advertisementId;

    final Handler adsHandler = new Handler();
    final AdView adView;
    private AdRequest request;

    public ScreenAdvertisement(final Activity activity, final int advertisementId) {
        adView = (AdView)activity.findViewById(advertisementId);
        this.activity = activity;
        this.advertisementId = advertisementId;

        request = new AdRequest.Builder().build();

        adsHandler.post(new Runnable() {
            public void run() {
                adView.setEnabled(true);
                adView.setVisibility(android.view.View.GONE);
                adView.loadAd(request);
            }
        });
    }

    //show the ads.
    public void showAds () {

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if(SceneRoot.getState() == SceneStates.MainMenu || SceneRoot.getState() == SceneStates.LevelSelector) {
                    adView.setEnabled(true);
                    adView.setVisibility(android.view.View.VISIBLE);
                } else {
                    unshowAds();
                }
            }
        });

        adView.loadAd(request);
    }

    //hide ads.
    public void unshowAds () {
        adView.setVisibility(android.view.View.GONE);
        adView.setEnabled(false);
    }

    final Runnable unshowAdsRunnable = new Runnable() {
        public void run() {
            unshowAds();
        }
    };

    final Runnable showAdsRunnable = new Runnable() {
        public void run() {
            showAds();
        }
    };

    public void showAdvertisement() {
        adsHandler.post(showAdsRunnable);
    }

    public void hideAdvertisement() {
        adsHandler.post(unshowAdsRunnable);
    }

}