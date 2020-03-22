package com.github.lkqm.weixin.gateway.util;

import com.github.lkqm.weixin.gateway.WxConfig;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 微信相关工具类
 */
@Slf4j
@UtilityClass
public class WxUtils {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final ThreadLocal<DocumentBuilder> BUILDER_LOCAL = new ThreadLocal<DocumentBuilder>() {
        @Override
        protected DocumentBuilder initialValue() {
            try {
                final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setExpandEntityReferences(false);
                return factory.newDocumentBuilder();
            } catch (ParserConfigurationException exc) {
                throw new IllegalArgumentException(exc);
            }
        }
    };

    /**
     * 校验签名
     */
    public static boolean checkSignature(String token, String timestamp, String nonce,
                                         String signature) {
        try {
            return signature(token, timestamp, nonce)
                    .equals(signature);
        } catch (Exception e) {
            log.error("Checking signature failed, and the reason is :" + e.getMessage());
            return false;
        }
    }

    /**
     * 生成签名
     */
    private static String signature(String token, String timestamp, String nonce) {
        String[] elements = {token, timestamp, nonce};
        Arrays.sort(elements);
        String text = StringUtils.join(elements, "");
        return sha1Hex(text);
    }

    /**
     * 解析AES密文xml
     */
    public static String decryptXml(WxConfig appConfig, String encryptedXml) {
        String cipherText = extractEncryptPart(encryptedXml);
        return decrypt(appConfig, cipherText);
    }

    /**
     * 提取密文xml
     */
    private static String extractEncryptPart(String xml) {
        try {
            DocumentBuilder db = BUILDER_LOCAL.get();
            Document document = db.parse(new InputSource(new StringReader(xml)));

            Element root = document.getDocumentElement();
            return root.getElementsByTagName("Encrypt").item(0).getTextContent();
        } catch (Exception e) {
            throw new RuntimeException("提取加密的xml内容失败", e);
        }
    }

    /**
     * 对密文xml继续解密
     */
    private static String decrypt(WxConfig appConfig, String cipherText) {
        final String appId = appConfig.getAppId();
        final String encodingAesKey = appConfig.getAesKey();

        byte[] original;
        try {
            byte[] aesKey = decodeBase64(encodingAesKey + "=");
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(
                    Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
            byte[] encrypted = decodeBase64(cipherText);
            original = cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("微信消息密文解析失败", e);
        }

        String xmlContent, fromAppid;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int xmlLength = bytesNetworkOrder2Number(networkOrder);
            xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            fromAppid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length),
                    CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("微信消息密文解析失败", e);
        }

        if (!fromAppid.equals(appId)) {
            throw new RuntimeException("微信消息密文解析失败, AppID不正确");
        }
        return xmlContent;
    }

    private static int bytesNetworkOrder2Number(byte[] bytesInNetworkOrder) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= bytesInNetworkOrder[i] & 0xff;
        }
        return sourceNumber;
    }


    /**
     * 提供基于PKCS7算法的加解.
     *
     * @author tencent
     */
    public static class PKCS7Encoder {

        private static final Charset CHARSET = StandardCharsets.UTF_8;
        private static final int BLOCK_SIZE = 32;

        /**
         * 获得对明文进行补位填充的字节.
         *
         * @param count 需要进行填充补位操作的明文字节个数
         * @return 补齐用的字节数组
         */
        public static byte[] encode(int count) {
            // 计算需要填充的位数
            int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
            // 获得补位所用的字符
            char padChr = chr(amountToPad);
            StringBuilder tmp = new StringBuilder();
            for (int index = 0; index < amountToPad; index++) {
                tmp.append(padChr);
            }
            return tmp.toString().getBytes(CHARSET);
        }

        /**
         * 删除解密后明文的补位字符.
         *
         * @param decrypted 解密后的明文
         * @return 删除补位字符后的明文
         */
        public static byte[] decode(byte[] decrypted) {
            int pad = decrypted[decrypted.length - 1];
            if (pad < 1 || pad > 32) {
                pad = 0;
            }
            return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
        }

        /**
         * 将数字转化成ASCII码对应的字符，用于对明文进行补码.
         *
         * @param a 需要转化的数字
         * @return 转化得到的字符
         */
        private static char chr(int a) {
            byte target = (byte) (a & 0xFF);
            return (char) target;
        }

    }

    @SneakyThrows
    public static byte[] decodeBase64(String source) {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(source);
    }

    public static String sha1Hex(String source) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            return bytesToHex((md.digest(StringUtils.getBytesUTF8(source))));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Never happen", e);
        }
    }

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
