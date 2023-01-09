// Постпроцессор бинов
// Используется, если нужно кастомизировать работу с бинами
// Интерфейс содержит два метода
// Первый выполняется перед инициализацией бина, второй после

@Component
public class PostProcessor implements BeanPostProcessor {

    @Override
    // Метод вызывается перед инициализацией бина
    // Принимет сам бин и имя бина
    // Должен вернуть бин
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("Called PostProcessBeforeInitialization: " + beanName);
        return bean;
    }

    @Override
    // Метод вызывается после инициализацией бина
    // Должен вернуть бин
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("Called PostProcessAfterInitialization: " + beanName);
        return bean;
    }
}
