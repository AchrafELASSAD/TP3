package dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("daoApi")
@Profile("api")
public class DaoApi implements IDao {
    @Override
    public double getValue() {
        System.out.println("→ DAO API utilisé");
        return 220.0;
    }
}
