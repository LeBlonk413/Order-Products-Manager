import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Tema2 {

	public static void main(String[] args) throws IOException {
		int no_threads = Integer.parseInt(args[1]);

		FileWriter out_order = new FileWriter("orders_out.txt");
		FileWriter out_product = new FileWriter("order_products_out.txt");

		// add the users and products to a list
		List<String> order_list = new ArrayList<String>();
		List<String> product_list = new ArrayList<String>();

		BufferedReader read_orders = new BufferedReader(new FileReader(args[0] + "/orders.txt"));
		String strOrder;
		while((strOrder = read_orders.readLine()) != null)
			order_list.add(strOrder);

		BufferedReader read_products = new BufferedReader(new FileReader(args[0] + "/order_products.txt"));
		String strProduct;
		while((strProduct = read_products.readLine()) != null)
			product_list.add(strProduct);

		Thread[] thread = new Thread[no_threads];
		Semaphore semmy = new Semaphore(1);
		Semaphore semmy_p = new Semaphore(no_threads);

		for (int i = 0; i < no_threads; i++) {
			thread[i] = new Thread(new Comanda(order_list, product_list, out_order, out_product, no_threads, semmy, semmy_p, i));
			thread[i].start();
		}
		for (int i = 0; i < no_threads; i++)
			try {
				thread[i].join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		out_order.close();
		out_product.close();
	}
}
