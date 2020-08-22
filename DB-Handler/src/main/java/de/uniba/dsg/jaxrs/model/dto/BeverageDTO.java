package de.uniba.dsg.jaxrs.model.dto;

import de.uniba.dsg.jaxrs.model.Beverage;

import javax.xml.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beverage")
@XmlType(propOrder = {"beverages"})
@XmlSeeAlso({ de.uniba.dsg.jaxrs.model.dto.BottleDTO.class, CrateDTO.class })
public class BeverageDTO
{
    private static final Logger LOGGER = Logger.getLogger(BeverageDTO.class.getName());

    @XmlElement(required = true)
    List<Beverage> beverages;

    public BeverageDTO(final List<Beverage> beverage, URI baseUri) {
        this.beverages = beverage;
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<Beverage> beverages) {
        this.beverages = beverages;
    }

    public BeverageDTO() {}

    public BeverageDTO marshall(final List<Beverage> beverageList, final URI baseUri) {

        this.beverages = beverageList;

        return this;
    }

}
