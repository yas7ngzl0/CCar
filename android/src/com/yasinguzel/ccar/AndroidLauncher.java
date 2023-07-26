


package com.yasinguzel.ccar;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CCar(), config);
		// Diğer konfigürasyonlar...

		// Oyun başlatılırken reklamları yüklemek için
		View gameView = initializeForView(new CCar(), config);
		AdView adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-9582732495383626/8044051790"); // AdMob'dan aldığınız reklam birimi kimliği
		adView.setAdSize(AdSize.BANNER);

		// Reklam isteğini oluşturun
		AdRequest adRequest = new AdRequest.Builder().build();

		// Reklamı yüklemek için isteği yükleyin
		adView.loadAd(adRequest);

		// Reklam görüntüleme düzenlemesi
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		// Reklamı ekleyin ve alt tarafa hizalayın
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		layout.addView(adView, adParams);

		setContentView(layout);
	}
}

