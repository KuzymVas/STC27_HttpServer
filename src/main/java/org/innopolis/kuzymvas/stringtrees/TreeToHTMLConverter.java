package org.innopolis.kuzymvas.stringtrees;

public class TreeToHTMLConverter {

    private final int indentPerLevel;

    public TreeToHTMLConverter(int indentPerLevel) {
        this.indentPerLevel = indentPerLevel;
    }

    public  String convertTreeToHTML(StringTreeNode root) {
        StringBuilder builder = new StringBuilder();
        builder.append("<ul>\n");
        convertNodeToHtml(root, builder, indentPerLevel);
        builder.append("</ul>\n");
        return builder.toString();
    }

    private  void convertNodeToHtml(StringTreeNode node, StringBuilder builder, int indent) {
        appendIndentation(builder, indent);
        builder.append("<li>");
        builder.append(node.getName());
        builder.append("</li>\n");
        indent+=indentPerLevel;
        appendIndentation(builder, indent);
        builder.append("<ul>\n");
        indent+=indentPerLevel;
        for (StringTreeNode subnode: node.getSubNodes()) {
            convertNodeToHtml(subnode, builder, indent);
        }
        for (String entry: node.getEntries()) {
            convertEntryToHtml(entry, builder, indent);
        }
        indent-=indentPerLevel;
        appendIndentation(builder, indent);
        builder.append("</ul>\n");
    }


    private  void convertEntryToHtml(String entry, StringBuilder builder, int indent) {
        appendIndentation(builder, indent);
        builder.append("<li>");
        builder.append(entry);
        builder.append("</li>\n");
    }

    private  void appendIndentation(StringBuilder builder, int indent) {
        for (int i = 0; i < indent; i++) {
            builder.append(" ");
        }
    }

}
