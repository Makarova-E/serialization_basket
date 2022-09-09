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
    void addToCart(int productNum, int amount) { //0,3
        orderedProducts[productNum] += amount; // [0] = 3
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

    //метод сохранения корзины в текстовый файл; использовать встроенные сериализаторы нельзя;
    public void saveTxt(File textFile) throws IOException {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(textFile))) {
            for (String product : products) {
                br.write(product + " ");
            }
            br.newLine();
            for (int basketProducts : orderedProducts) {
                br.write(basketProducts + " ");
            }
            br.newLine();
            for (int price : prices)
                br.write(price + " ");
            br.newLine();
            br.write(String.valueOf(sumProducts));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //статический(!) метод восстановления объекта корзины из текстового файла
    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = null;
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String[] products = br.readLine().split(" ");
            String[] ordProducts = br.readLine().split(" ");
            int[] orderedProducts = new int[products.length];
            for (int i = 0; i < ordProducts.length; i++) {
                orderedProducts[i] = Integer.parseInt(ordProducts[i]);
            }
            String[] price = br.readLine().split(" ");
            int[] prices = new int[products.length];
            for (int i = 0; i < prices.length; i++) {
                prices[i] = Integer.parseInt(price[i]);
            }
            basket = new Basket(products, prices);
            basket.orderedProducts = orderedProducts;
            basket.sumProducts = Integer.parseInt(br.readLine());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return basket;
    }
}
