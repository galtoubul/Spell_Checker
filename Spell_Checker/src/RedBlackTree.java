/**
 * Represents information about Red Black Tree
 *
 * @author David kipnis and Gal Toubul
 * @version 1.0
 * <p>the code is based on the algorithms from the book:
 * "Introduction To Algorithms", second edition. authors:</p>
 * <P>Thomas H. Cormen,
 * Charles E. Leiserson,
 * Ronald L. Rivest,
 * Clifford Stein</P>
 */
public class RedBlackTree {
    //declarations
    private RedBlackNode _root;//root[T]
    private RedBlackNode _nil;//nil[T]
    final String RED = "RED";
    final String BLACK = "BLACK";

    //constructor

    /**
     * creats a new RB-Tree
     * sets its root to nil[T]
     */
    public RedBlackTree() {
        _nil = new RedBlackNode();
        _root = _nil;//root initialized to nil
        _nil.setLeftSon(null);
        _nil.setRightSon(null);
        _nil.setColor(BLACK);
    }

    //getters

    /**
     * returns the root of the tree
     *
     * @return the root of the tree
     */
    public RedBlackNode getRoot() {
        return _root;
    }

    /**
     * returns the successor of a given node in the tree
     *
     * @param x - the node in the RB-Tree which the method will return its successor
     * @return the successor node of a given node in the tree
     */
    private RedBlackNode getSuccessor(RedBlackNode x) {
        if (x.getRightSon() != null)
            return getMinimum(x.getRightSon());
        RedBlackNode y = x.getParent();
        while (y != null && x == y.getRightSon()) {
            x = y;
            y = y.getParent();
        }
        return y;
    }

    /**
     * @param x - the root of a subtree
     * @return the minimum node at the given subtree
     */
    private RedBlackNode getMinimum(RedBlackNode x) {
        while (x.getLeftSon() != null)
            x = x.getLeftSon();
        return x;
    }

    //setters

    /**
     * sets new root the RB-Tree
     *
     * @param newRoot - the node which will the new root of the RB-Tree
     */
    public void setRoot(RedBlackNode newRoot) {
        _root = newRoot;
        _root.setParent(null);
    }

    //methods

    /**
     * inserts new word into the RB-Tree
     *
     * @param T   - the root of the RB-Tree
     * @param str - the new word
     */
    public void insert(RedBlackNode T, String str) {
        RedBlackNode y = this._nil;
        RedBlackNode x = this.getRoot();
        RedBlackNode z = new RedBlackNode(str);
        while (x != this._nil) {
            y = x;
            if (str.compareTo(x.getValue()) < 0)//new node is lexicographically smaller than the compared node
                x = x.getLeftSon();
            else//new node is lexicographically bigger than the compared node
                x = x.getRightSon();
        }
        z.setParent(y);
        if (y == this._nil) //T is empty
        {
            T = z;
            this.setRoot(z);
        } else {
            if (str.compareTo(y.getValue()) < 0)
                y.setLeftSon(z);
            else
                y.setRightSon(z);
        }
        z.setLeftSon(this._nil);
        z.setRightSon(this._nil);
        insertColorFixUp(T, z);

    }

    /**
     * fixes violations (if exist) at the RB-Tree Properties as a result of inserting new node
     *
     * @param T - the root of the RB-Tree
     * @param z - the node that makes violation at the RB-Tree Properties
     */
    public void insertColorFixUp(RedBlackNode T, RedBlackNode z) {
        while (z != T && z.getParent().getColor().equals(RED)) {
            if (z.getParent() == (z.getGrandpa()).getLeftSon())//case a - z's father is a left child
            {
                RedBlackNode y = (z.getGrandpa()).getRightSon();
                if (y.getColor().equals(RED)) //case a.1 - z's uncle is RED
                {
                    y.getParent().setColor(BLACK);
                    y.setColor(BLACK);
                    z.getGrandpa().setColor(RED);
                    z = z.getGrandpa();
                } else {
                    if (z == z.getParent().getRightSon())//case a.2 - z's uncle is RED & z is a right child
                    {
                        z = z.getParent();
                        leftRotate(T, z);
                    }
                    z.getParent().setColor(BLACK);//case a.3 - z's uncle is RED & z is a left child
                    z.getGrandpa().setColor(RED);
                    rightRotate(T, z.getGrandpa());
                }
            } else//case b - z's father is a right child
            {
                RedBlackNode y = (z.getGrandpa()).getLeftSon();
                if (y.getColor().equals(RED))//case b.1 - z's uncle is RED
                {
                    z.getParent().setColor(BLACK);
                    y.setColor(BLACK);
                    z.getGrandpa().setColor(RED);
                    z = z.getGrandpa();
                } else {
                    if (z == z.getParent().getLeftSon())//case b.2 - z's uncle is RED & z is a left child
                    {
                        z = z.getParent();
                        rightRotate(T, z);
                    }
                    z.getParent().setColor(BLACK);//case b.3 - z's uncle is RED & z is a right child
                    z.getGrandpa().setColor(RED);
                    leftRotate(T, z.getGrandpa());
                }
            }
        }
        //  }
        this._root.setColor(BLACK);
    }

    /**
     * searches a node with a given string as a key
     *
     * @param T   - the root of the RB-Tree
     * @param str - the string which is used as a search key
     * @return the node with the given key, if it exists. otherwise return null
     */
    public RedBlackNode search(RedBlackNode T, String str) {
        if (T == this._nil || T.getValue() == str)
            return T;
        if (str.compareTo(T.getValue()) < 0)
            return search(T.getLeftSon(), str);
        else
            return search(T.getRightSon(), str);
    }

    /**
     * deletes a node from the RB-Tree by its string
     *
     * @param T   - the root of the RB-Tree
     * @param str - the string of the deleted node
     * @return the deleted node if it exits. otherwise return null
     */
    public RedBlackNode delete(RedBlackNode T, String str) {
        RedBlackNode z = search(T, str);
        RedBlackNode y = new RedBlackNode();
        RedBlackNode x = new RedBlackNode();
        if ((z.getLeftSon() == null) || (z.getRightSon() == null))
            y = z;
        else
            y = getSuccessor(z);
//        if (y!=null) {
        if (y.getLeftSon() != null)
            x = y.getLeftSon();
        else
            x = y.getRightSon();
        if (x != null)
            x.setParent(y.getParent());//deleting the relation between z and his father
        if (y.getParent() == this._nil)// if the deleted node is the root
        {
            T = x;
            this.setRoot(x);
        } else {//both of the deleted node's sons aren't null
            if (y == y.getParent().getLeftSon())
                y.getParent().setLeftSon(x);
            else
                y.getParent().setRightSon(x);
        }
        if (y != z)
            z.setValue(y.getValue());
        if (y.getColor().equals(BLACK))//if we deleted a BLACK node->we have to do a color fix
            deleteColorFixUp(T, x);
//        }
        return y;//y is the deleted node

    }

    /**
     * fixes violations at the RB-Tree Properties as a result of deleting a black node
     *
     * @param T - the root of the RB-Tree
     * @param x - the node that makes violation at the RB-Tree Properties
     */
    public void deleteColorFixUp(RedBlackNode T, RedBlackNode x) {
        RedBlackNode w = new RedBlackNode();
        while (x != null && x != T && x.getColor().equals(BLACK)) {
            if (x == x.getParent().getLeftSon())//case a - x is a left son
            {
                w = x.getParent().getRightSon();
                if (w.getColor().equals(RED))//case a.1 - x's uncle is RED
                {
                    w.setColor(BLACK);
                    x.getParent().setColor(RED);
                    leftRotate(T, x.getParent());
                    w = x.getParent().getRightSon();
                }
                if (w != null && w.getRightSon() != null && w.getLeftSon() != null &&
                        ((w.getLeftSon() == this._nil || w.getLeftSon().getColor().equals(BLACK)) &&
                                ((w.getLeftSon() == this._nil || w.getRightSon().getColor().equals(BLACK)))))
                //case a.2 - x's uncle is BLACK & both of the uncle's sons are BLACK
                {
                    w.setColor(RED);
                    x = x.getParent();
                } else {
                    if (w != null && w.getRightSon() != null && w.getLeftSon() != null &&
                            w.getRightSon().getColor().equals(BLACK)) //case a.3 - x's uncle is BLACK & uncle's right son is BLACK
                    {
                        w.getLeftSon().setColor(BLACK);
                        w.setColor(RED);
                        rightRotate(T, w);
                        w = x.getParent().getRightSon();
                    }
                    //case a.4 - x's uncle is BLACK & uncle's left son is BLACK
                    if (w != null && x != null)
                        w.setColor(x.getParent().getColor());
                    x.getParent().setColor(BLACK);
                    if (w.getRightSon() != null)
                        w.getRightSon().setColor(BLACK);
                    leftRotate(T, x.getParent());
                    x = T;
                }
            } else //case b - x is a right son
            {
                w = x.getParent().getLeftSon();
                if (w.getColor().equals(RED)) //case b.1 - x's uncle is RED
                {
                    w.setColor(BLACK);
                    x.getParent().setColor(RED);
                    rightRotate(T, x.getParent());
                    w = x.getParent().getLeftSon();
                }
                if ((w.getRightSon() == null || w.getRightSon().getColor().equals(BLACK))
                        && (w.getLeftSon() == null || w.getLeftSon().getColor().equals(BLACK)))
                //case b.2 - x's uncle is BLACK & both of the uncle's sons are BLACK
                {
                    w.setColor(RED);
                    x = x.getParent();
                } else {
                    if (w.getLeftSon().getColor().equals(BLACK))//case b.3 - x's uncle is BLACK & uncle's left son is BLACK
                    {
                        w.getRightSon().setColor(BLACK);
                        w.setColor(RED);
                        leftRotate(T, w);
                        w = x.getParent().getLeftSon();
                    }
                    //case b.4 - x's uncle is BLACK & uncle's right son is BLACK
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(BLACK);
                    w.getLeftSon().setColor(BLACK);
                    rightRotate(T, x.getParent());
                    x = T;
                }
            }
        }
        if (x != null)
            x.setColor(BLACK);
    }

    /**
     * rotates left using x as a pivot
     *
     * @param T - the root of the RB-Tree
     * @param x - the pivot for the rotation
     */
    public void leftRotate(RedBlackNode T, RedBlackNode x) {
        RedBlackNode y = x.getRightSon();//set y
        x.setRightSon(y.getLeftSon());//turn y's left subtree into x's right subtree
        if (y.getLeftSon() != null)
            y.getLeftSon().setParent(x);
        y.setParent(x.getParent());//link x's parent to y
        if (x.getParent() == null) {
            T = y;
            this._root = y;
        } else {
            if (x == x.getParent().getLeftSon())
                x.getParent().setLeftSon(y);
            else
                x.getParent().setRightSon(y);
        }
        y.setLeftSon(x);//put x on y's left
        x.setParent(y);
    }

    /**
     * rotates right using x as a pivot
     *
     * @param T - the root of the RB-Tree
     * @param x - the pivot for the rotation
     */
    public void rightRotate(RedBlackNode T, RedBlackNode x) {
        RedBlackNode y = x.getLeftSon();//set y
        x.setLeftSon(y.getRightSon());//turn y's right subtree into x's left subtree
        if (y.getRightSon() != this._nil)
            y.getRightSon().setParent(x);
        y.setParent(x.getParent());//link x's parent to y
        if (x.getParent() == null) {
            T = y;
            this._root = y;
        } else {
            if (x == x.getParent().getRightSon())
                x.getParent().setRightSon(y);
            else
                x.getParent().setLeftSon(y);
        }
        y.setRightSon(x);//put x on y's right
        x.setParent(y);
    }

    /**
     * method for inorder scanning of the RB-Tree: Left->Node->Right
     *
     * @param x - the root the RB-Tree
     */
    public void inorderTreeWalk(RedBlackNode x) {
        if (x != this._nil) {
            inorderTreeWalk(x.getLeftSon());
            System.out.println(x.getValue() + " " + x.getColor());
            inorderTreeWalk(x.getRightSon());
        }
    }

}