package com.melody.supermarket;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopApplicationTests {


    @Value("${rsa.private_key}")
    private String privateKey;
    @Value("${rsa.public_key}")
    private String publicKey;
    @Test
    void contextLoads() {
        RSA rsa = new RSA(privateKey,publicKey);

//      获得私钥
//        System.out.println(rsa.getPrivateKey());
//        System.out.println(rsa.getPrivateKeyBase64());
//      获得公钥
//        System.out.println("---------------------------");
//        System.out.println(rsa.getPublicKey());
//        System.out.println(rsa.getPublicKeyBase64());

//      公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);

//      Junit单元测试
        assert StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8).equals("我是一段测试aaaa");
    }

}
