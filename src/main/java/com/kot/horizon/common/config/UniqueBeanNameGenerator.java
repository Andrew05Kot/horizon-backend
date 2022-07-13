package com.kot.horizon.common.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class UniqueBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        //use fully-qualified name as beanName
        return definition.getBeanClassName();
    }

}