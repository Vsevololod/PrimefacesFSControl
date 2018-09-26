package today.vse;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@ManagedBean(name = "documentService")
@ApplicationScoped
public class DocumentService {

    static void constructTreeNode(TreeNode treeNode){
        try(Stream<Path> pathNodeStream = Files.list(((Document) treeNode.getData()).getPath())){
            pathNodeStream.forEach(path -> {
                if(Files.isDirectory(path)){
                    new DefaultTreeNode("folder",new Document(path),treeNode);
                }
                if(Files.isRegularFile(path)){
                    new DefaultTreeNode("document",new Document(path),treeNode);
                }
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    static void constructTreeChildren(TreeNode treeNode){
        for(TreeNode ch : treeNode.getChildren()){
            if(ch.getType().equals("folder")){
                constructTreeNode(ch);
            }
        }
    }
}