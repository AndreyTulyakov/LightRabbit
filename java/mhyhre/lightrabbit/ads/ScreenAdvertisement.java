package mhyhre.lightrabbit.ads;

import android.app.Activity;
import android.os.Handler;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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

        request = new AdRequest.Builder()
                .setGender(AdRequest.GENDER_UNKNOWN)
                .build();

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
                adView.setEnabled(true);
                adView.setVisibility(android.view.View.VISIBLE);
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