package de.uniba.dsg.jaxrs.model.api;

import de.uniba.dsg.jaxrs.model.dto.BeverageDTO;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;

@XmlRootElement
@XmlType(propOrder = { "pagination", "beverages", "href" })
public class PaginatedBeverages
{
    private Pagination pagination;
    private BeverageDTO beverages;

    private URI href;

    public PaginatedBeverages() {}

    public PaginatedBeverages(final Pagination pagination, final BeverageDTO beverages, final URI href)
    {
        this.pagination = pagination;
        this.beverages = beverages;
        this.href = href;
    }

    public Pagination getPagination()
    {
        return this.pagination;
    }

    public void setPagination(final Pagination pagination)
    {
        this.pagination = pagination;
    }

    public BeverageDTO getBeverages()
    {
        return beverages;
    }

    public void setBeverages(BeverageDTO beverages)
    {
        this.beverages = beverages;
    }

    public URI getHref()
    {
        return this.href;
    }

    public void setHref(final URI href)
    {
        this.href = href;
    }
}
