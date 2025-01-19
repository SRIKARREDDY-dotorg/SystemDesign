package com.srikar.cor.pattern;

import com.srikar.cor.pattern.middleware.Middleware;
import com.srikar.cor.pattern.middleware.RoleCheckMiddleware;
import com.srikar.cor.pattern.middleware.ThrottlingMiddleware;
import com.srikar.cor.pattern.middleware.UserExistsMiddleware;
import com.srikar.cor.pattern.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;

    private static void init() {
        server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        Middleware middleware = Middleware.link(
                new ThrottlingMiddleware(2),
                new UserExistsMiddleware(server),
                new RoleCheckMiddleware()
        );

        server.setMiddleware(middleware);
    }

    public static void main(String[] args) {
        init();

        boolean success;
        do {
            System.out.print("Enter email: ");
            String email;
            try {
                email = reader.readLine();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.print("Input password: ");
            String password;
            try {
                password = reader.readLine();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            success = server.logIn(email, password);
        } while (!success);
    }
}