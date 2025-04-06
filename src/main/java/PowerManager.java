public class PowerManager {
    private final ServiceInstance si;

    public PowerManager(ServiceInstance si) {
        this.si = si;
    }

    public void powerAction(JSONObject config, String action) throws Exception {
        Folder rootFolder = si.getRootFolder();
        ManagedEntity[] vms = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");

        for (ManagedEntity me : vms) {
            VirtualMachine vm = (VirtualMachine) me;
            if (vm.getName().startsWith(config.getString("vm_prefix"))) {
                if ("on".equalsIgnoreCase(action)) {
                    vm.powerOnVM_Task(null).waitForTask();
                    System.out.println("Powered ON: " + vm.getName());
                } else if ("off".equalsIgnoreCase(action)) {
                    vm.powerOffVM_Task().waitForTask();
                    System.out.println("Powered OFF: " + vm.getName());
                }
            }
        }
    }
}
