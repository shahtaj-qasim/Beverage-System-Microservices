package de.uniba.dsg.jaxrs.model.logic;

import de.uniba.dsg.jaxrs.model.Beverage;

import java.util.List;

public class BottleDB {

    List<BottleList> bottles;

    public BottleDB(List<BottleList> bottles) {
        this.bottles = bottles;
    }

    public static class BottleList {
        private int id;
        private String name;
        private double volume;
        private boolean isAlcoholic;
        private double volumePercent;
        private double price;
        private String supplier;
        private int inStock;
        private BeverageType beverageType;


        public BottleList(int id, String name, double volume, boolean isAlcoholic, double volumePercent, double price, String supplier, int inStock) {
            this.id = id;
            this.name = name;
            this.volume = volume;
            this.isAlcoholic = isAlcoholic;
            this.volumePercent = volumePercent;
            this.price = price;
            this.supplier = supplier;
            this.inStock = inStock;
            this.beverageType = BeverageType.BOTTLE;

        }

        public BottleList(int id, String name) {
            this.id = id;
            this.name = name;

        }
    }
}