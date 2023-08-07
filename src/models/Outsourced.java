package models;

public class Outsourced extends Part {
    private String companyName;

    /**
     *set the parameters for outsourced and this.company name
     * @param id the id
     * @param name the name
     * @param price the price
     * @param stock the stock
     * @param min the min
     * @param max the max
     * @param companyName the company name
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This gets the company name
     * @return the company name for outsourced
     */
    public String getCompanyName() {

        return companyName;
    }

    /**
     * This sets the company name
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}

