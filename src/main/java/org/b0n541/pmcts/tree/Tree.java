package org.b0n541.pmcts.tree;

public final class Tree<T> {

    private RootNode<T> rootNode;

    public Tree(RootNode<T> rootNode) {
        this.rootNode=rootNode;
    }

    public RootNode<T> getRootNode() {
        return rootNode;
    }
}
