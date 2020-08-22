package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.logic.Crate;
import de.uniba.dsg.jaxrs.resources.DbHandlerBottle;
import de.uniba.dsg.jaxrs.resources.DbHandlerCrate;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = { "id",  "bottle", "noOfBottles", "inStock", "price", "href" })
public class CrateDTO
{
    @XmlElement(required = true)
    private int id;
    @XmlElement(required = true)
    private BottleDTO bottle;
    @XmlElement(required = true)
    private int noOfBottles;
    @XmlElement(required = false)
    private int inStock;
    @XmlElement(required = false)
    private double price;
    @XmlElement(required = false)
    private URI href;


    public CrateDTO()
    {
    }

    public CrateDTO(final Crate crate, final URI baseUri)
    {
        this.id = crate.getId();
        this.bottle = new BottleDTO(crate.getBottle(), baseUri);
        this.noOfBottles = crate.getNoOfBottles();
        this.inStock = crate.getInStock();
        this.price = crate.getPrice();
        this.href = UriBuilder.fromUri(baseUri).path(DbHandlerCrate.class)
                .path(DbHandlerCrate.class, "getCrates").build(this.id);
    }

    public CrateDTO(final Crate crate)
    {
        this.id = crate.getId();
        this.bottle = new BottleDTO(crate.getBottle());
        this.noOfBottles = crate.getNoOfBottles();
        this.inStock = crate.getInStock();
        this.price = crate.getPrice();

    }

    public Crate MapToCrate() {
        Crate crate = new Crate();
        crate.setId(this.id);
        crate.setBottle(this.bottle.MapToBottle());
        crate.setNoOfBottles(this.noOfBottles);
        crate.setPrice(this.price);
        crate.setInStock(this.inStock);
        return crate;
    }

    public static List<CrateDTO> marshall(final List<Crate> crates, final URI baseUri)
    {
        final ArrayList<CrateDTO> crateDTOs = new ArrayList<>();
        for (final Crate crt : crates)
        {
            crateDTOs.add(new CrateDTO(crt, baseUri));
        }
        return crateDTOs;
    }

    public BottleDTO getBottle()
    {
        return bottle;
    }

    public void setBottle(BottleDTO bottle)
    {
        this.bottle = bottle;
    }

    public int getNoOfBottles()
    {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles)
    {
        this.noOfBottles = noOfBottles;
    }

    public int getInStock()
    {
        return inStock;
    }

    public void setInStock(int inStock)
    {
        this.inStock = inStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public URI getHref()
    {
        return href;
    }

    public void setHref(URI href)
    {
        this.href = href;
    }

    @Override
    public String toString()
    {
        return "CrateDTO [bottle=" + bottle + ", href=" + href + ", id=" + id + ", inStock="
                + inStock + ", noOfBottles=" + noOfBottles + ", price=" + price + "]";
    }

//    public Beverage unmarshall()
//    {
//        return new Crate(id, (Bottle) bottle.unmarshall(), noOfBottles, price, inStock);
//    }
}
