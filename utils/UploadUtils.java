package com.jzfblog.crm.utils;

import java.util.UUID;

/**
 * �ļ��ϴ��Ĺ�����
 * @author �����
 *
 */
public class UploadUtils {
	
	/**
	 * ����ļ����ظ�����
	 * @param fileName
	 * @return
	 */
	public static String getUuidFileName(String fileName) {
		
		// �ҳ�����"."��λ��
		int idx = fileName.indexOf("."); // aa.txt
		// ��ȡ��׺
		String lastName = fileName.substring(idx); //.txt
		// ����������ɵ�uuid+��׺
		return UUID.randomUUID().toString().replace("-", "") + lastName;
	}
	
	/**
	 * ʹ��Ŀ¼����ķ���
	 * @return
	 */
	public static String getPath(String uuidFileName) {
		// ��ȡ�ļ����Ĺ�ϣֵ
		int hashCode1 = uuidFileName.hashCode();
		int hashCode2 = hashCode1 >>> 4;
		// ��Ϊһ��Ŀ¼
		int d1 = hashCode1 & 0xf;
		// ��Ϊ����Ŀ¼
		int d2 = hashCode2 & 0xf;
		return "/" + d1 + "/" + d2;
	}
	
	public static void main(String[] args) {
		System.out.println(getUuidFileName("aa.txt"));
	}
}
