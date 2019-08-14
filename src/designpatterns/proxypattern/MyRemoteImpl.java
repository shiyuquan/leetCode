package designpatterns.proxypattern;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author shiyuquan
 * Create Time: 2019/8/13 19:55
 */
public class MyRemoteImpl extends UnicastRemoteObject implements IMyRemote {

    public MyRemoteImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Server says, 'Hey'";
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        IMyRemote service = new MyRemoteImpl();
        // 启动本地rmi registry，默认端口1099
        LocateRegistry.createRegistry(1099);
        // 注册远程对象
        Naming.rebind("rmi://localhost:1099/RemoteHello", service);
    }
}
