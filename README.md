## keytool&openssl命令

keytool官方文档：
[https://docs.oracle.com/javase/8/docs/technotes/tools/windows/keytool.html](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/keytool.html)


### 查看命令帮助
```
keytool -command_name -help
keytool -genkeypair -help
```

### 查看密钥库里面的信息
```
keytool -list -keystore peer1.jks -storepass passwd1 -v
```

### 查看指定证书文件的信息
```
keytool -printcert -file peer1.cer -v
```

### 转换JKS格式为P12
```
keytool -importkeystore -srckeystore peer2.jks -destkeystore peer2.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass passwd2 -deststorepass passwd2 -srckeypass passwd2 -destkeypass passwd2 -srcalias peer2 -destalias peer2 -noprompt
```

### cer证书转为pem
```
openssl x509 -in ca.cer -inform DER -out ca.pem -outform PEM
```

