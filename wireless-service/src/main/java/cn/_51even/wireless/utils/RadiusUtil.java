package cn._51even.wireless.utils;

import java.io.UnsupportedEncodingException;

public class RadiusUtil {

    public RadiusUtil() {
    }

    public static byte[] getUtf8Bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return str.getBytes();
        }
    }

    public static String getStringFromUtf8(byte[] utf8) {
        try {
            return new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return new String(utf8);
        }
    }

    public static String getStringFromCPPU8(byte[] utf8) {
        try {
            if (utf8 == null) {
                return "";
            } else {
                int length;
                for(length = utf8.length; length > 0 && utf8[length - 1] == 0; --length) {
                }

                return new String(utf8, 0, length, "UTF-8");
            }
        } catch (UnsupportedEncodingException var2) {
            return new String(utf8);
        }
    }

    public static String getHexString(byte[] data) {
        StringBuffer hex = new StringBuffer();
        if (data != null) {
            for(int i = 0; i < data.length; ++i) {
                String digit = Integer.toString(data[i] & 255, 16);
                if (digit.length() < 2) {
                    hex.append('0');
                }

                hex.append(digit);
            }
        }

        return hex.toString();
    }

    public static String getHexString(byte data) {
        return getHexString(new byte[]{data});
    }

    public static byte[] hexStringToByte(String hex) {
        String temphex = hex.toUpperCase();
        int len = temphex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = temphex.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static long getUnsignedInt(int value) {
        long anUnsignedInt = 0L;
        byte[] data = new byte[]{(byte)(value >> 24 & 255), (byte)(value >> 16 & 255), (byte)(value >> 8 & 255), (byte)(value & 255)};
        anUnsignedInt = (long)((data[0] & 255) << 24 | (data[1] & 255) << 16 | (data[2] & 255) << 8 | data[3] & 255) & 4294967295L;
        return anUnsignedInt;
    }

}
