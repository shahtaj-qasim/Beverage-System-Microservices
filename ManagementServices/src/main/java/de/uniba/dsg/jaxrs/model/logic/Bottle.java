package de.uniba.dsg.jaxrs.model.logic;

import de.uniba.dsg.jaxrs.model.Beverage;




public class Bottle implements Beverage {

    private int id;
    private String name;
    private double volume;
    private boolean isAlcoholic;
    private double volumePercent;
    private double price;
    private String supplier;
    private int inStock;
    private BeverageType beverageType;

    public Bottle(int id, String name, double volume, boolean isAlcoholic, double volumePercent, double price, String supplier, int inStock) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.isAlcoholic = isAlcoholic;
        this.volumePercent = volumePercent;
        this.price = price;
        this.supplier = supplier;
        this.inStock = inStock;
        this.beverageType=  BeverageType.BOTTLE;
    }

    public Bottle(String name, double volume, boolean isAlcoholic, double volumePercent, double price, String supplier, int inStock) {
        this.name = name;
        this.volume = volume;
        this.isAlcoholic = isAlcoholic;
        this.volumePercent = volumePercent;
        this.price = price;
        this.supplier = supplier;
        this.inStock = inStock;
        this.beverageType=  BeverageType.BOTTLE;

    }
    public Bottle() {
        this.beverageType= BeverageType.BOTTLE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean getisAlcoholic() {
        return isAlcoholic;
    }

    public void setisAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public de.uniba.dsg.jaxrs.model.logic.BeverageType getBeverageType() { return beverageType.BOTTLE; }

    public void setBeverageType(de.uniba.dsg.jaxrs.model.logic.BeverageType beverageType) { this.beverageType = beverageType.BOTTLE; }

    @Override
    public String toString() {
        return "Bottle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", volume=" + volume +
                ", isAlcoholic=" + isAlcoholic +
                ", volumePercent=" + volumePercent +
                ", price=" + price +
                ", supplier='" + supplier + '\'' +
                ", inStock=" + inStock +
                '}';
    }
}