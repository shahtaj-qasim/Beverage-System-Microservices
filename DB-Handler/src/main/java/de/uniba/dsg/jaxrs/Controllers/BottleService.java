package de.uniba.dsg.jaxrs.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.uniba.dsg.jaxrs.model.logic.Bottle;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class BottleService {

    public static BottleService instance = null;
    private static final Logger logger = Logger.getLogger("BottleService");


    static {
        try {
            instance = new BottleService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ObjectMapper objectMapper = new ObjectMapper();
    TypeReference<List<Bottle>> typeReference = new TypeReference<List<Bottle>>(){};
    List<Bottle> bottle= objectMapper.readValue(new File("bottle.json"), typeReference);

    public BottleService() throws IOException {
    }

    public List<Bottle> getAllBeverages() {

        return bottle;
    }

    public Bottle getBottleById(final int id) {
        return bottle.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }
    public Bottle createBottle(Bottle newBottle) {
        int temp = bottle.stream().map(Bottle::getId).max(Comparator.naturalOrder()).orElse(0) + 1;
        newBottle.setId(bottle.stream().map(Bottle::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        bottle.add(newBottle);
        return newBottle;
    }

    public Bottle updateBottle(Bottle bot,int bottleId) {
        Bottle dbBottle = bottle.stream().filter(c -> c.getId() == bottleId).findFirst().orElse(null);
        if(bot == null || dbBottle == null) {
            return null;
        }
        dbBottle.setName(bot.getName());
        dbBottle.setVolume(bot.getVolume());
        dbBottle.setisAlcoholic(bot.getisAlcoholic());
        dbBottle.setVolumePercent(bot.getVolumePercent());
        dbBottle.setPrice(bot.getPrice());
        dbBottle.setSupplier(bot.getSupplier());
        dbBottle.setInStock(bot.getInStock());
        return dbBottle;

    }

    public Bottle deleteBottle(int bottleId) {
        Bottle dbBottle = bottle.stream().filter(c -> c.getId() == bottleId).findFirst().orElse(null);
        bottle.remove(dbBottle);

        return dbBottle;
    }

}