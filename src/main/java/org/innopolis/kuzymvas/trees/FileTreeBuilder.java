package org.innopolis.kuzymvas.trees;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class FileTreeBuilder {

    public static StringTreeNode getFileTree(File rootDirectory) {
        if(rootDirectory == null) {
            return  null;
        }
        StringTreeNode node = new StringTreeNode(rootDirectory.getName());
        if (!(rootDirectory.isDirectory())) {
            return  node;
        }
        File[] files = rootDirectory.listFiles();
        if (files == null || files.length == 0)
            return node;
        for (final File file : files) {
            if (file != null && file.isDirectory()) {
                node.addSubNode(getFileTree(file));
            }
        }
        for (final File file : rootDirectory.listFiles()) {
            if (!(file.isDirectory())) {
                node.addEntry(file.getName());
            }
        }
        return  node;
    }
}
