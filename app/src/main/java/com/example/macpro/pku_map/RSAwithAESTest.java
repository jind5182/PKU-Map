package com.example.macpro.pku_map;
import java.util.Map;


public class RSAwithAESTest {
	private static String publicKey;   //RSA��Կ
	private static String privateKey;  //RSA˽Կ
	
	public static void main(String[] args) throws Exception{
		String inputStr = "15528911698";  
			
		String key = AESCoder.initKey();  //����AES��Կ
		System.out.println("ԭ��:  " + inputStr); 
		System.out.println("AES��Կ:  " + key);
		byte[] inputData = inputStr.getBytes();
		inputData = AESCoder.encrypt(inputData, key);
		System.out.println("��AES��Կ���ܺ������:  " + AESCoder.encryptBASE64(inputData));  //��AES���ܺ������
		
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
	
	}
}
