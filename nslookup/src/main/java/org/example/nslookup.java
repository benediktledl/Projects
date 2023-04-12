package org.example;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.google.common.net.InetAddresses;
import com.google.common.net.InternetDomainName;

public class nslookup {
    public static String nslookup(String input) throws UnknownHostException {
        if(isDomainName(input)){
            InetAddress output = java.net.InetAddress.getByName(input);

            return output.toString();
        }
        if(isIpAddress(input)){
            InetAddress ia = InetAddress.getByName(input);

            return (ia.getHostName());
        }

        return "Unsupported Format";
    }

    public static boolean isIpAddress(final String hostname) {
        return InetAddresses.isUriInetAddress(hostname);
    }
    public static boolean isDomainName(final String hostname) {
        return InternetDomainName.isValid(hostname);
    }
    
}
