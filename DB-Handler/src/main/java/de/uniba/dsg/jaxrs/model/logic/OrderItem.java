package de.uniba.dsg.jaxrs.model.logic;

import de.uniba.dsg.jaxrs.model.Beverage;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderItem")
@XmlType(propOrder = {"number", "beverage", "quantity"})
public class OrderItem {
    private int number;
    private Beverage beverage;
    private int quantity;

    public OrderItem(int number, Beverage beverage, int quantity) {
        this.number = number;
        this.beverage = beverage;
        this.quantity = quantity;
    }
    public OrderItem(){

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "number=" + number +
                ", beverage=" + beverage +
                ", quantity=" + quantity +
                '}';
    }
}
