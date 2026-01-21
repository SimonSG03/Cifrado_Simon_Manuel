package es.SimonSG;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class Servidor {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Servidor esperando conexión...");
        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectado");
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String ivBase64 = in.readLine();
            String mensajeBase64 = in.readLine();

            SecretKeySpec key =
                    new SecretKeySpec(ClaveCompartida.CLAVE.getBytes(), "AES");

            byte[] iv = Base64.getDecoder().decode(ivBase64);
            byte[] cifrado = Base64.getDecoder().decode(mensajeBase64);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

            byte[] descifrado = cipher.doFinal(cifrado);

            System.out.println("Mensaje recibido: " + new String(descifrado));

            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            System.err.println("Error en la comunicación, la clave del cliente o servidor es incorrecta");
        }
    }
}