package org.innopolis.kuzymvas.stringtrees;

import java.io.File;

/**
 * Построитель строкового дерева, описывающего дерево файлов
 */
public class FileTreeBuilder {

    /**
     * Возвращает строковое дерево, описывающее дерево файло для заданной корневой директории
     *
     * @param rootDirectory - корневая директория
     * @return - строковое дерево, соответствующее файловому дереву.
     */
    public static StringTreeNode getFileTree(File rootDirectory) {
        if (rootDirectory == null) {
            return null;
        }
        StringTreeNode node = new StringTreeNode(rootDirectory.getName());
        if (!(rootDirectory.isDirectory())) {
            return node;
        }
        File[] files = rootDirectory.listFiles();
        if (files == null || files.length == 0) {
            return node;
        }
        for (final File file : files) {
            if (file != null && file.isDirectory()) {
                node.addSubNode(getFileTree(file));
            }
        }
        for (final File file : files) {
            if (!(file.isDirectory())) {
                node.addEntry(file.getName());
            }
        }
        return node;
    }
}
