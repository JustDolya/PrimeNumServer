import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             Scanner scanner = new Scanner(System.in)) {

            // Ввод числа пользователем
            System.out.print("Вычисление простых чисел\nВведите верхнюю границу: ");
            int number = scanner.nextInt();

            // Отправка числа серверу
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeInt(number);
            dataOutputStream.flush();
            System.out.println("Клиент отправил: " + number);

            // Получение ответа от сервера
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String response = dataInputStream.readUTF();
            System.out.println("Клиент получил: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
