package com.gigabit;

import java.io.*;
import java.util.ArrayList;

public class Main {


    static int hostCounter = 0;
    static int serviceCounter = 0;

    static ArrayList<Host> hosts = new ArrayList<Host>();
    static ArrayList<Service> services = new ArrayList<Service>();

    public static void createCollections(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        try {
            while (true) {
                String s = reader.readLine();
                if (s.contains("define host")) {
                    Host host = new Host();
                    while (true) {
                        String temp = reader.readLine();
                        if (temp.contains("}")) {
                            hostCounter++;
                            hosts.add(host);
                            break;
                        } else if (temp.contains("use")) host.use = temp;
                        else if (temp.contains("host_name")) host.host_name = temp;
                        else if (temp.contains("alias")) host.alias = temp;
                        else if (temp.contains("address")) host.address = temp;
                        else if (temp.contains("hostgroups")) host.hostgroups = temp;
                        else if (temp.contains("parents")) host.parents = temp;
                        else if (temp.contains("contact_groups")) host.contact_groups = temp;
                        else if (temp.contains("contacts")) host.contacts = temp;
                    }
                } else if (s.contains("define service")) {
                    Service service = new Service();
                    while (true) {
                        String temp = reader.readLine();
                        if (temp.contains("}")) {
                            serviceCounter++;
                            services.add(service);
                            break;
                        } else if (temp.contains("use")) service.use = temp;
                        else if (temp.contains("host_name")) service.host_name = temp;
                        else if (temp.contains("service_description")) service.service_description = temp;
                        else if (temp.contains("check_command")) service.check_command = temp;
                        else if (temp.contains("check_period")) service.check_period = temp;
                        else if (temp.contains("contact_groups")) service.contact_groups = temp;
                        else if (temp.contains("contacts")) service.contacts = temp;
                    }
                }
            }

        } catch (NullPointerException e) {
            System.out.println("hosts: " + hostCounter);
            System.out.println("services: " + serviceCounter);
        }


    }

    public static void main(String[] args) throws IOException {

        createCollections("switches.cfg");
        createCollections("bgp.cfg");
        createCollections("clients.cfg");
        createCollections("electricity.cfg");
        createCollections("servers.cfg");
        createCollections("wifi.cfg");
        createCollections("switches_core.cfg");
        createCollections("switches_olt.cfg");
        createCollections("switches_sfp.cfg");

        for (int i = 0; i < hosts.size(); i++) {
            Host host = hosts.get(i);
            String[] s = host.host_name.split(" ");

            String hostFileName = s[1] + ".cfg";

            FileWriter hostFile = new FileWriter(hostFileName, false);
            hostFile.write("define host {");
            hostFile.write(System.lineSeparator());
            try {hostFile.write(host.use);} catch (NullPointerException e) {}
            hostFile.write(System.lineSeparator());
            try {hostFile.write(host.host_name);} catch (NullPointerException e) {}
            hostFile.write(System.lineSeparator());
            try {hostFile.write(host.alias);} catch (NullPointerException e) {}
            hostFile.write(System.lineSeparator());
            try {hostFile.write(host.address);} catch (NullPointerException e) {}
            hostFile.write(System.lineSeparator());
            try {hostFile.write(host.hostgroups);} catch (NullPointerException e) {}
            hostFile.write(System.lineSeparator());
            try {hostFile.write(host.parents);} catch (NullPointerException e) {}
            hostFile.write(System.lineSeparator());
            try {hostFile.write(host.contact_groups);} catch (NullPointerException e) {}
            hostFile.write(System.lineSeparator());
            try {hostFile.write(host.contacts);} catch (NullPointerException e) {}
            hostFile.write(System.lineSeparator());
            try {hostFile.write("}");} catch (NullPointerException e) {}
            hostFile.write(System.lineSeparator());

            for (Service service : services) {
                if (service.host_name.equals(host.host_name)) {
                    hostFile.write("define service {");
                    hostFile.write(System.lineSeparator());
                    try {hostFile.write(service.use);} catch (NullPointerException e) {}
                    hostFile.write(System.lineSeparator());
                    try {hostFile.write(service.host_name);} catch (NullPointerException e) {}
                    hostFile.write(System.lineSeparator());
                    try {hostFile.write(service.service_description);} catch (NullPointerException e) {}
                    hostFile.write(System.lineSeparator());
                    try {hostFile.write(service.check_command);} catch (NullPointerException e) {}
                    hostFile.write(System.lineSeparator());
                    try {hostFile.write(service.check_period);} catch (NullPointerException e) {}
                    hostFile.write(System.lineSeparator());
                    try {hostFile.write(service.contact_groups);} catch (NullPointerException e) {}
                    hostFile.write(System.lineSeparator());
                    try {hostFile.write(service.contacts);} catch (NullPointerException e) {}
                    hostFile.write(System.lineSeparator());
                    hostFile.write("}");
                    hostFile.write(System.lineSeparator());
                }
            }
        hostFile.close();
        }
    }
}
class Host {
    String use;
    String host_name;
    String alias;
    String address;
    String hostgroups;
    String parents;
    String contact_groups;
    String contacts;
}

class Service {
    String use;
    String host_name;
    String service_description;
    String check_command;
    String check_period;
    String contact_groups;
    String contacts;
}