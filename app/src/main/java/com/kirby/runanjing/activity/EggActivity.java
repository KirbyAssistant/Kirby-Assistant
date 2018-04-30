package com.kirby.runanjing.activity;
import android.os.*;
import android.view.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.*;
import com.yasic.library.particletextview.MovingStrategy.*;
import com.yasic.library.particletextview.Object.*;
import com.yasic.library.particletextview.View.*;

public class EggActivity extends BaseActivity
{
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		//隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg);
		ParticleTextView egg = (ParticleTextView) findViewById(R.id.egg);
		ParticleTextView kirbyAssistant = (ParticleTextView) findViewById(R.id.kirbydownload);
		ParticleTextView hi = (ParticleTextView) findViewById(R.id.hi);	
		
		RandomMovingStrategy randomMovingStrategy = new RandomMovingStrategy();
		BidiVerticalStrategy bidiVerticalStrategy = new BidiVerticalStrategy();
		
		ParticleTextViewConfig config1 = new ParticleTextViewConfig.Builder()
			.setRowStep(8)
			.setColumnStep(8)
			.setTargetText(getEggText(R.string.egg))
			.setReleasing(0.2)
			.setParticleRadius(3)
		    .setColumnStep(6)
		    .setRowStep(6)
			.setMiniDistance(0.1)
			.setTextSize(150)
			//.setDelay(500L)
			.setMovingStrategy(randomMovingStrategy)
			.instance();
		egg.setConfig(config1);
		
		ParticleTextViewConfig config2 = new ParticleTextViewConfig.Builder()
			.setRowStep(8)
			.setColumnStep(8)
			.setTargetText("Kirby Assistant")
			.setReleasing(0.2)
			.setParticleRadius(3)
			.setColumnStep(6)
		    .setRowStep(6)
			.setMiniDistance(0.1)
			.setTextSize(150)
			//.setDelay(500L)
			.setMovingStrategy(bidiVerticalStrategy)
			.instance();
		kirbyAssistant.setConfig(config2);
		
		ParticleTextViewConfig config3 = new ParticleTextViewConfig.Builder()
			.setRowStep(8)
			.setColumnStep(8)
			.setTargetText(getEggText(R.string.welcome_to))
			.setReleasing(0.2)
			.setParticleRadius(3)
			.setColumnStep(6)
		    .setRowStep(6)
			.setMiniDistance(0.1)
			.setTextSize(150)
			//.setDelay(500L)
			.setMovingStrategy(randomMovingStrategy)
			.instance();
		hi.setConfig(config3);
		
		egg.startAnimation();
		kirbyAssistant.startAnimation();
		hi.startAnimation();
	}
	private String getEggText(int res_id){
		return this.getResources().getString(res_id);
	}
}
