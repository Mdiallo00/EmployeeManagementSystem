class Division {

    public int divisionID;
    private String divisionName;
    private String city;
    private String addressLine1;
    private String addresssLine2;
    private String state;
    private String country;
    private String postalCode;
    
    
    public int getDivisionID() {
        return divisionID;
    }


    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }


    public String getDivisionName() {
        return divisionName;
    }


    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getAddressLine1() {
        return addressLine1;
    }


    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }


    public String getAddresssLine2() {
        return addresssLine2;
    }


    public void setAddresssLine2(String addresssLine2) {
        this.addresssLine2 = addresssLine2;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getPostalCode() {
        return postalCode;
    }


    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    public Division(int divisionID, String divisionName, String city, String addressLine1, String addresssLine2,
            String state, String country, String postalCode) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.addresssLine2 = addresssLine2;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }



    
}