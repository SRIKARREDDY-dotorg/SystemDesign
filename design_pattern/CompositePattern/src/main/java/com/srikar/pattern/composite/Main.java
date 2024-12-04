package com.srikar.pattern.composite;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Create leaf nodes
        File file1 = new File("Document1.txt");
        File file2 = new File("Photo1.png");
        File file3 = new File("Video1.mp4");

        // Create composite nodes
        Folder rootFolder = new Folder("Root");
        Folder subFolder = new Folder("SubFolder");

        // Build the hierarchy
        rootFolder.add(file1);
        rootFolder.add(subFolder);
        subFolder.add(file2);
        subFolder.add(file3);

        System.out.println("Tree view of the files & folders");
        // Show hierarchy
        rootFolder.show("");
    }
}