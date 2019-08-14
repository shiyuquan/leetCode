package designpatterns.proxypattern;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author shiyuquan
 * Create Time: 2019/8/13 19:54
 */
public interface IMyRemote extends Remote {
    String sayHello() throws RemoteException;
}
