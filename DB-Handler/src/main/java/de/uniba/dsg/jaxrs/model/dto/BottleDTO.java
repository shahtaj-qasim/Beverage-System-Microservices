package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.logic.BeverageType;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.resources.DbHandlerBottle;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
@XmlType(propOrder = {"id","name", "volume", "isAlcoholic", "volumePercent", "supplier","price", "inStock", "href"})
public class BottleDTO
{
    @XmlElement(required = true)
    private int id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private double volume;
    @XmlElement(required = true)
    private boolean isAlcoholic;
    @XmlElement(required = true)
    private double volumePercent;
    @XmlElement(required = true)
    private String supplier;
    @XmlElement(required = true)
    private double price;
    @XmlElement(required = true)
    private int inStock;
//    @XmlElement(required = true)
//    private BeverageType beverageType;
    @XmlElement(required = false)
    private URI href;

    public BottleDTO()
    {
    }

    public BottleDTO(final Bottle bottle, final URI baseUri)
    {
        this.id = bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.getisAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
        this.price = bottle.getPrice();
//        this.beverageType = bottle.getBeverageType();
        this.href = UriBuilder.fromUri(baseUri).path(DbHandlerBottle.class)
                .path(DbHandlerBottle.class, "getBottles").build(this.id);
    }
    public BottleDTO(Bottle bottle)
    {
        this.id=bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.getisAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
        this.price = bottle.getPrice();
//        this.beverageType = bottle.getBeverageType();

    }

    public static List<BottleDTO> marshall(final List<Bottle> bottlesList, final URI baseUri)
    {
        final ArrayList<BottleDTO> bottles = new ArrayList<>();
        for (final Bottle bottle : bottlesList)
        {
            bottles.add(new BottleDTO(bottle, baseUri));
        }
        return bottles;
    }

    public Bottle MapToBottle(){
        Bottle bottle= new Bottle();
        bottle.setId(this.id);
        bottle.setName(this.name);
        bottle.setVolume(this.volume);
        bottle.setisAlcoholic(this.isAlcoholic);
        bottle.setVolumePercent(this.volumePercent);
        bottle.setPrice(this.price);
        bottle.setSupplier(this.supplier);
        bottle.setInStock(this.inStock);
//        bottle.setBeverageType(this.beverageType);
        return bottle;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getVolume()
    {
        return volume;
    }

    public void setVolume(double volume)
    {
        this.volume = volume;
    }

    public boolean isAlcoholic()
    {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean isAlcoholic)
    {
        this.isAlcoholic = isAlcoholic;
    }

    public double getVolumePercent()
    {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent)
    {
        this.volumePercent = volumePercent;
    }

    public String getSupplier()
    {
        return supplier;
    }

    public void setSupplier(String supplier)
    {
        this.supplier = supplier;
    }

    public int getInStock()
    {
        return inStock;
    }

    public void setInStock(int inStock)
    {
        this.inStock = inStock;
    }

    public URI getHref()
    {
        return this.href;
    }

    public void setHref(final URI href)
    {
        this.href = href;
    }

    @Override
    public String toString()
    {
        return "BottleDTO [href=" + href + ", id=" + id + ", inStock=" + inStock + ", isAlcoholic=" + isAlcoholic
                + ", name=" + name + ", price=" + price + ", supplier=" + supplier
                + ", volume=" + volume + ", volumePercent=" + volumePercent + "]";
    }

//    public Beverage unmarshall()
//    {
//        return new Bottle(id, name, volume, isAlcoholic, volumePercent, price, supplier, inStock);
//    }
}
