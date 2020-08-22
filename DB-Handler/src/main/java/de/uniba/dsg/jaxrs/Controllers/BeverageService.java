package de.uniba.dsg.jaxrs.Controllers;
import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Crate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeverageService {

        public static BeverageService instance = null;

        static {
            try {
                instance = new BeverageService();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public BeverageService() throws IOException {
        }

        BottleService bottleService = new BottleService();
        CrateService crateService = new CrateService();

        List<Bottle> bottleList = bottleService.getAllBeverages();
        List<Crate> crateList = crateService.getAllBeverages();

        List<Beverage> beverageList = new ArrayList<>();

        public List<Beverage> getAllBeverages() {
            beverageList.addAll(bottleList);
            beverageList.addAll(crateList);
            System.out.println(beverageList);
            return beverageList;
        }
    }
