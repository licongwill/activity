package com.me.activity.util.base64;

import java.util.Arrays;

/**
 * @program: activity
 * @description: base64加密
 * @author: lic
 * @create: 2020-01-16 10:54
 **/
public final class Base64 {
    private static final char[] BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final char[] BASE64URL_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();
    private static final int[] BASE64_IALPHABET = new int[256];
    private static final int[] BASE64URL_IALPHABET = new int[256];
    private static final int IALPHABET_MAX_INDEX = BASE64_IALPHABET.length - 1;
    static {
        Arrays.fill(BASE64_IALPHABET, -1);
        System.arraycopy(BASE64_IALPHABET, 0, BASE64URL_IALPHABET, 0, BASE64_IALPHABET.length);
        for (int i = 0, iS = BASE64_ALPHABET.length; i < iS; i++) {
            BASE64_IALPHABET[BASE64_ALPHABET[i]] = i;
            BASE64URL_IALPHABET[BASE64URL_ALPHABET[i]] = i;
        }
        BASE64_IALPHABET['='] = 0;
        BASE64URL_IALPHABET['='] = 0;
    }

    public static final Base64 DEFAULT = new Base64(false);
    public static final Base64 URL_SAFE = new Base64(true);

    private final boolean urlsafe;
    private final char[] ALPHABET;
    private final int[] IALPHABET;

    private Base64(boolean urlsafe) {
        this.urlsafe = urlsafe;
        this.ALPHABET = urlsafe ? BASE64URL_ALPHABET : BASE64_ALPHABET;
        this.IALPHABET = urlsafe ? BASE64URL_IALPHABET : BASE64_IALPHABET;
    }

    private String getName() {
        return urlsafe ? "base64url" : "base64"; // RFC 4648 codec names are all lowercase
    }


    /**
    *@Description: base64加密，将3个8位的字节，转换成4个6位字节，不足高位补0
    *@Param: [sArr, lineSep]
    *@return: char[]
    *@Author: lic
    *@date: 2020/1/16
    */
    private char[] encodeToChar(byte[] sArr, boolean lineSep) {

        // Check special case
        int sLen = sArr != null ? sArr.length : 0;
        if (sLen == 0) {
            return new char[0];
        }

        int eLen = (sLen / 3) * 3; // # of bytes that can encode evenly into 24-bit chunks
        int left = sLen - eLen;    // # of bytes that remain after 24-bit chunking. Always 0, 1 or 2

        int cCnt = (((sLen - 1) / 3 + 1) << 2); // # of base64-encoded characters including padding
        int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0); // Length of returned char array with padding and any line separators

        int padCount = 0;
        if (left == 2) {
            padCount = 1;
        } else if (left == 1) {
            padCount = 2;
        }

        char[] dArr = new char[urlsafe ? (dLen - padCount) : dLen];

        // Encode even 24-bits
        for (int s = 0, d = 0, cc = 0; s < eLen; ) {

            // Copy next three bytes into lower 24 bits of int, paying attension to sign.
            int i = (sArr[s++] & 0xff) << 16 | (sArr[s++] & 0xff) << 8 | (sArr[s++] & 0xff);

            // Encode the int into four chars
            dArr[d++] = ALPHABET[(i >>> 18) & 0x3f];
            dArr[d++] = ALPHABET[(i >>> 12) & 0x3f];
            dArr[d++] = ALPHABET[(i >>> 6) & 0x3f];
            dArr[d++] = ALPHABET[i & 0x3f];

            // Add optional line separator
            if (lineSep && ++cc == 19 && d < dLen - 2) {
                dArr[d++] = '\r';
                dArr[d++] = '\n';
                cc = 0;
            }
        }

        // Pad and encode last bits if source isn't even 24 bits.
        if (left > 0) {
            // Prepare the int
            int i = ((sArr[eLen] & 0xff) << 10) | (left == 2 ? ((sArr[sLen - 1] & 0xff) << 2) : 0);

            // Set last four chars
            dArr[dLen - 4] = ALPHABET[i >> 12];
            dArr[dLen - 3] = ALPHABET[(i >>> 6) & 0x3f];
            //dArr[dLen - 2] = left == 2 ? ALPHABET[i & 0x3f] : '=';
            //dArr[dLen - 1] = '=';
            if (left == 2) {
                dArr[dLen - 2] = ALPHABET[i & 0x3f];
            } else if (!urlsafe) { // if not urlsafe, we need to include the padding characters
                dArr[dLen - 2] = '=';
            }
            if (!urlsafe) { // include padding
                dArr[dLen - 1] = '=';
            }
        }
        return dArr;
    }

    private int ctoi(char c) throws DecodingException{
        int i = c > IALPHABET_MAX_INDEX ? -1 : IALPHABET[c];
        if (i < 0) {
            String msg = "Illegal " + getName() + " character: '" + c + "'";
            throw new DecodingException(msg);
        }
        return i;
    }

   /**
   *@Description: 解密
   *@Param: [sArr]
   *@return: byte[]
   *@Author: lic
   *@date: 2020/1/16
   */
   final byte[] decodeFast(char[] sArr) throws DecodingException {

        // Check special case
        int sLen = sArr != null ? sArr.length : 0;
        if (sLen == 0) {
            return new byte[0];
        }

        int sIx = 0, eIx = sLen - 1;    // Start and end index after trimming.

        // Trim illegal chars from start
        while (sIx < eIx && IALPHABET[sArr[sIx]] < 0) {
            sIx++;
        }

        // Trim illegal chars from end
        while (eIx > 0 && IALPHABET[sArr[eIx]] < 0) {
            eIx--;
        }

        // get the padding count (=) (0, 1 or 2)
        int pad = sArr[eIx] == '=' ? (sArr[eIx - 1] == '=' ? 2 : 1) : 0;  // Count '=' at end.
        int cCnt = eIx - sIx + 1;   // Content count including possible separators
        int sepCnt = sLen > 76 ? (sArr[76] == '\r' ? cCnt / 78 : 0) << 1 : 0;

        int len = ((cCnt - sepCnt) * 6 >> 3) - pad; // The number of decoded bytes
        byte[] dArr = new byte[len];       // Preallocate byte[] of exact length

        // Decode all but the last 0 - 2 bytes.
        int d = 0;
        for (int cc = 0, eLen = (len / 3) * 3; d < eLen; ) {

            // Assemble three bytes into an int from four "valid" characters.
            int i = ctoi(sArr[sIx++]) << 18 | ctoi(sArr[sIx++]) << 12 | ctoi(sArr[sIx++]) << 6 | ctoi(sArr[sIx++]);

            // Add the bytes
            dArr[d++] = (byte) (i >> 16);
            dArr[d++] = (byte) (i >> 8);
            dArr[d++] = (byte) i;

            // If line separator, jump over it.
            if (sepCnt > 0 && ++cc == 19) {
                sIx += 2;
                cc = 0;
            }
        }

        if (d < len) {
            // Decode last 1-3 bytes (incl '=') into 1-3 bytes
            int i = 0;
            for (int j = 0; sIx <= eIx - pad; j++) {
                i |= ctoi(sArr[sIx++]) << (18 - j * 6);
            }

            for (int r = 16; d < len; r -= 8) {
                dArr[d++] = (byte) (i >> r);
            }
        }

        return dArr;
    }
}
