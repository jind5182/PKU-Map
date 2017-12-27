package com.example.macpro.pku_map;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONObject;

import java.util.Map;


public class RSAwithAESTest {
	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA" +
			"4GNADCBiQKBgQDeqJ2LoWiLE77i+2V7BvNY4iaxE0bv2ES6rWUhCoBMYMMTek7TSQ" +
			"yhuInWvdMp0d6zqMwsHKGavyfx+r1E/Xkt4/w60PQzT1u1c7oGGz8ot45msHnWSOprfD" +
			"0Rh+1fCBL1QxkD21Xog1fQCDrQeKfZ+NyBMx2T3qd4HTxQRhtzewIDAQAB";
	private static String privateKey;
	
	@RequiresApi(api = Build.VERSION_CODES.O)
	public static JSONObject RSA_AES(JSONObject inputStr){
		JSONObject res = new JSONObject();
		String key = null;
		try {
			key = AESCoder.initKey();
			byte[] inputData = inputStr.toString().getBytes("UTF-8");
			inputData = AESCoder.encrypt(inputData, key);
			String input = new String(inputData);
			byte[] encodedData = RSACoder.encryptByPublicKey(key.getBytes(), publicKey);
			String encodedKey = new String(encodedData);
			res.put("key", encodedKey);
			res.put("data", input);
		} catch (java.lang.Exception e){

		}

		return res;
		/*
		Map<String, Object> keyMap = RSACoder.initKey();  //����RSA�Ĺ�Կ��˽Կ
		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);
		System.out.println("RSA�����Ĺ�Կ: \n" + publicKey);
		System.out.println("RSA������˽Կ�� \n" + privateKey);
		System.out.println("RSA��Կ����AES��Կ����RSA˽Կ����AES��Կ");
		
		byte[] data = key.getBytes();  //��AES��Կ�����ɵ�RSA��Կ����
		byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
		byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,privateKey);
		
		String outputkey = new String(decodedData);
		System.out.println("RSA��Կ����ǰ��AES��Կ: " + key + "\n" + "RSA��Կ���ܺ��AES��Կ��"+encodedData+"\n"+"RSA˽Կ���ܺ��AES��Կ: " + outputkey);
		
		byte[] outputData = AESCoder.decrypt(inputData, outputkey);  //��AES��Կ��������
		String outputStr = new String(outputData);
		System.out.println("��AES��Կ���ܺ�õ�������:  " + outputStr);
		*/
	
	}
}
