package courier;
public class CourierCredentials {
    private final String login;
    private final String password;
    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public CourierCredentials(Courier courier) {
        this.login = courier.getLogin();
        this.password = courier.getPassword();
    }
    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier);
    }

    @Override
    public String toString() {
        return "{ login= \"" + login + "\",password= \"" + password + "\" }";
    }
}
