package com.connxun.elinetv.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;

import com.connxun.elinetv.util.GlobalConsts;

import java.io.IOException;



public class PlayMusicService extends Service {
	private MediaPlayer player;
	private boolean isLoop=true;

	@Override
	public void onCreate() {
		player = new MediaPlayer();
		player.setOnPreparedListener(new OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				Intent i = new Intent(GlobalConsts.ACTION_START_PLAY);
				sendBroadcast(i);
			}
		});
		new WorkThread().start();
	}
	
	class WorkThread extends Thread {
		@Override
		public void run() {
			while(isLoop){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(player.isPlaying()){
					Intent i = new Intent(GlobalConsts.ACTION_UPDATE_PROGRESS);
					i.putExtra("current", player.getCurrentPosition());
					i.putExtra("total", player.getDuration());
					sendBroadcast(i);
				}
			}
		}
	}
	
	/**
	 */
	public IBinder onBind(Intent intent) {
		return new MusicBinder();
	}

	public class MusicBinder extends Binder {
		
		/**
		 * @param progress
		 */
		public void seekTo(int progress){
			player.seekTo(progress);
		}
		
		public boolean playOrPause(){
			if(player.isPlaying()){
				player.pause();
				return  true;
			}else{
				player.start();
				return  false;
			}
		}
		
		/**
		 */
		public void playMusic(String url){
			try {
				player.reset();
				player.setDataSource(url);
				player.prepareAsync();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}


