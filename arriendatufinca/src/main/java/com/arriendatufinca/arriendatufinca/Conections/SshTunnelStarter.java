package com.arriendatufinca.arriendatufinca.Conections;

import java.util.ResourceBundle;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshTunnelStarter {

    private Session session;

    public void init() throws JSchException {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String sshHost = bundle.getString("spring.ssh.host");
        String sshUser = bundle.getString("spring.ssh.user");
        String sshPassword = bundle.getString("spring.ssh.password");
        int sshPort = Integer.parseInt(bundle.getString("spring.ssh.port"));
        String remoteHost = bundle.getString("spring.ssh.remoteHost");
        int remotePort = Integer.parseInt(bundle.getString("spring.ssh.remotePort"));
        int localPort = Integer.parseInt(bundle.getString("spring.ssh.localPort"));

        JSch jsch = new JSch();
        session = jsch.getSession(sshUser, sshHost, sshPort);
        session.setPassword(sshPassword);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        session.setPortForwardingL(localPort, remoteHost, remotePort);
    }

    public void shutdown() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    @Override
    public String toString() {
        return "SshTunnelStarter{" +
                "session=" + session +
                '}';
    }
}