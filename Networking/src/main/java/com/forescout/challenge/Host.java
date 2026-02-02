package com.forescout.challenge;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Objects;

public class Host {

    private InetAddress address;
    private String hostName;

    public Host(InetAddress address, String hostName) {
        setAddress(address);
        setHostName(hostName);
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = Objects.requireNonNull(address);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = Objects.requireNonNull(hostName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Host)) {
            return false;
        }
        Host host = (Host) o;
        return address.equals(host.address) && hostName.equalsIgnoreCase(host.hostName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, hostName);
    }

    public boolean isIPv4() {
        return address instanceof Inet4Address;
    }

    public boolean isIPv6() {
        return address instanceof Inet6Address;
    }
}
