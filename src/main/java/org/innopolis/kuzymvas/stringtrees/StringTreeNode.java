package org.innopolis.kuzymvas.stringtrees;

import java.util.ArrayList;
import java.util.List;

/**
 * Структура данных представдляющая собой дерево, где каждый узел - это строка
 */
public class StringTreeNode {

    private final String name;
    private final List<String> entries;
    private final List<StringTreeNode> subNodes;

    public StringTreeNode(String name) {
        this.name = name;
        entries = new ArrayList<>();
        subNodes = new ArrayList<>();
    }

    public void addEntry(String entry) {
        entries.add(entry);
    }

    public void addSubNode(StringTreeNode subnode) {
        subNodes.add(subnode);
    }

    public String getName() {
        return name;
    }

    public List<String> getEntries() {
        return new ArrayList<>(entries);
    }

    public List<StringTreeNode> getSubNodes() {
        return subNodes;
    }
}
