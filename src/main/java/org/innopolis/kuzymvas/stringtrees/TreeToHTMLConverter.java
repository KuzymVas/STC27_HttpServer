package org.innopolis.kuzymvas.stringtrees;

/**
 * Класс преобразователя дерева из строк в многоуровненывй html список
 */
public class TreeToHTMLConverter {

    private final int indentPerLevel;

    /**
     * Создает новый преобразователь с заданным размером отступа за каждый уровень вложеннотси
     *
     * @param indentPerLevel -  величина отступа за каждый уровень вложенности.
     */
    public TreeToHTMLConverter(int indentPerLevel) {
        this.indentPerLevel = indentPerLevel;
    }

    /**
     * Преобразует данное дерево в многоуровневый html список
     *
     * @param root - корень дерева.
     * @return - строку, содержащую многоуровневый html список
     */
    public String convertTreeToHTML(StringTreeNode root) {
        StringBuilder builder = new StringBuilder();
        builder.append("<ul>\n");
        convertNodeToHtml(root, builder, indentPerLevel);
        builder.append("</ul>\n");
        return builder.toString();
    }

    /**
     * Добавляет html представление узла дерева к StringBuilder-у.
     *
     * @param node    - узел дерева
     * @param builder - целевой построитель для добавления
     * @param indent  - текущий отступ
     */
    private void convertNodeToHtml(StringTreeNode node, StringBuilder builder, int indent) {
        appendIndentation(builder, indent);
        builder.append("<li>");
        builder.append(node.getName());
        builder.append("</li>\n");
        indent += indentPerLevel;
        appendIndentation(builder, indent);
        builder.append("<ul>\n");
        indent += indentPerLevel;
        for (StringTreeNode subnode : node.getSubNodes()) {
            convertNodeToHtml(subnode, builder, indent);
        }
        for (String entry : node.getEntries()) {
            convertEntryToHtml(entry, builder, indent);
        }
        indent -= indentPerLevel;
        appendIndentation(builder, indent);
        builder.append("</ul>\n");
    }

    /**
     * Добавляет html представление листа дерева к StringBuilder-у.
     *
     * @param entry   - лист строкового дерева
     * @param builder - целевой построитель для добавления
     * @param indent  - текущий отступ
     */
    private void convertEntryToHtml(String entry, StringBuilder builder, int indent) {
        appendIndentation(builder, indent);
        builder.append("<li>");
        builder.append(entry);
        builder.append("</li>\n");
    }

    /**
     * Добавляет к построителю отступ заданной величины
     *
     * @param builder - целевой построитель
     * @param indent  - уровень отступа
     */
    private void appendIndentation(StringBuilder builder, int indent) {
        for (int i = 0; i < indent; i++) {
            builder.append(" ");
        }
    }
}
