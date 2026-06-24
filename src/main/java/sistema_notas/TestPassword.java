package sistema_notas;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {

    public static void main(String[] args) {

        String password = "123456";

        String encoded = new BCryptPasswordEncoder().encode(password);

        System.out.println("PASSWORD ENCRIPTADO: " + encoded);
    }
}