package di;

/**
 * @作者 liudazhi
 * @创建日期 2017/6/28
 */

public interface HasComponent <C extends BaseComponent>{
    C getComponent();
}
