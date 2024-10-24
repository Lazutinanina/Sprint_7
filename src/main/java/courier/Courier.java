package courier;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private final String login;
    private final String password;
    private final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public static Courier getRandom() {
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }
    public static Courier getRandomRequiredField() {
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = "";
        return new Courier(login, password, firstName);
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "{ login= \"" + login + "\",password= \"" + password + "\", firstName= \"" + firstName + "\" }";
    }
}
