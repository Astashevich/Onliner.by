package by.onliner.elements.factory;

import by.onliner.elements.base.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a list of WebElements in multiple wrapped by.onliner.elements.
 */
public class ElementListHandler implements InvocationHandler {

    private final ElementLocator locator;
    private final Class<?> wrappingType;

    /**
     * Given an interface and a locator, apply a wrapper over a list of by.onliner.elements.
     *
     * @param interfaceType interface type we're trying to wrap around the element.
     * @param locator       locator on the by.onliner.page for the by.onliner.elements.
     * @param <T>           type of the interface.
     */
    public <T> ElementListHandler(Class<T> interfaceType, ElementLocator locator) {
        this.locator = locator;
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }
        this.wrappingType = ImplementedByProcessor.getWrapperClass(interfaceType);

    }

    /**
     * Executed on invoke of the requested proxy. Used to gather a list of wrapped WebElements.
     *
     * @param o       object to invoke on
     * @param method  method to invoke
     * @param objects parameters for method
     * @return return value from method
     * @throws Throwable when frightened.
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        List<Object> wrappedList = new ArrayList<Object>();
        Constructor<?> cons = wrappingType.getConstructor(WebElement.class);
        for (WebElement element : locator.findElements()) {
            Object thing = cons.newInstance(element);
            wrappedList.add(wrappingType.cast(thing));
        }
        try {
            return method.invoke(wrappedList, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
