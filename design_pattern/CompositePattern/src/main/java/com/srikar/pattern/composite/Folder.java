package com.srikar.pattern.composite;

import java.util.ArrayList;
import java.util.List;

public class Folder implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components;
    public Folder(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    public void add(FileSystemComponent component) {
        components.add(component);
    }
    public void remove(FileSystemComponent component) {
        components.remove(component);
    }
    @Override
    public void show(String path) {
        String currentPath = path + "/" + name;
        System.out.println(currentPath);
        for (FileSystemComponent component : components) {
            component.show(currentPath);
        }
    }
}
