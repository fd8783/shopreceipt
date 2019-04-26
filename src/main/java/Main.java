import Controller.ShoppingCartController;
import Model.TaxCalculatorModel;
import View.ShoppingCartView;

public class Main {

    public static void main(String[] args) {
        ShoppingCartView shoppingCartView = new ShoppingCartView();
        TaxCalculatorModel taxCalculatorModel = new TaxCalculatorModel();

        ShoppingCartController shoppingCartController = new ShoppingCartController(shoppingCartView, taxCalculatorModel);

        shoppingCartView.setVisible(true);
    }
}
