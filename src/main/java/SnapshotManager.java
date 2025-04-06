public class SnapshotManager {
    private final ServiceInstance si;

    public SnapshotManager(ServiceInstance si) {
        this.si = si;
    }

    public void createSnapshot(JSONObject config) throws Exception {
        Folder rootFolder = si.getRootFolder();
        String prefix = config.getString("vm_prefix");
        ManagedEntity[] vms = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");

        for (ManagedEntity me : vms) {
            VirtualMachine vm = (VirtualMachine) me;
            if (vm.getName().startsWith(prefix)) {
                Task snapshotTask = vm.createSnapshot_Task(config.getString("snapshot_name"), "Auto snapshot", false, false);
                System.out.println("Creating snapshot for: " + vm.getName());
                snapshotTask.waitForTask();
            }
        }
    }
}
