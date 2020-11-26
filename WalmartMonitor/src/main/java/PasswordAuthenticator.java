import java.net.Authenticator;
import java.net.PasswordAuthentication;

public  class PasswordAuthenticator extends Authenticator {


    protected PasswordAuthentication getPasswordAuthentication() {

        String username = "eqzppx";
        String password = "yjfqbf";
        return new PasswordAuthentication(username, password.toCharArray());

    }
}