package com.example.myparkweb.config;



import com.example.myparkweb.DTO.parking.ShowParkingDetailedInfoDto;
import com.example.myparkweb.models.entities.Parking;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableCaching
@EnableJpaAuditing
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);


        mapper.typeMap(Parking.class, ShowParkingDetailedInfoDto.class)
                .addMappings(m -> m.skip(ShowParkingDetailedInfoDto::setReviews));

        return mapper;
    }
}
