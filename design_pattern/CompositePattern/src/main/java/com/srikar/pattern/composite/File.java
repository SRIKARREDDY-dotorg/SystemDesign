package com.srikar.pattern.composite;

public class File implements FileSystemComponent {
    private String name;
    public File(String name) {
        this.name = name;
    }
    @Override
    public void show(String path) {
        System.out.println(path + "/" + name);
    }
}
