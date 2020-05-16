package com.example.ad_nup.card;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.ad_nup.UtilityKt;
import com.example.ad_nup.UtilityKt.*;

import com.example.ad_nup.database.AppDatabase;
import com.example.ad_nup.database.models.transaction.UserTransaction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            if(sms != null){
                for (int i = 0; i < sms.length; ++i) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
                    String smsBody = smsMessage.getMessageBody().toString();
                    String address = smsMessage.getOriginatingAddress();

                    if(smsBody.matches(".*XXXX[0-9][0-9][0-9][0-9].*")
                        && smsBody.matches(".*Rs\\.[0-9]+.*")
                    ){
                        Pattern pattern = Pattern.compile("Rs\\.[0-9]+(\\.[0-9]+)*");
                        Matcher matcher = pattern.matcher(smsBody);
                        if(matcher.find()){
                            String amount = smsBody.substring(matcher.start()+3,matcher.end());
                            handleTheSms(context,amount);
                        }
                    }
                }

            }
       }
    }

    public void handleTheSms(Context context,String message){
        try{
            AppDatabase database = AppDatabase.Companion.getDatabase(context);
            UserTransaction transaction = new UserTransaction(0, UtilityKt.getDay(),UtilityKt.getMonth(),UtilityKt.getYear(),"Undefined",Double.valueOf(message));
            database.userTransactionDao().insertAll(transaction);
            Log.e("Abinaya","SuccessFull");
        }catch (Exception e){
            Log.e("ERROR",e.getMessage());
        }
    }
}