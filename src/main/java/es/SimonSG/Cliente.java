package es.SimonSG;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Simon
 */
public class Cliente {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("10.13.7.195", 5000);

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

        String mensaje = "Hola manuel, soy Simon";

        SecretKeySpec key =
                new SecretKeySpec(ClaveCompartida.CLAVE.getBytes(), "AES");

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

        byte[] cifrado = cipher.doFinal(mensaje.getBytes());

        out.println(Base64.getEncoder().encodeToString(iv));
        out.println(Base64.getEncoder().encodeToString(cifrado));

        socket.close();
    }

}
