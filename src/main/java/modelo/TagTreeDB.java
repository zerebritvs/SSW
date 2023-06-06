
package modelo;

import java.util.ArrayList;


public class TagTreeDB {
    private static TagTree<String> tagTree = new TagTree<String>("root");
    
    public static void insert(String uni, String carrera, String curso, String asig){ 
        
        if (!tagTree.getSuccessors(tagTree.getHead()).contains(uni)) {
            TagTree tagUni = tagTree.addLeaf(uni);
            TagTree tagCar = tagUni.addLeaf(carrera);
            TagTree tagCur = tagCar.addLeaf(curso);
            tagCur.addLeaf(asig);
        } else {
            TagTree tagUni = tagTree.getTree(uni);
            if (!tagUni.getSuccessors(uni).contains(carrera)) {
                TagTree tagCar = tagUni.addLeaf(carrera);
                TagTree tagCur = tagCar.addLeaf(curso);
                tagCur.addLeaf(asig);
            } else {
                TagTree tagCar = tagUni.getTree(carrera); 
                if (!tagCar.getSuccessors(carrera).contains(curso)) {
                    TagTree tagCur = tagCar.addLeaf(curso);
                    tagCur.addLeaf(asig);
                } else {
                    TagTree tagCur = tagCar.getTree(curso);
                    if (!tagCur.getSuccessors(curso).contains(asig)) {
                        tagCur.addLeaf(asig);
                    } 
                }
            }
        }
    }
    
    public static ArrayList<String> getOptions(String parent) {
        return (ArrayList<String>) tagTree.getSuccessors(parent);
    }
    
}
