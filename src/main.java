import rmi_servidor_cliente.Cliente_RMI_class;
import rmi_servidor_cliente.Server_RMI_Interface;
import rmi_servidor_cliente.UtilizadorRMI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class main {
    static Scanner scanner;
    static Server_RMI_Interface iRMI;
    static Cliente_RMI_class crc;
    static String nome;

    public static void main(String[] args) {
        boolean conect = false;

        try {
            crc = new Cliente_RMI_class();

            Remote remote = Naming.lookup("rmi//127.0.0.1:1099/ServidorRede");
            iRMI = (Server_RMI_Interface) remote;

            System.out.println("Conectado a servidor RMI com sucesso");

            scanner = new Scanner(System.in);
            int option;

            do {
                System.out.println("opções:\n1- Registar Cliente\n2- Mandar Mensagem Para Todos\n0- sair");
                try {
                    option = scanner.nextInt();
                }catch (InputMismatchException e)
                {
                    scanner.nextLine();
                    option = 9;
                }
                switch (option) {
                    case 1:
                        registarCliente();
                        break;
                    case 2:
                        msgBroadcast();
                        break;
                }
            } while (option != 0);


        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            System.out.println("Não foi possivel conectar a servidor RMI");
            e.printStackTrace();
        }
        System.out.println("<-A desligar programa->");
    }


    static void registarCliente() {
        //obtem nome, pass e diretorio da fotografia
        System.out.println("username:\n");
        nome = scanner.next();
        System.out.println("password:\n");
        String password = scanner.next();
        System.out.println("localização da foto");
        String foto = scanner.next();

        UtilizadorRMI eu;
        //obtem user
        try {
            eu = new UtilizadorRMI(nome, password, foto);
        } catch (FileNotFoundException e) {
            System.out.println("A localização indicada não foi encontrada!");
            return;
        } catch (IOException e) {
            System.out.println("Erro a ler imagem");
            e.printStackTrace();
            return;
        }

        try {
           iRMI.registaCliente(eu, crc);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("programa continua funcional tho");
        }
    }


    //obtem a mensagem e manda por broadcast
    static void msgBroadcast() {
        System.out.println("nome:");
        nome = scanner.next();
        nome += scanner.nextLine();
        System.out.println("Indique a mensagem a enviar:");
        String msg = scanner.next();
        msg += scanner.nextLine();

        try {
            iRMI.broadCastUtilizadoresRMI(nome, msg);

        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("programa continua funcional tho");
        }
    }

}
