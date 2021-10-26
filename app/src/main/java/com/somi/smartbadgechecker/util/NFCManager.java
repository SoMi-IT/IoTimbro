package com.somi.smartbadgechecker.util;


import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class NFCManager {


    private static final String TAG = "XXX";


    public static String obtainTagFromIntent(Intent intent) {

        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            assert tag != null;

            for (String tech : tag.getTechList()) {

                if (tech.equals(MifareUltralight.class.getName())) {

                    MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                    return readTag(mifareUlTag);

                }

            }

        }

        return "";

    }

    public static String readTag(MifareUltralight mifareUlTag) {

        try {

            mifareUlTag.connect();
            byte[] payload = mifareUlTag.readPages(4);
            //Charset.forName("US-ASCII")
            return new String(payload, StandardCharsets.US_ASCII);

        } catch (IOException e) {

            Log.d(TAG, "IOException while reading MifareUltralight message...", e);

        } finally {

            if (mifareUlTag != null) {

                try {
                    mifareUlTag.close();

                } catch (IOException e) {
                    Log.d(TAG, "Error closing tag...", e);
                }

            }

        }

        return null;

    }//readTag

}//NFCManager
