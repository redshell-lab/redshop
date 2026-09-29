package com.redshell.redshop.admin;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class DiagnosticService {

    public String ping(String host) {

        try {
            String command = "ping -c 4 " + host;

            Process process = Runtime.getRuntime().exec(
                    new String[]{"sh", "-c", command}
            );

            StringBuilder output = new StringBuilder();

            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(
                                         process.getInputStream()
                                 )
                         )) {

                String line;

                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            process.waitFor();

            return output.toString();

        } catch (Exception e) {
            return "Diagnostic failed: " + e.getMessage();
        }
    }
}