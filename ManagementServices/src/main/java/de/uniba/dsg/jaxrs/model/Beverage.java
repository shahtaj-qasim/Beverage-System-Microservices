package de.uniba.dsg.jaxrs.model;

import de.uniba.dsg.jaxrs.model.logic.BeverageType;

public interface Beverage {
    public int getId();
    public void setId(int id);
    public double getPrice();
    public void setPrice(double price);
    public BeverageType getBeverageType();
    public void setBeverageType(BeverageType beverageType);
}
