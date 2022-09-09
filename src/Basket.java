import java.io.*;

public class Basket implements Serializable {
    protected String[] products;
    protected int[] prices;
    protected int[] orderedProducts;
    protected int sumProducts;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        orderedProducts = new int[products.length];
    }

    // метод добавления amount штук продукта номер productNum в корзину;
    void addToCart(int productNum, int amount) { //0,2
        orderedProducts[productNum] += amount; // [0] = 2
        sumProducts += amount * prices[productNum]; //
    }

    // метод вывода на экран покупательской корзины
    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < orderedProducts.length; i++) {
            if (orderedProducts[i] != 0) {
                System.out.println(products[i] + " " + orderedProducts[i] + " шт " +
                        prices[i] + " руб/шт " +
                        orderedProducts[i] * prices[i] + " руб в сумме"
                );
            }
        }
        System.out.println("Итого " + sumProducts + " руб");
    }

    public void saveBin(File file) throws IOException {
        ObjectOutputStream dos = new ObjectOutputStream(new FileOutputStream("basket.bin"));
        dos.writeObject(this);
        dos.close();
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        Basket basket = (Basket) in.readObject();
        in.close();
        return basket;
    }
}
