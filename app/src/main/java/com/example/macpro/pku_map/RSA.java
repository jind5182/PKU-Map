package com.example.macpro.pku_map;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import android.util.Base64;

import javax.crypto.Cipher;

/**
 * Created by Jackie on 27/12/2017.
 */

public class RSA {
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeqJ2LoWiLE77i+2V7BvNY4iaxE0bv2ES6rWUhCoBMYMMTek7TSQyhuInWvdMp0d6zqMwsHKGavyfx+r1E/Xkt4/w60PQzT1u1c7oGGz8ot45msHnWSOprfD0Rh+1fCBL1QxkD21Xog1fQCDrQeKfZ+NyBMx2T3qd4HTxQRhtzewIDAQAB";
    public static byte[] encrypt(byte[] content){
        PublicKey publicKey = getPublicKey(RSA.publicKey);
        Cipher cipher = null;
        byte[] res = null;
        try{
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            res = cipher.doFinal(content);
        } catch (java.security.NoSuchAlgorithmException |
                javax.crypto.NoSuchPaddingException |
                java.security.InvalidKeyException|
                javax.crypto.IllegalBlockSizeException |
                javax.crypto.BadPaddingException e){

        }

        return Base64.encode(res, Base64.NO_WRAP);
    }
    public static PublicKey getPublicKey(String publicKey){
        byte[] keyBytes = Base64.decode(publicKey.getBytes(), Base64.NO_WRAP);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory;
        PublicKey res = null;
        try{
            keyFactory = KeyFactory.getInstance("RSA");
            res = keyFactory.generatePublic(keySpec);
        } catch (java.security.NoSuchAlgorithmException |
                java.security.spec.InvalidKeySpecException e) {

        }

        return res;

    }

}
