package de.uniba.dsg.jaxrs.model;

import de.uniba.dsg.jaxrs.model.logic.BeverageType;

public interface Beverage {
    public int getId();
    public void setId(int id);
    public double getPrice();
    public void setPrice(double price);
    public  int getInStock();
    public void setInStock(int inStock);
    public BeverageType getBeverageType();
    public void setBeverageType(BeverageType beverageType);
}
