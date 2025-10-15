package config;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:app.properties")
public class PropertyDrivenConfig {

    @Value("${dao.target:dao}")
    private String target;

    @Bean(name = "dao")
    @Primary
    public IDao selectedDao(@Lazy Map<String, IDao> candidates) {
        // Exclure le bean "dao" lui-même pour éviter la référence circulaire
        Map<String, IDao> filteredCandidates = candidates.entrySet().stream()
                .filter(entry -> !"dao".equals(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        IDao bean = filteredCandidates.get(target);
        if (bean == null) {
            throw new IllegalArgumentException("Implémentation inconnue: " + target + " (dao2|daoFile|daoApi)");
        }
        return bean;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}