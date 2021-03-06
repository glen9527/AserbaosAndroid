package com.aserbao.aserbaosandroid.ui.animation.explosionAnimation.leonids.initializers;

import android.util.Log;


import com.aserbao.aserbaosandroid.ui.animation.explosionAnimation.leonids.Particle;

import java.util.Random;

public class AccelerationInitializer implements ParticleInitializer {

	private float mMinValue;
	private float mMaxValue;
	private int mMinAngle;
	private int mMaxAngle;

	public AccelerationInitializer(float minAcceleration, float maxAcceleration, int minAngle, int maxAngle) {
		mMinValue = minAcceleration;
		mMaxValue = maxAcceleration;
		mMinAngle = minAngle;
		mMaxAngle = maxAngle;
	}

	private static final String TAG = "AccelerationInitializer";
	@Override
	public void initParticle(Particle p, Random r) {
		float angle = mMinAngle;
		if (mMaxAngle != mMinAngle) {
			angle = r.nextInt(mMaxAngle - mMinAngle) + mMinAngle;
		}
		float angleInRads = (float) (angle* Math.PI/180f);
		float value = r.nextFloat()*(mMaxValue-mMinValue)+mMinValue;
		p.mAccelerationX = (float) (value * Math.cos(angleInRads));
		p.mAccelerationY = (float) (value * Math.sin(angleInRads));
		Log.e(TAG, "initParticle: " + p.mAccelerationX + " Y = " + p.mAccelerationY );
	}

}
