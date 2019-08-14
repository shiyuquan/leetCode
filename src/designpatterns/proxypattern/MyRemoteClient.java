package designpatterns.proxypattern;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author shiyuquan
 * Create Time: 2019/8/13 19:57
 */
public class MyRemoteClient {
    private void go() throws RemoteException, NotBoundException, MalformedURLException {
        IMyRemote service = (IMyRemote) Naming.lookup("rmi://localhost/RemoteHello");
        System.out.println(service.sayHello());
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        new MyRemoteClient().go();
    }
}
