package com.forescout.challenge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {

    private final Network network = new Network();

    @AfterEach
    public void afterEach() {
        assertEquals(0, network.hostCount());
    }

    private Host addHost(InetAddress address) throws UnknownHostException {
        String hostName = "host-" + address.getHostAddress().replaceAll("[.:]", "-");
        Host host = new Host(address, hostName);
        assertTrue(network.addHost(host));
        // Test that the same host cannot be added twice
        assertThrows(IllegalArgumentException.class, () -> network.addHost(host));
        // Test that a host with the same name cannot be added
        InetAddress differentAddress = address instanceof Inet4Address ?
                Inet4Address.getByName("1.0.0.1") :
                Inet6Address.getByName("::1");
        assertThrows(IllegalArgumentException.class, () -> network.addHost(new Host(differentAddress, hostName)));
        // Test that a host with the same address cannot be added
        assertThrows(IllegalArgumentException.class, () -> network.addHost(new Host(address, hostName + "-test")));
        return host;
    }

    private void removeHost(Host host) {
        assertTrue(network.removeHost(host));
        // cannot be removed twice
        assertFalse(network.removeHost(host));
        // Put it back
        assertTrue(network.addHost(host));
        // And remove it by address
        assertTrue(network.removeHostByIp(host.getAddress()));
        // cannot be removed twice
        assertFalse(network.removeHostByIp(host.getAddress()));
        // Put it back
        assertTrue(network.addHost(host));
        // And remove it by name
        assertTrue(network.removeHostByName(host.getHostName()));
        // cannot be removed twice
        assertFalse(network.removeHostByName(host.getHostName()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.1.1.1", "1.1.1.2", "1.2.1.1", "1.2.1.2"})
    void testAddAndRemoveIPv4(String addressStr) throws Exception {
        Inet4Address address = (Inet4Address) Inet4Address.getByName(addressStr);
        Host host = addHost(address);
        removeHost(host);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2001:db8::1", "2001:db8::1", "2001:db8:0:1::1", "2001:db8:0:2::2"})
    void testAddAndRemoveIPv6(String addressStr) throws Exception {
        Inet6Address address = (Inet6Address) Inet6Address.getByName(addressStr);
        Host host = addHost(address);
        removeHost(host);
    }


    //test cases to show case bug in Network class
    @Test
    void testMixedIpAddressHandling() throws UnknownHostException {
        // Add an IPv4 host
        Inet4Address ipv4Address = (Inet4Address) Inet4Address.getByName("192.168.1.1");
        Host ipv4Host = new Host(ipv4Address, "host-ipv4");
        assertTrue(network.addHost(ipv4Host));

        // Add an IPv6 host
        Inet6Address ipv6Address = (Inet6Address) Inet6Address.getByName("2001:db8::1");
        Host ipv6Host = new Host(ipv6Address, "host-ipv6");
        assertTrue(network.addHost(ipv6Host));

        // Check IPv4 host retrieval by IP
        Host foundIpv4Host = network.getHostByIp(ipv4Address);
        assertNotNull(foundIpv4Host);
        assertEquals("host-ipv4", foundIpv4Host.getHostName());

        // Check IPv6 host retrieval by IP
        Host foundIpv6Host = network.getHostByIp(ipv6Address);
        assertNotNull(foundIpv6Host);
        assertEquals("host-ipv6", foundIpv6Host.getHostName());

        // Check retrieval by hostname (case insensitive and trimmed)
        assertNotNull(network.getHostByName("   HOST-IPv4   "));
        assertNotNull(network.getHostByName("   host-ipv6  "));
    }

    @Test
    void testBugHandlingWithIncorrectIpType() throws UnknownHostException {
        // Add IPv4 and IPv6 hosts
        Inet4Address ipv4Address = (Inet4Address) Inet4Address.getByName("192.168.1.1");
        Inet6Address ipv6Address = (Inet6Address) Inet6Address.getByName("2001:db8::1");

        Host ipv4Host = new Host(ipv4Address, "host-ipv4");
        Host ipv6Host = new Host(ipv6Address, "host-ipv6");

        network.addHost(ipv4Host);
        network.addHost(ipv6Host);

        // Ensure that IPv4 cannot retrieve IPv6 host and vice versa
        assertNull(network.getHostByIp((InetAddress) Inet4Address.getByName("2001:db8::1"))); // Should be null
        assertNull(network.getHostByIp((InetAddress) Inet6Address.getByName("192.168.1.1"))); // Should be null
    }
}
