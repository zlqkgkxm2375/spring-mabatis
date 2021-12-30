package com.jang.bbs.utils;

import java.io.UnsupportedEncodingException;

public class AESTest {

	public static void main(String[] args) throws Exception {
		
        String key = "jangan-1182-sam!!";       // key�뒗 16�옄 �씠�긽
        AES256Util aes256 = new AES256Util(key);
         
        String text = "1234";
        String encText = aes256.aesEncode(text);
        String decText = aes256.aesDecode(encText);
         
        System.out.println("�븫�샇�솕�븷 臾몄옄 : " + text);
        System.out.println("�븫�샇�솕�맂 臾몄옄 : " + encText);
        System.out.println("蹂듯샇�솕�맂 臾몄옄 : " + decText);
    }
}
