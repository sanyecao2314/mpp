package com.citsamex.service.tony;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Md5PasswordEncoder {

	private String pony_mixer_type = "pony";

	private int[] DEFAULT_KEY = null;

	public Md5PasswordEncoder() {
		DEFAULT_KEY = new int[] { 0x789f5645, 0xf68bd5a4, 0x81963ffa, 0x458fac58 };
	}

	public Md5PasswordEncoder(int[] KEY) {
		this.DEFAULT_KEY = KEY;
	}

	public String encodePassword(String originalPwd) {
		String ponyMixPassword = this.mergePasswordAndPonymixer(originalPwd, pony_mixer_type, false);
		// System.out.println("*****"+ponyMixPassword+"****");
		MessageDigest messageDigest = this.getMessageDigest();
		byte[] digest;
		try {

			digest = messageDigest.digest(ponyMixPassword.getBytes("UTF-8"));
			// System.out.println("----"+digest);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported!");
		}
		return new String(Hex.encodeHex(digest));

	}

	public boolean isPasswordValid(String encPassword, String originalPwd) {
		String password1 = "" + encPassword;
		String password2 = this.encodePassword(originalPwd);
		return password1.equals(password2);
	}

	protected String mergePasswordAndPonymixer(String password, Object pony_mixer_type, boolean strict) {
		if (password == null) {
			password = "";
		}
		if (strict && pony_mixer_type != null) {
			if (pony_mixer_type.toString().lastIndexOf("{") != -1 || pony_mixer_type.toString().lastIndexOf("}") != -1) {

				throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
			}
		}
		if (pony_mixer_type == null || "".equals(pony_mixer_type)) {
			return password;
		} else {
			return password + "{" + pony_mixer_type.toString() + "}";
		}

	}

	protected final MessageDigest getMessageDigest() {
		String algorithm = "MD5";
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
		}
	}

	public String getPony_mixer_type() {
		return pony_mixer_type;
	}

	public void setPony_mixer_type(String pony_mixer_type) {
		this.pony_mixer_type = pony_mixer_type;
	}

	public String decrypt(String strEncrypt) {
		byte[] secretInfo;
		try {
			secretInfo = Hex.decodeHex(strEncrypt.toCharArray());
		} catch (DecoderException e) {
			e.printStackTrace(System.out);
			return null;
		}
		return decryptByAlgorithm(secretInfo, DEFAULT_KEY);
	}

	public String decryptByAlgorithm(byte[] secretInfo, int[] KEY) {
		byte[] decryptStr = null;
		byte[] tempDecrypt = new byte[secretInfo.length];
		for (int offset = 0; offset < secretInfo.length; offset += 8) {
			decryptStr = decrypt(secretInfo, offset, KEY, 32);
			System.arraycopy(decryptStr, 0, tempDecrypt, offset, 8);
		}

		int n = tempDecrypt[0];
		return new String(tempDecrypt, n, decryptStr.length - n);
	}

	public String encrypt(String strOriginal) {

		return encryptByAlgorithm(strOriginal, DEFAULT_KEY);

	}

	public String encryptByAlgorithm(String strOriginal, int[] KEY) {
		byte[] temp = strOriginal.getBytes();
		int n = 8 - temp.length % 8;// ��temp��λ����8�ı���,��Ҫ����λ��
		byte[] encryptStr = new byte[temp.length + n];
		encryptStr[0] = (byte) n;
		System.arraycopy(temp, 0, encryptStr, n, temp.length);
		byte[] result = new byte[encryptStr.length];
		for (int offset = 0; offset < result.length; offset += 8) {
			byte[] tempEncrpt = encrypt(encryptStr, offset, KEY, 32);
			System.arraycopy(tempEncrpt, 0, result, offset, 8);
		}
		return new String(Hex.encodeHex(result));
	}

	public byte[] encrypt(byte[] content, int offset, int[] key, int times) {
		int[] tempInt = byteToInt(content, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0, i;
		int delta = 0x9e3779b9; // �����㷨��׼���ֵ
		int a = key[0], b = key[1], c = key[2], d = key[3];

		for (i = 0; i < times; i++) {
			sum += delta;
			y += ((z << 4) + a) ^ (z + sum) ^ ((z >> 5) + b);
			z += ((y << 4) + c) ^ (y + sum) ^ ((y >> 5) + d);
		}
		tempInt[0] = y;
		tempInt[1] = z;
		return intToByte(tempInt, 0);
	}

	public byte[] decrypt(byte[] encryptContent, int offset, int[] key, int times) {
		int[] tempInt = byteToInt(encryptContent, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0xC6EF3720, i;
		int delta = 0x9e3779b9; // �����㷨��׼���ֵ
		int a = key[0], b = key[1], c = key[2], d = key[3];

		for (i = 0; i < times; i++) {
			z -= ((y << 4) + c) ^ (y + sum) ^ ((y >> 5) + d);
			y -= ((z << 4) + a) ^ (z + sum) ^ ((z >> 5) + b);
			sum -= delta;
		}
		tempInt[0] = y;
		tempInt[1] = z;

		return intToByte(tempInt, 0);
	}

	private int[] byteToInt(byte[] content, int offset) {

		int[] result = new int[content.length >> 2];// ����2��n�η� == ����nλ ��
													// content.length / 4 ==
													// content.length >> 2
		for (int i = 0, j = offset; j < content.length; i++, j += 4) {
			result[i] = transform(content[j + 3]) | transform(content[j + 2]) << 8 | transform(content[j + 1]) << 16
					| (int) content[j] << 24;
		}
		return result;

	}

	private byte[] intToByte(int[] content, int offset) {
		byte[] result = new byte[content.length << 2];// ����2��n�η� == ����nλ ��
														// content.length * 4 ==
														// content.length << 2
		for (int i = 0, j = offset; j < result.length; i++, j += 4) {
			result[j + 3] = (byte) (content[i] & 0xff);
			result[j + 2] = (byte) ((content[i] >> 8) & 0xff);
			result[j + 1] = (byte) ((content[i] >> 16) & 0xff);
			result[j] = (byte) ((content[i] >> 24) & 0xff);
		}
		return result;
	}

	private static int transform(byte temp) {
		int tempInt = (int) temp;
		if (tempInt < 0) {
			tempInt += 256;
		}
		return tempInt;
	}

	public static void main(String[] args) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = "123456789";
		String passwd = encoder.encrypt(password);
		System.out.println(passwd);
		passwd = encoder.decrypt(passwd);
		System.out.println(passwd);

	}
}
