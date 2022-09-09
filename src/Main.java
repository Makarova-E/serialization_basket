import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Basket basket;
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        File file = new File("basket.txt");
        if (file.exists()) {
            basket = Basket.loadFromTxtFile(file);
        } else {
            basket = new Basket(products, prices);
        }

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i]);
        }

        while (true) {
            int productNumber = 0; //номер продукта, введенный пользователем
            int productCount = 0; // кол-во, введенное пользователем

            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] parts = input.split(" "); // 1 4
            productNumber = Integer.parseInt(parts[0]) - 1; //0
            productCount = Integer.parseInt(parts[1]); //3
            basket.addToCart(productNumber, productCount);
        }
        basket.printCart();
        basket.saveTxt(file);
    }
}
