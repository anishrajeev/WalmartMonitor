import java.net.Authenticator;
import java.net.PasswordAuthentication;

public  class PasswordAuthenticator extends Authenticator {


    protected PasswordAuthentication getPasswordAuthentication() {

        String username = "USER";
        String password = "PW";
        return new PasswordAuthentication(username, password.toCharArray());

    }
}
