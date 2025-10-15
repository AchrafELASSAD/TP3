import metier.IMetier;
import org.junit.After;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import presentation.Presentation2;

import static org.junit.Assert.assertEquals;
public class OcpSelectionTest {

    private AnnotationConfigApplicationContext ctx;

    @After
    public void tearDown() {
        if (ctx != null) {
            ctx.close();
        }
    }


    @Test
    public void devProfile_choisitDao2_300() {
        // Arrange
        ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("dev");  // DaoImpl2 (150)
        ctx.register(Presentation2.class);
        ctx.refresh();

        // Act
        IMetier m = ctx.getBean(IMetier.class);
        double resultat = m.calcul();

        // Assert
        assertEquals("Profil 'dev' doit utiliser DaoImpl2 (150 * 2)",
                300.0, resultat, 1e-6);
    }

    @Test
    public void prodProfile_choisitDao_200() {
        // Arrange
        ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("prod");  // DaoImpl (100)
        ctx.register(Presentation2.class);
        ctx.refresh();

        // Act
        IMetier m = ctx.getBean(IMetier.class);
        double resultat = m.calcul();

        // Assert
        assertEquals("Profil 'prod' doit utiliser DaoImpl (100 * 2)",
                200.0, resultat, 1e-6);
    }

    @Test
    public void fileProfile_choisitDaoFile_360() {
        // Arrange
        ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("file");  // DaoFile (180)
        ctx.register(Presentation2.class);
        ctx.refresh();

        // Act
        IMetier m = ctx.getBean(IMetier.class);
        double resultat = m.calcul();

        // Assert
        assertEquals("Profil 'file' doit utiliser DaoFile (180 * 2)",
                360.0, resultat, 1e-6);
    }

    @Test
    public void apiProfile_choisitDaoApi_440() {
        // Arrange
        ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("api");  // DaoApi (220)
        ctx.register(Presentation2.class);
        ctx.refresh();

        // Act
        IMetier m = ctx.getBean(IMetier.class);
        double resultat = m.calcul();

        // Assert
        assertEquals("Profil 'api' doit utiliser DaoApi (220 * 2)",
                440.0, resultat, 1e-6);
    }


    @Test
    public void propertySelection_dao_200() {
        // Arrange - Définir la propriété AVANT la création du contexte
        System.setProperty("dao.target", "dao");

        try {
            ctx = new AnnotationConfigApplicationContext();
            ctx.register(Presentation2.class);
            ctx.refresh();

            // Act
            IMetier m = ctx.getBean(IMetier.class);
            double resultat = m.calcul();

            // Assert
            assertEquals("dao.target=dao doit utiliser DaoImpl (100 * 2)",
                    200.0, resultat, 1e-6);
        } finally {
            System.clearProperty("dao.target");
        }
    }

    @Test
    public void propertySelection_dao2_300() {
        // Arrange
        System.setProperty("dao.target", "dao2");

        try {
            ctx = new AnnotationConfigApplicationContext();
            ctx.register(Presentation2.class);
            ctx.refresh();

            // Act
            IMetier m = ctx.getBean(IMetier.class);
            double resultat = m.calcul();

            // Assert
            assertEquals("dao.target=dao2 doit utiliser DaoImpl2 (150 * 2)",
                    300.0, resultat, 1e-6);
        } finally {
            System.clearProperty("dao.target");
        }
    }

    @Test
    public void propertySelection_daoFile_360() {
        // Arrange
        System.setProperty("dao.target", "daoFile");

        try {
            ctx = new AnnotationConfigApplicationContext();
            ctx.register(Presentation2.class);
            ctx.refresh();

            // Act
            IMetier m = ctx.getBean(IMetier.class);
            double resultat = m.calcul();

            // Assert
            assertEquals("dao.target=daoFile doit utiliser DaoFile (180 * 2)",
                    360.0, resultat, 1e-6);
        } finally {
            System.clearProperty("dao.target");
        }
    }

    @Test
    public void propertySelection_daoApi_440() {
        // Arrange
        System.setProperty("dao.target", "daoApi");

        try {
            ctx = new AnnotationConfigApplicationContext();
            ctx.register(Presentation2.class);
            ctx.refresh();

            // Act
            IMetier m = ctx.getBean(IMetier.class);
            double resultat = m.calcul();

            // Assert
            assertEquals("dao.target=daoApi doit utiliser DaoApi (220 * 2)",
                    440.0, resultat, 1e-6);
        } finally {
            System.clearProperty("dao.target");
        }
    }
}