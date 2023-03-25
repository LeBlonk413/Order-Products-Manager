import java.io.*;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Produs implements Runnable {
    List<String> orders, products;
    String current_order;
    FileWriter output_file_product;
    Integer no_threads, thread_id;
    Semaphore semmy;
    Semaphore semmy_p;

    public Produs(List<String> orders, List<String> products, String current_order, FileWriter output_file_product,
                  Integer thread_id, Integer no_threads, Semaphore semmy, Semaphore semmy_p) {
        this.orders = orders;
        this.products = products;
        this.current_order = current_order;
        this.output_file_product = output_file_product;
        this.no_threads = no_threads;
        this.thread_id = thread_id;
        this.semmy = semmy;
        this.semmy_p = semmy_p;
    }

    @Override
    public void run() {
        try {
            semmy_p.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int start = (int)(thread_id * (double)products.size()/no_threads);
        int end = (int)((thread_id + 1) * (double)products.size() / no_threads);

        String[] product_elements;
        for (int i = start; i < end && i < products.size(); i++) {

            product_elements = products.get(i).split(",");

            try {
                semmy.acquire();
                if (product_elements[0].equals(current_order))
                    output_file_product.write(product_elements[0] + "," + product_elements[1] + ",shipped\n");
                semmy.release();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        semmy_p.release();
    }
}
