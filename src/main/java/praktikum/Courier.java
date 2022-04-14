package praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    public String login;
    public String password;
    public String firstName;

    public Courier(String login, String password, String firstName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier(){
        }

    public static Courier getRandom(){
        return Courier.getRandom(true, true, true);
    }

    public static Courier getRandom(boolean userLogin, boolean userPassword, boolean userFirstName){

        String login = "";
        String password = "";
        String firstName = "";

        if (userLogin){
            login = RandomStringUtils.randomAlphabetic(10);
        }
        if (userPassword){
            password = RandomStringUtils.randomAlphabetic(10);
        }
        if (userFirstName){
            firstName = RandomStringUtils.randomAlphabetic(10);
        }

        return new Courier(login, password, firstName);
    }
}