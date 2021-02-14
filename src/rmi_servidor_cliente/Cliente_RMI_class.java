package rmi_servidor_cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Cliente_RMI_class extends UnicastRemoteObject implements Cliente_RMI_Interface {
    public Cliente_RMI_class() throws RemoteException {
    }


    @Override
    public void recebemMensagem(String msg) {
        System.out.println("Mensagem recebida de \t" + msg);
    }
}
