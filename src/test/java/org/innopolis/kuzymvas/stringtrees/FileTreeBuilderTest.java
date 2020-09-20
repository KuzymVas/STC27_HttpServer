package org.innopolis.kuzymvas.stringtrees;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

public class FileTreeBuilderTest {


    private final String directoryName = "Root folder";
    private final String file1Name = "First file";
    private final String file2Name = "SecondFile";
    private final String subDirectoryName = "subFolder";
    private final String subDirectoryFileName = "Sub.File";
    private final String subSubDirectoryName = "Deepest_Folder";
    private final String secondSubDirectoryName = "ChildDirectory";
    private final String secondSubDirectoryFileName = "Second_Child_File";

    private File directory;
    private File file1;
    private File file2;
    private File subDirectory;
    private File subDirectoryFile;
    private File subSubDirectory;
    private File secondSubDirectory;
    private File secondSubDirectoryFile;

    @Before
    public void setUp() {
        directory = Mockito.mock(File.class);
        Mockito.when(directory.isDirectory()).thenReturn(true);
        Mockito.when(directory.getName()).thenReturn(directoryName);
        file1 = Mockito.mock(File.class);
        Mockito.when(file1.isDirectory()).thenReturn(false);
        Mockito.when(file1.getName()).thenReturn(file1Name);
        file2 = Mockito.mock(File.class);
        Mockito.when(file2.isDirectory()).thenReturn(false);
        Mockito.when(file2.getName()).thenReturn(file2Name);
        subDirectory = Mockito.mock(File.class);
        Mockito.when(subDirectory.isDirectory()).thenReturn(true);
        Mockito.when(subDirectory.getName()).thenReturn(subDirectoryName);
        subDirectoryFile = Mockito.mock(File.class);
        Mockito.when(subDirectoryFile.isDirectory()).thenReturn(false);
        Mockito.when(subDirectoryFile.getName()).thenReturn(subDirectoryFileName);
        subSubDirectory = Mockito.mock(File.class);
        Mockito.when(subSubDirectory.isDirectory()).thenReturn(true);
        Mockito.when(subSubDirectory.getName()).thenReturn(subSubDirectoryName);
        secondSubDirectory = Mockito.mock(File.class);
        Mockito.when(secondSubDirectory.isDirectory()).thenReturn(true);
        Mockito.when(secondSubDirectory.getName()).thenReturn(secondSubDirectoryName);
        secondSubDirectoryFile = Mockito.mock(File.class);
        Mockito.when(secondSubDirectoryFile.isDirectory()).thenReturn(false);
        Mockito.when(secondSubDirectoryFile.getName()).thenReturn(secondSubDirectoryFileName);

        Mockito.when(directory.listFiles()).thenReturn(new File[] {file1, subDirectory, secondSubDirectory, file2});
        Mockito.when(subDirectory.listFiles()).thenReturn(new File[] {subSubDirectory, subDirectoryFile});
        Mockito.when(subSubDirectory.listFiles()).thenReturn(new File[] {});
        Mockito.when(secondSubDirectory.listFiles()).thenReturn(new File[] {secondSubDirectoryFile});
    }

    @Test
    public void testNull() {
        Assert.assertNull("File tree for a null root isn't null", FileTreeBuilder.getFileTree(null));
    }

    @Test
    public void testFile() {
        StringTreeNode node = FileTreeBuilder.getFileTree(file1);
        Mockito.verify(file1, Mockito.atLeastOnce()).getName();
        Mockito.verify(file1, Mockito.atLeastOnce()).isDirectory();
        Assert.assertEquals("File tree for a file doesn't has its name on the root", file1Name, node.getName());
        Assert.assertTrue("File tree isn't single empty node for a file", node.getEntries().isEmpty());
        Assert.assertTrue("File tree isn't single empty node for a file", node.getSubNodes().isEmpty());
    }

    @Test
    public void testSimpleDir() {
        StringTreeNode node = FileTreeBuilder.getFileTree(secondSubDirectory);
        Mockito.verify(secondSubDirectory, Mockito.atLeastOnce()).getName();
        Mockito.verify(secondSubDirectory, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(secondSubDirectory, Mockito.atLeastOnce()).listFiles();
        Mockito.verify(secondSubDirectoryFile, Mockito.atLeastOnce()).getName();
        Mockito.verify(secondSubDirectoryFile, Mockito.atLeastOnce()).isDirectory();
        Assert.assertEquals("File tree for a directory doesn't have its name on the root", secondSubDirectoryName, node.getName());
        Assert.assertEquals("File tree is empty for a non-empty directory", 1, node.getEntries().size());
        Assert.assertEquals("Entry in the file tree doesn't correspond to a given file structure",
                            secondSubDirectoryFileName, node.getEntries().get(0));
        Assert.assertTrue("File tree for a directory without subdirs have a subnodes", node.getSubNodes().isEmpty());
    }

    @Test
    public void testSubDir() {
        StringTreeNode node = FileTreeBuilder.getFileTree(subDirectory);
        Mockito.verify(subDirectory, Mockito.atLeastOnce()).getName();
        Mockito.verify(subDirectory, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(subDirectory, Mockito.atLeastOnce()).listFiles();
        Mockito.verify(subDirectoryFile, Mockito.atLeastOnce()).getName();
        Mockito.verify(subDirectoryFile, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(subSubDirectory, Mockito.atLeastOnce()).getName();
        Mockito.verify(subSubDirectory, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(subSubDirectory, Mockito.atLeastOnce()).listFiles();
        Assert.assertEquals("File tree for a directory doesn't have its name on the root", subDirectoryName, node.getName());
        Assert.assertEquals("File tree is empty for a non-empty directory", 1, node.getEntries().size());
        Assert.assertEquals("Entry in the file tree doesn't correspond to a given file structure",
                            subDirectoryFileName, node.getEntries().get(0));
        Assert.assertEquals("File tree doesn't have subnodes for a directory with subdirectories",1, node.getSubNodes().size());
        StringTreeNode subNode = node.getSubNodes().get(0);
        Assert.assertEquals("File tree has a wrong name for a subdirectory",subSubDirectoryName, subNode.getName());
        Assert.assertTrue("File tree isn't single empty node for a empty directory", subNode.getEntries().isEmpty());
        Assert.assertTrue("File tree isn't single empty node for a empty directory", subNode.getSubNodes().isEmpty());

    }

    @Test
    public void testTree() {
        StringTreeNode node = FileTreeBuilder.getFileTree(directory);
        Mockito.verify(directory, Mockito.atLeastOnce()).getName();
        Mockito.verify(directory, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(directory, Mockito.atLeastOnce()).listFiles();
        Mockito.verify(file1, Mockito.atLeastOnce()).getName();
        Mockito.verify(file1, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(file2, Mockito.atLeastOnce()).getName();
        Mockito.verify(file2, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(subDirectory, Mockito.atLeastOnce()).getName();
        Mockito.verify(subDirectory, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(subDirectory, Mockito.atLeastOnce()).listFiles();
        Mockito.verify(subDirectoryFile, Mockito.atLeastOnce()).getName();
        Mockito.verify(subDirectoryFile, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(subSubDirectory, Mockito.atLeastOnce()).getName();
        Mockito.verify(subSubDirectory, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(subSubDirectory, Mockito.atLeastOnce()).listFiles();
        Mockito.verify(secondSubDirectory, Mockito.atLeastOnce()).getName();
        Mockito.verify(secondSubDirectory, Mockito.atLeastOnce()).isDirectory();
        Mockito.verify(secondSubDirectory, Mockito.atLeastOnce()).listFiles();
        Mockito.verify(secondSubDirectoryFile, Mockito.atLeastOnce()).getName();
        Mockito.verify(secondSubDirectoryFile, Mockito.atLeastOnce()).isDirectory();
        Assert.assertEquals("File tree for a directory doesn't have its name on the root", directoryName, node.getName());
        Assert.assertEquals("File tree is empty for a non-empty directory", 2, node.getEntries().size());
        Assert.assertEquals("Entry in the file tree doesn't correspond to a given file structure",
                            file1Name, node.getEntries().get(0));
        Assert.assertEquals("Entry in the file tree doesn't correspond to a given file structure",
                            file2Name, node.getEntries().get(1));
        Assert.assertEquals("File tree doesn't have subnodes for a directory with subdirectories",2, node.getSubNodes().size());
        StringTreeNode firstSubNode = node.getSubNodes().get(0);
        Assert.assertEquals("File tree has a wrong name for a subdirectory",subDirectoryName, firstSubNode.getName());
        Assert.assertEquals("File tree is empty for a non-empty directory", 1, firstSubNode.getEntries().size());
        Assert.assertEquals("Entry in the file tree doesn't correspond to a given file structure",
                            subDirectoryFileName, firstSubNode.getEntries().get(0));
        Assert.assertEquals("File tree doesn't have subnodes for a directory with subdirectories",1, firstSubNode.getSubNodes().size());
        StringTreeNode subSubNode = firstSubNode.getSubNodes().get(0);
        Assert.assertEquals("File tree has a wrong name for a subdirectory",subSubDirectoryName, subSubNode.getName());
        Assert.assertTrue("File tree isn't single empty node for a empty directory", subSubNode.getEntries().isEmpty());
        Assert.assertTrue("File tree isn't single empty node for a empty directory", subSubNode.getSubNodes().isEmpty());
        StringTreeNode secondSubNode = node.getSubNodes().get(1);
        Assert.assertEquals("File tree for a directory doesn't have its name on the root", secondSubDirectoryName, secondSubNode.getName());
        Assert.assertEquals("File tree is empty for a non-empty directory", 1, secondSubNode.getEntries().size());
        Assert.assertEquals("Entry in the file tree doesn't correspond to a given file structure",
                            secondSubDirectoryFileName, secondSubNode.getEntries().get(0));
        Assert.assertTrue("File tree for a directory without subdirs have a subnodes", secondSubNode.getSubNodes().isEmpty());




    }



}