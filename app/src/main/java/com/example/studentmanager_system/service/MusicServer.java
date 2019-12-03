package com.example.studentmanager_system.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.studentmanager_system.R;

public class MusicServer extends Service {
	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mediaPlayer.stop();

	}

	String ma, mb;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		ma = intent.getStringExtra(Contants.CONNET_DATA);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		if (mediaPlayer == null) {
			if (ma.equals("1")) {
				mediaPlayer = MediaPlayer.create(this, R.raw.chuxue);
			}

			mediaPlayer.setLooping(true);
			mediaPlayer.start();
		}
	}
}
