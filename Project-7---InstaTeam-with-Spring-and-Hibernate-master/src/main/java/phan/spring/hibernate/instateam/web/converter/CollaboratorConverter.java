package phan.spring.hibernate.instateam.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import phan.spring.hibernate.instateam.dao.CollaboratorDao;
import phan.spring.hibernate.instateam.model.Collaborator;

import java.util.HashSet;
import java.util.Set;

@Component
public class CollaboratorConverter implements Converter<String, Collaborator> {
    @Autowired
    private CollaboratorDao collaboratorDao;

    @Override
    public Collaborator convert(String source) {

        return collaboratorDao.findById(Long.parseLong(source));
    }

    @Bean
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new CollaboratorConverter());
        bean.setConverters(converters);
        return bean.getObject();
    }
}
