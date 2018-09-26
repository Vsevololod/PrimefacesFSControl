package today.vse;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

@ManagedBean(name = "editor")
@ViewScoped
public class EditorBean implements Serializable {

    public void onNodeExpand(NodeExpandEvent nodeExpandEvent) {
        DocumentService.constructTreeChildren(nodeExpandEvent.getTreeNode());

    }

    private TreeNode root1;


    @PostConstruct
    public void init() {
        Path rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toPath();
        root1  = new DefaultTreeNode(new Document(rootPath), null);
        DocumentService.constructTreeNode(root1);
        DocumentService.constructTreeChildren(root1);

    }

    public TreeNode getRoot1() {
        return root1;
    }


    public void onDragDrop(TreeDragDropEvent event) throws IOException {
        Path dragNode = ((Document) event.getDragNode().getData()).getPath();
        Path dropNode = ((Document) event.getDropNode().getData()).getPath();

        if (Files.isDirectory(dropNode)) {
            if (Files.isDirectory(dragNode)) {
                FileUtils.moveDirectoryToDirectory(dragNode.toFile(), dropNode.toFile(), true);
            } else {
                FileUtils.moveFileToDirectory(dragNode.toFile(), dropNode.toFile(), true);
            }
        } else {
            System.err.println("you cant move something to file");
        }
    }
}