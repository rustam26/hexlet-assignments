// Состояния

enum States {
    ON,
    OFF
}

// События
enum Events {
    SWITCH_ON,
    SWITCH_OFF
}

// Переходы

// SWITCH_ON: OFF -> ON
// SWITCH_OFF: ON -> OFF



// Модель

public class TV {
    // Начальное состояние
    private States state = States.OFF

    public boolean switchOn() {
        // Если переход возможен
        if (state == States.OFF) {
            // Меняем состояние
            state = States.ON;
            return true;
        }
        // Если переход не возможен
        return false;
    }

    public boolean switchOff() {
        if (state == States.ON) {
            state = States.OFF;
            return true;
        }
        return false;
    }
}

// Конторллер

@RestController
@RequestMapping("/tv")
public class TVController {

    @Autowired
    private TVRepository tvRepository;

    @PatchMapping(path = "/{id}/on")
    public Post switchOn(@PathVariable long id) {
        TV tv = tvRepository.findById(id);

        // Вызывается событие ON
        // Если переход возможен, устанавливаем новое состояние
        if (tv.on()) {
            return tvRepository.save(tv);
        }

        throw new UnprocessableEntityException("Tv is already switched on");
    }

    @PatchMapping(path = "/{id}/off")
    public Post switchOff(@PathVariable long id) {

        TV tv = tvRepository.findById(id);

        if (post.off()) {
            return tvRepository.save(tv);
        }

        throw new UnprocessableEntityException("Tv is already switched off");
    }
}
