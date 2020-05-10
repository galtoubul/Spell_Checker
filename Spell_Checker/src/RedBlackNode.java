/**
 * Represents information about Red Black Node
 * @author David kipnis and Gal Toubul
 * @version 1.0
 */
public class RedBlackNode
{
    //declarations
    private String _str;
    private String _color;
    private RedBlackNode _leftSon, _rightSon,_parent;
    final String RED="RED";
    final String BLACK="BLACK";


    //constructor

    /**
     * creates a new RB Node which contains the given String as data
     * <p>sets its parent and sons to null</p>
     * <p>sets its color to red</p>
     * @param str - the data for the new RB Node
     */
    public RedBlackNode(String str)
    {
        _str=str;
        _leftSon=null;
        _rightSon=null;
        _parent=null;
        _color=RED;
    }

    /**
     * default constructor for empty RB Nodes
     */
    public RedBlackNode()
    {
        _str="";
        _leftSon=null;
        _rightSon=null;
        _parent=null;
        _color=RED;
    }

    /**
     * copy constructor for RB Nodes
     * sets the new RB Node's parents, sons, color and String as the copied RB Node
     * @param other - the copied RB Node
     */
    public RedBlackNode(RedBlackNode other)
    {
        _str=other.getValue();
        _leftSon=other.getLeftSon();
        _rightSon=other.getRightSon();
        _parent=other.getParent();
        _color=other.getColor();
    }

    //getters

    /**
     * @return the String of the RB Node
     */
    public String getValue()
    {
        return _str;
    }

    /**
     * @return the left son of the RB Node
     */
    public RedBlackNode getLeftSon()
    {
        return _leftSon;
    }
    /**
     * @return the right son of the RB Node
     */
    public RedBlackNode getRightSon()
    {
        return _rightSon;
    }

    /**
     * @return the parent of the RB Node
     */
    public RedBlackNode getParent()
    {
        return _parent;
    }

    /**
     * @return the grandpa (parent of parent) of the RB Node
     */
    public RedBlackNode getGrandpa()
    {
        if (_parent != null)
            return _parent.getParent();
        else
            return null;
    }

    /**
     * @return the color of the RB Node
     */
    public String getColor()
    {
        return _color;
    }

    //setters

    /**
     * sets the String of the RB Node with the given String
     * @param str - The String which will be the RB-Node's String
     */
    public void setValue(String str)
    {
        _str=str;
    }

    /**
     * sets the given RB Node as the RB-Node's left son
     * @param newLeft - The RB Node which will be the RB-Node's left son
     */
    public void setLeftSon(RedBlackNode newLeft)
    {
        _leftSon=newLeft;
    }

    /**
     * sets the given RB Node as the RB-Node's right son
     * @param newRight - The RB Node which will be the RB-Node's right son
     */
    public void setRightSon(RedBlackNode newRight)
    {
        _rightSon=newRight;
    }

    /**
     * sets the given RB Node as the RB-Node's parent
     * @param newParent - The RB Node which will be the RB-Node's parent
     */
    public void setParent(RedBlackNode newParent)
    {
        _parent=newParent;
    }

    /**
     * sets the given color as the RB-Node's color
     * @param color - the String which will be the RB-Node's color
     */
    public void setColor(String color)
    {
        if (color.equals(RED))
            _color=RED;
        else
            _color=BLACK;
    }
}//end of class RedBlackNode