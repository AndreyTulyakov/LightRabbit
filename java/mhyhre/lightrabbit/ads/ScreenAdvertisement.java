package mhyhre.lightrabbit.ads;

import android.app.Activity;
import android.os.Handler;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ScreenAdvertisement {

    final Activity activity;
    final int advertisementId;

    final Handler adsHandler = new Handler();

    public ScreenAdvertisement(final Activity activity, final int advertisementId) {
        this.activity = activity;
        this.advertisementId = advertisementId;
    }

    //show the ads.
    public void showAds () {
        // Show the ad.
        AdView adView = (AdView)activity.findViewById(advertisementId);
        adView.setVisibility(android.view.View.VISIBLE);
        adView.setEnabled(true);

       // AdRequest request = new AdRequest.Builder().build();
        AdRequest request = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_UNKNOWN)
                .build();

        adView.loadAd(request);
    }

    //hide ads.
    public void unshowAds () {
        AdView adView = (AdView)activity.findViewById(advertisementId);
        adView.setVisibility(android.view.View.INVISIBLE);
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