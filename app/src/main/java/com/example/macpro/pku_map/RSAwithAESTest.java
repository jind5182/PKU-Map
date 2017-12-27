package com.example.macpro.pku_map;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import org.json.JSONObject;
import sun.misc.BASE64Encoder;
import java.util.Map;


public class RSAwithAESTest {
	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCj64mf9V3vf0/VM1yn0dy47MpTumbZIT+m5Fib" +
			"VGKTtuHqg582smO86+Rzdb/WqqbT/jKUUwaJIy+zMICpS1Z+1QrbgCqaJB96Ltbj7tM2jaxKTJ8S" +
			"+DrOJpc8BWdo/He72CgrUQmHvxT7Uq6wH+F1mvaR/JQDsDVMNCzq0p7BZQIDAQAB";
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
//			res.put("key", Base64.encodeToString(encodedKey.getBytes(),Base64.NO_WRAP));
			res.put("key", encodedKey);
//			res.put("data", Base64.encodeToString(input.getBytes(), Base64.NO_WRAP));
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
