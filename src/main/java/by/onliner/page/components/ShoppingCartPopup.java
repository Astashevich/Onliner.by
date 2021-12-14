package by.onliner.page.components;

import by.onliner.elements.widget.Button;
import org.openqa.selenium.support.FindBy;
import by.onliner.page.AbstractComponent;
import by.onliner.utils.Waiter;

public class ShoppingCartPopup extends AbstractComponent {

    @FindBy(className = "product-recommended__sidebar-close")
    private Button shoppingCartPopupCloseButton;

    public ShoppingCartPopup() {
        super();
    }

    @Override
    public void waitForComponentOpened() {
        Waiter.elementToBeClickable(shoppingCartPopupCloseButton);
    }

    /***
     * Checks if a Shopping cart popup is visible
     * @return visibility of popup
     */
    public boolean isShoppingCartPopupVisible() {
        return shoppingCartPopupCloseButton.isDisplayed();
    }

    /***
     * Click on the popup close button.
     */
    public void closeShoppingCartPopup() {
        shoppingCartPopupCloseButton.click();
    }
}
