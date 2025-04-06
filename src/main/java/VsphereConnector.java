public class VsphereConnector {
    public static ServiceInstance connect(String url, String username, String password) throws Exception {
        return new ServiceInstance(new URL(url), username, password, true);
    }
}
