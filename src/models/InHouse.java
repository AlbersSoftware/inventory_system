package models;
public class InHouse extends Part {
    private int machineID;
    /**
     *set the parameters for outsourced and this.company name
     * @param id the id
     * @param name the name
     * @param price the price
     * @param inv the stock
     * @param min the min
     * @param max the max
     * @param machineID the machines number
     */
    public InHouse(int id, String name, double price, int inv, int min, int max, int machineID) {
        super(id, name, price, inv, min, max);
        this.machineID = machineID;
    }

    /**
     * This sets the machineID
     * @param machineID given for inhouse
     */

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * This gets the machineID
     * @return the machineID
     */
    public int getMachineID() {

        return machineID;
    }

}
