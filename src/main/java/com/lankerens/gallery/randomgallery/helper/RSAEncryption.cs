
 public static string Encrypt(string data, string base64PublicKey)
 {
  byte[] publicKeyBytes = Convert.FromBase64String(base64PublicKey);
  using (RSA rsa = RSA.Create())
  {
   rsa.ImportSubjectPublicKeyInfo(publicKeyBytes, out _);
   byte[] encryptedData = rsa.Encrypt(Encoding.UTF8.GetBytes(data), RSAEncryptionPadding.Pkcs1);
   return Convert.ToBase64String(encryptedData);
  }
 }


  public MainWindowViewModel() {
   // 替换为 Java 生成的公钥
   string publicKey = "";
   string data = "Hello, RSA!";
   string encryptedData = RSAEncryption.Encrypt(data, publicKey);
   Console.WriteLine("Encrypted Data: " + encryptedData);
  }