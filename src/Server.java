import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Сервер ожидает клиента...");

        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 InputStream inputStream = clientSocket.getInputStream();
                 OutputStream outputStream = clientSocket.getOutputStream()) {

                System.out.println("Новое соединение: " + clientSocket.getInetAddress().toString());

                // Чтение числа от клиента
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                int number = dataInputStream.readInt();
                System.out.println("Сервер получил: " + number);

                // Вычисление простых чисел
                String primes = IntStream.rangeClosed(1, number)
                        .filter(Server::isPrime)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", "));

                // Отправка результата клиенту
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF(primes);
                dataOutputStream.flush();
                System.out.println("Сервер отправил: " + primes);
            }
        }
    }

    public static boolean isPrime(final int number) {
        if (number < 2) return false;
        return IntStream.rangeClosed(2, (int) Math.sqrt(number)).allMatch(i -> number % i != 0);
    }
}
