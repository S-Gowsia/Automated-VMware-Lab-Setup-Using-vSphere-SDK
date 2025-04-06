public class Main {
    public static void main(String[] args) throws Exception {
        JSONObject config = new JSONObject(Files.readString(Paths.get("config/vm-config.json")));

        ServiceInstance si = VsphereConnector.connect("https://" + config.getString("vcenter") + "/sdk",
                                                      config.getString("username"),
                                                      config.getString("password"));

        new VmDeployer(si).deployVMs(config);
        new SnapshotManager(si).createSnapshot(config);
        new PowerManager(si).powerAction(config, "on");

        si.getServerConnection().logout();
    }
}
