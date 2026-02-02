package com.forescout.challenge;

public class IPv4Network extends Network {
    public boolean addHost(Host host) {
        if (!host.isIPv4()) {
            throw new IllegalArgumentException("Only IPv4 hosts are allowed in this network.");
        }
        return super.addHost(host);
    }
}
