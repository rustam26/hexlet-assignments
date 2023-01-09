package exercise;

import exercise.calculator.Calculator;
import exercise.calculator.CalculatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeanPostProcessor.class);
    private final Map<String, String> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass()
                .isAnnotationPresent(Inspect.class)) {
            map.put(beanName, bean.getClass()
                    .getAnnotation(Inspect.class)
                    .level());
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (map.containsKey(beanName)) {
            CalculatorImpl calculatorImpl = (CalculatorImpl) bean;
            ClassLoader classLoader = calculatorImpl.getClass()
                    .getClassLoader();
            Class<?>[] interfaces = calculatorImpl.getClass()
                    .getInterfaces();
            Calculator calculator = (Calculator) Proxy.newProxyInstance(
                    classLoader,
                    interfaces,
                    (proxy, method, args) -> {
                        switch (map.get(beanName)) {
                            case "info" -> LOGGER.info("Was called method: {}() with arguments: {}",
                                    method.getName(), Arrays.toString(args));
                            case "debug" -> LOGGER.debug("Was called method: {}() with arguments: {}",
                                    method.getName(), Arrays.toString(args));
                            default -> LOGGER.debug("default");
                        }
                        return method.invoke(calculatorImpl, args);
                    }
            );
            return BeanPostProcessor.super.postProcessAfterInitialization(calculator, beanName);
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
// END
