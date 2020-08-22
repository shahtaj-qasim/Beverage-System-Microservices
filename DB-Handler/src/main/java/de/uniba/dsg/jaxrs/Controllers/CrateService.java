package de.uniba.dsg.jaxrs.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.uniba.dsg.jaxrs.model.logic.Crate;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class CrateService {

    public static CrateService instance = null;
    private static final Logger logger = Logger.getLogger("CrateService");


    static {
        try {
            instance = new CrateService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ObjectMapper objectMapper = new ObjectMapper();
    TypeReference<List<Crate>> typeReference = new TypeReference<List<Crate>>(){};
    List<Crate> crate= objectMapper.readValue(new File("crate.json"), typeReference);

    public CrateService() throws IOException {
    }

    public List<Crate> getAllBeverages() {
        return crate;
    }

    public Crate createCrate(Crate newCrate) {
        newCrate.setId(crate.stream().map(Crate::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        crate.add(newCrate);
        return newCrate;
    }
    public Crate getCrateById(final int id) {
        return crate.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public Crate updateCrate(Crate cra,int crateId) {
        Crate dbCrate = crate.stream().filter(c -> c.getId() == crateId).findFirst().orElse(null);
        if(cra == null || dbCrate == null) {
            return null;
        }

        dbCrate.setBottle(cra.getBottle());
        dbCrate.setNoOfBottles(cra.getNoOfBottles());
        dbCrate.setPrice(cra.getPrice());
        dbCrate.setInStock(cra.getInStock());
        dbCrate.setBeverageType(cra.getBeverageType());

        return dbCrate;

    }

    public Crate deleteCrate(int crateId) {
        Crate dbBottle = crate.stream().filter(c -> c.getId() == crateId).findFirst().orElse(null);
        crate.remove(dbBottle);

        return dbBottle;
    }

}