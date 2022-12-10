package gr.thegoodsideofe1.tourguide.aes;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AES_ENCRYPTION_Test {
    AES_ENCRYPTION aes_encryption;

    @BeforeAll
    void setUp() {
        try {
            AES_ENCRYPTION encryptionClass = new AES_ENCRYPTION();
            encryptionClass.init();
            aes_encryption = encryptionClass;
        } catch (Exception e){
            assertTrue(false, "Encryption Class Initialize Method Not Working Properly");
        }
    }

    @AfterAll
    void tearDown() {
        aes_encryption.destroy();
    }

    @Test
    void checkEncryptionStrIsBase64() {
        try{
            String plainText = "This is a plain text";
            String encryptedStr = aes_encryption.encrypt(plainText);
            assertTrue(isBase64(encryptedStr), "Encrypt Method Not Working Properly");
        } catch (Exception e){
            assertTrue(false, "Exception Thrown");
        }
    }

    @Test
    void checkCanDecryptTheEncryption() {
        try {
            String plainText = "This is a plain text";
            String encryptedStr = aes_encryption.encrypt(plainText);
            String decryptedStr = aes_encryption.decrypt(encryptedStr);
            assertEquals(plainText, decryptedStr, "Expected Output is not Equal with Actual Output");
        } catch (Exception e){
            assertTrue(false, "Exception Thrown");
        }
    }

    @Test
    void checkIfEncodedStringIsBase64(){
        String plainText = "This is a plain text";
        byte[] plainTextByteArray = plainText.getBytes();
        String encodedStr = aes_encryption.encode(plainTextByteArray);
        assertTrue(isBase64(encodedStr), "Encode Method Not Working Properly");
    }

    @Test
    void checkCanDecodeTheEncodedString(){
        String plainText = "This is a plain text";
        byte[] plainTextByteArray = plainText.getBytes();
        String encodedStr = aes_encryption.encode(plainTextByteArray);
        byte[] decodedByteArray = aes_encryption.decode(encodedStr);
        assertTrue(Arrays.equals(plainTextByteArray, decodedByteArray), "Decode Method Not Working Properly");
    }

    public static boolean isBase64(String s) {
        String pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        return m.find();
    }
}