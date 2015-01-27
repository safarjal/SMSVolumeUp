package com.safarjal.smsvolumeup;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;


public class SmsReceiver extends WakefulBroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		intent.setClass(context, SmsService.class);
		startWakefulService(context, intent);
	}

	
	public static class SmsService extends IntentService
	implements Runnable {
		private Handler handler;
		String smsbody = "";
		String smsphonenumber = "";

		public SmsService() {
			super("Sms Service");
		}

		@Override
		public void onCreate() {
			super.onCreate();
			System.out.println("Sms service created");

			handler = new Handler();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void onHandleIntent(Intent intent) {
			// TODO Auto-generated method stub
//			 ---get the SMS message passed in---
			System.out.println("Sms service handled");

	        Bundle bundle = intent.getExtras();        
	        SmsMessage[] msgs = null;
	        smsbody = "";
	        smsphonenumber = "";
	        if (bundle != null){
	            //---retrieve the SMS message received---
	            Object[] pdus = (Object[]) bundle.get("pdus");
	            msgs = new SmsMessage[pdus.length];            
	            for (int i=0; i<msgs.length; i++){
	                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
	                smsphonenumber = msgs[i].getOriginatingAddress();
	                smsbody = msgs[i].getMessageBody().toString();
	            }
	        }
	        checkContent();
			handler.post(this);
			clearValues();
	        SmsReceiver.completeWakefulIntent(intent);
		}
		public void checkContent(){
			System.out.println("checkContent started");
			SharedPreferences settings;
			settings = PreferenceManager.getDefaultSharedPreferences(this);
			String password;
			password = settings.getString("password", "VolumeUp");

			if(smsbody.equals(password)){
//				sendSms("Read Sms", "03334226874");
				System.out.println("GOT to sms reading");
				AudioManager am = 
					    (AudioManager) getSystemService(Context.AUDIO_SERVICE);

					am.setStreamVolume(
					    AudioManager.STREAM_RING,
					    am.getStreamMaxVolume(AudioManager.STREAM_RING),
					    0);
			}
		}
		
//		public void sendSms(String smscompose, String phoneNumber){
//			System.out.println("SendSms started");
//		    
//	        SmsManager sms = SmsManager.getDefault();
//	        
//	        ArrayList<String> parts =sms.divideMessage(smscompose);
//	        int numParts = parts.size();
//
//	        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
//	        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
//
//	        for (int i = 0; i < numParts; i++) {
//	        	sentIntents.add(PendingIntent.getBroadcast(this, 0, new Intent(this, SmsService.class), 0));
//	        	deliveryIntents.add(PendingIntent.getBroadcast(this, 0, new Intent(this, SmsService.class), 0));
//	        }
//
//	        sms.sendMultipartTextMessage(phoneNumber,null, parts, sentIntents, deliveryIntents);
//		}
		
		public void clearValues(){
	        smsbody = "";
	        smsphonenumber = "";
		}
	}
}
