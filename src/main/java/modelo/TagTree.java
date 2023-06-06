
package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class TagTree<T> {

    private T head;

    private ArrayList<TagTree<T>> leafs = new ArrayList<TagTree<T>>();

    private TagTree<T> parent = null;

    private HashMap<T, TagTree<T>> locate = new HashMap<T, TagTree<T>>();

    public TagTree(T head) {
        this.head = head;
        locate.put(head, this);
    }

    public void addLeaf(T root, T leaf) {
        if (locate.containsKey(root)) {
            locate.get(root).addLeaf(leaf);
        } else {
            addLeaf(root).addLeaf(leaf);
        }
    }

    public TagTree<T> addLeaf(T leaf) {
        TagTree<T> t = new TagTree<T>(leaf);
        leafs.add(t);
        t.parent = this;
        t.locate = this.locate;
        locate.put(leaf, t);
        return t;
    }

    public TagTree<T> setAsParent(T parentRoot) {
        TagTree<T> t = new TagTree<T>(parentRoot);
        t.leafs.add(this);
        this.parent = t;
        t.locate = this.locate;
        t.locate.put(head, this);
        t.locate.put(parentRoot, t);
        return t;
    }

    public T getHead() {
        return head;
    }

    public TagTree<T> getTree(T element) {
        return locate.get(element);
    }

    public TagTree<T> getParent() {
        return parent;
    }

    public Collection<T> getSuccessors(T root) {
        Collection<T> successors = new ArrayList<T>();
        TagTree<T> tree = getTree(root);
        if (null != tree) {
            for (TagTree<T> leaf : tree.leafs) {
                successors.add(leaf.head);
            }
        }
        return successors;
    }

    public Collection<TagTree<T>> getSubTrees() {
        return leafs;
    }

    public static <T> Collection<T> getSuccessors(T of, Collection<TagTree<T>> in) {
        for (TagTree<T> tree : in) {
            if (tree.locate.containsKey(of)) {
                return tree.getSuccessors(of);
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public String toString() {
        return printTree(0);
    }

    private static final int indent = 2;

    private String printTree(int increment) {
        String s = "";
        String inc = "";
        for (int i = 0; i < increment; ++i) {
            inc = inc + " ";
        }
        s = inc + head;
        for (TagTree<T> child : leafs) {
            s += "\n" + child.printTree(increment + indent);
        }
        return s;
    }
}
