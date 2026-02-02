package com.forescout.challenge;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Network {
  private final Set<Host> hosts = new HashSet<>();

   public Host getHostByIp(InetAddress inetAddress) {
        // Ensure type-safe IP comparison
        return hosts.stream()
            .filter(h -> h.getAddress().getClass().equals(inetAddress.getClass()) && h.getAddress().equals(inetAddress))
            .findFirst()
            .orElse(null);
    }

  public Host getHostByName(String hostName) {
    return hosts.stream().filter(h -> h.getHostName().equalsIgnoreCase(hostName.trim())).findFirst().orElse(null);
  }

  public boolean addHost(Host host) {
    Objects.requireNonNull(host);
    if (getHostByIp(host.getAddress()) != null) {
      throw new IllegalArgumentException("A host with the same IP address already exists");
    }
    if (getHostByName(host.getHostName()) != null) {
      throw new IllegalArgumentException("A host with the same name already exists");
    }
    return hosts.add(host);
  }

  public boolean removeHost(Host host) {
    return hosts.remove(Objects.requireNonNull(host));
  }

  public boolean removeHostByIp(InetAddress inetAddress) {
    Host host = getHostByIp(inetAddress);
    if (host != null) {
      return removeHost(host);
    }
    return false;
  }

  public boolean removeHostByName(String hostName) {
    Host host = getHostByName(hostName);
    if (host != null) {
      return removeHost(host);
    }
    return false;
  }

  public int hostCount() {
    return hosts.size();
  }
}
