package de.uniba.dsg.jaxrs.model.logic;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum(String.class)
public enum BeverageType {
    BOTTLE, CRATE;
}
