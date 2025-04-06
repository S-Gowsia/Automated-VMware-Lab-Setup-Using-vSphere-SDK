public class VmDeployer {
    private final ServiceInstance si;

    public VmDeployer(ServiceInstance si) {
        this.si = si;
    }

    public void deployVMs(JSONObject config) throws Exception {
        Folder rootFolder = si.getRootFolder();
        String datacenterName = config.getString("datacenter");
        String clusterName = config.getString("cluster");

        Datacenter dc = (Datacenter) new InventoryNavigator(rootFolder).searchManagedEntity("Datacenter", datacenterName);
        ResourcePool rp = (ResourcePool) new InventoryNavigator(dc).searchManagedEntity("ResourcePool", "Resources");
        VirtualMachine templateVM = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", config.getString("template"));

        for (int i = 1; i <= config.getInt("vm_count"); i++) {
            String vmName = config.getString("vm_prefix") + "-" + i;
            Task cloneTask = templateVM.cloneVM_Task((Folder) templateVM.getParent(), vmName, new VirtualMachineCloneSpec() {{
                setLocation(new VirtualMachineRelocateSpec() {{
                    setPool(rp.getMOR());
                }});
                setPowerOn(true);
                setTemplate(false);
            }});
            System.out.println("Cloning VM: " + vmName);
            cloneTask.waitForTask();
        }
    }
}
