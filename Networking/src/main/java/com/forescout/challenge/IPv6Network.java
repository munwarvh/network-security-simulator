package com.forescout.challenge;

public class IPv6Network extends Network {
    public boolean addHost(Host host) {
        if (!host.isIPv6()) {
            throw new IllegalArgumentException("Only IPv6 hosts are allowed in this network.");
        }
        return super.addHost(host);
    }
}
