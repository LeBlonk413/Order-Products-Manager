import java.io.*;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Comanda implements Runnable {
    List<String> orders, products;
    FileWriter output_file_order, output_file_product;
    Integer no_threads, thread_id;
    Semaphore semmy, semmy_p;

    public Comanda(List<String> orders, List<String> products, FileWriter output_file_order,
                   FileWriter output_file_product, Integer no_threads, Semaphore semmy, Semaphore semmy_p, Integer thread_id) {
        this.orders = orders;
        this.products = products;
        this.output_file_order = output_file_order;
        this.output_file_product = output_file_product;
        this.no_threads = no_threads;
        this.semmy = semmy;
        this.semmy_p = semmy_p;
        this.thread_id = thread_id;
    }

    @Override
    public void run() {
        try {


            Thread[] thread = new Thread[no_threads];

            int start = (int) (thread_id * (double) orders.size() / no_threads);
            int end = (int) ((thread_id + 1) * (double) orders.size() / no_threads);

            String[] order_elements;
            for (int i = start; i < end && i < orders.size(); i++) {

                order_elements = orders.get(i).split(",");
                if (order_elements[1].equals("0"))
                    continue;

                for (int k = 0; k < no_threads; k++) {
                    thread[k] = new Thread(new Produs(orders, products, order_elements[0], output_file_product, k,
                            no_threads, semmy, semmy_p));
                    thread[k].start();
                }

                for (int k = 0; k < no_threads; k++)
                    thread[k].join();

                semmy.acquire();
                output_file_order.write(order_elements[0] + "," + order_elements[1] + ",shipped\n");
                semmy.release();
            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
